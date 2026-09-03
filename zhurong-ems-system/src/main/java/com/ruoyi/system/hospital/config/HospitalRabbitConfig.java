package com.ruoyi.system.hospital.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 医院智慧能源 - RabbitMQ 交换机/队列声明（与现有 MQTT->MQ 链路隔离）
 * <p>
 * 主队列消费失败（basicNack requeue=false）的消息经死信转发至重试队列；
 * 重试消费者按最大次数重新投递，超限则人工排查，保证数据不丢失。
 *
 * @author cpems
 */
@Configuration
@RequiredArgsConstructor
public class HospitalRabbitConfig {

    /** 重试队列（死信队列） */
    public static final String RETRY_QUEUE = "hospital.device.data.retry.queue";

    /** 重试路由键 */
    public static final String RETRY_ROUTING_KEY = "hospital.device.data.retry";

    /** 最大重试次数（重新投递到主队列的次数上限） */
    public static final int MAX_RETRY_TIMES = 3;

    private final HospitalIotProperties iotProperties;

    @Bean
    public TopicExchange hospitalTopicExchange() {
        return new TopicExchange(iotProperties.getExchange(), true, false);
    }

    @Bean
    public Queue hospitalDeviceDataQueue() {
        Map<String, Object> args = new HashMap<>(4);
        // 消费失败且不重新入队的消息，进入重试队列
        args.put("x-dead-letter-exchange", iotProperties.getExchange());
        args.put("x-dead-letter-routing-key", RETRY_ROUTING_KEY);
        return new Queue(iotProperties.getQueue(), true, false, false, args);
    }

    @Bean
    public Binding hospitalDeviceDataBinding() {
        return BindingBuilder.bind(hospitalDeviceDataQueue())
            .to(hospitalTopicExchange())
            .with(iotProperties.getRoutingKey());
    }

    @Bean
    public Queue hospitalDeviceDataRetryQueue() {
        return new Queue(RETRY_QUEUE, true);
    }

    @Bean
    public Binding hospitalDeviceDataRetryBinding() {
        return BindingBuilder.bind(hospitalDeviceDataRetryQueue())
            .to(hospitalTopicExchange())
            .with(RETRY_ROUTING_KEY);
    }
}
