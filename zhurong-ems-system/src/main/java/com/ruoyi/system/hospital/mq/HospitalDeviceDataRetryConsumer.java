package com.ruoyi.system.hospital.mq;

import com.rabbitmq.client.Channel;
import com.ruoyi.system.hospital.config.HospitalIotProperties;
import com.ruoyi.system.hospital.config.HospitalRabbitConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 医院设备数据重试消费者
 * <p>
 * 主队列落库失败（basicNack requeue=false）的消息经死信进入本重试队列；
 * 按重试次数重新投递主队列，超过 {@link HospitalRabbitConfig#MAX_RETRY_TIMES} 次则放弃并人工排查，保证数据不丢失。
 *
 * @author cpems
 */
@RequiredArgsConstructor
@Component
public class HospitalDeviceDataRetryConsumer {

    private static final Logger log = LoggerFactory.getLogger(HospitalDeviceDataRetryConsumer.class);

    /** 重试次数消息头 */
    public static final String RETRY_COUNT_HEADER = "x-retry-count";

    private final RabbitTemplate rabbitTemplate;
    private final HospitalIotProperties iotProperties;

    @RabbitListener(queues = HospitalRabbitConfig.RETRY_QUEUE, ackMode = "MANUAL")
    public void onRetry(Message message, Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        int retryCount = readRetryCount(message);
        try {
            if (retryCount < HospitalRabbitConfig.MAX_RETRY_TIMES) {
                // 重新投递主队列，次数 +1
                MessageProperties props = new MessageProperties();
                props.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                props.setContentEncoding(StandardCharsets.UTF_8.name());
                if (message.getMessageProperties() != null) {
                    message.getMessageProperties().getHeaders().forEach(props::setHeader);
                }
                props.setHeader(RETRY_COUNT_HEADER, retryCount + 1);
                rabbitTemplate.send(iotProperties.getExchange(), iotProperties.getRoutingKey(),
                    new Message(message.getBody(), props));
                channel.basicAck(deliveryTag, false);
                log.warn("[IOT回调] 数据重试投递 第{}次 payload={}",
                    retryCount + 1, new String(message.getBody(), StandardCharsets.UTF_8));
            } else {
                // 超过最大重试次数：记录失败并确认，待人工排查
                log.error("[IOT回调] 数据重试{}次仍失败，已放弃，请人工排查 payload={}",
                    HospitalRabbitConfig.MAX_RETRY_TIMES, new String(message.getBody(), StandardCharsets.UTF_8));
                channel.basicAck(deliveryTag, false);
            }
        } catch (Exception e) {
            log.error("[IOT回调] 重试队列处理异常，放回重试队列", e);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (Exception nackEx) {
                log.error("[IOT回调] 重试 basicNack 失败", nackEx);
            }
        }
    }

    private int readRetryCount(Message message) {
        Object count = message.getMessageProperties().getHeader(RETRY_COUNT_HEADER);
        if (count instanceof Number) {
            return ((Number) count).intValue();
        }
        if (count != null) {
            try {
                return Integer.parseInt(String.valueOf(count));
            } catch (NumberFormatException ignore) {
                // 忽略异常头
            }
        }
        return 0;
    }
}
