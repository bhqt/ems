package com.cpems.job.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.system.domain.enums.EnergyType;
import com.ruoyi.system.service.IEnergyService;
import com.ruoyi.system.service.IInspectionPlanService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * XxlJob开发示例（Bean模式）
 * <p>
 * 开发步骤：
 * 1、任务开发：在Spring Bean实例中，开发Job方法；
 * 2、注解配置：为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobHelper.log" 打印执行日志；
 * 4、任务结果：默认任务结果为 "成功" 状态，不需要主动设置；如有诉求，比如设置任务结果为失败，可以通过 "XxlJobHelper.handleFail/handleSuccess" 自主设置任务结果；
 * @author xuxueli 2019-12-11 21:52:51
 */
@Slf4j
@Service
public class SampleService {
    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    @Value("${server.port}")
    private int serverPort;

    private final IInspectionPlanService iInspectionPlanService;
    private final IEnergyService energyService;

    @Autowired
    public SampleService(IInspectionPlanService iInspectionPlanService, IEnergyService energyService) {
        this.iInspectionPlanService = iInspectionPlanService;
        this.energyService = energyService;
    }

    private long curVal = 0;

    public long getTestVal() {
        if (curVal == 0) {
            long baseTime = DateUtil.parse("2025-04-01 00:00:00").getTime(); // 基准时间戳
            // 2. 计算当前时间与基准时间的分钟差
            long currentTime = System.currentTimeMillis();
            long minutesElapsed = (currentTime - baseTime) / (60 * 1000); // 转为分钟数

            // 3. 生成严格递增的整数值（每分钟+1）
            curVal = minutesElapsed;
        } else {
            int increment = RandomUtil.randomInt(0, 31); // 每次增加 10~30 的随机值
            curVal = curVal + increment;
        }
        return curVal;
    }

    /**
     * 测试向MQTT发送电表数据
     */
    @XxlJob("sendElectricDataToMqttTest")
    public void sendElectricDataToMqttTest() {
        // 1. 定义接口地址和固定参数
        String url = "http://localhost:" + serverPort + "/autoee-iot-ems/system/mqtt/sendEquipmentData";
        logger.info("[开始]-定时任务-测试向MQTT发送电表数据-请求地址：{}" + url);
        String clientId = "101_电表01"; // 中文无需手动编码，Hutool 自动处理

        // 2. 生成动态参数
        // 当前时间（格式：yyyy-MM-dd HH:mm:ss）
        String createTime = DateUtil.now();

        // 随机选择一个 topicType（从英文标识符列表选）
        String[] topicTypes = {"electric/voltage", "electric/current", "electric/power", "electric/consumption"};
        // String topicType = RandomUtil.randomEle(topicTypes);

        for (String topicType : topicTypes) {
            String[] clientIds = {"101_电表01",
                "202_电表01",
                "102_电表01",
                "201_电表01"
            };

            for (int i = 0; i < clientIds.length; i++) {
                clientId = clientIds[i];

                // 3. 根据 topicType 生成模拟值
                long value = 0;
                switch (topicType) {
                    case "electric/voltage":
                        // 电压：220V ±5V 波动
                        int baseVoltage = 220;
                        int voltageFluctuation = RandomUtil.randomInt(-50, 50); // -5~+5
                        value = baseVoltage + voltageFluctuation;
                        break;
                    case "electric/current":
                        // 电流：10A ±2A 波动
                        int baseCurrent = 40;
                        int currentFluctuation = RandomUtil.randomInt(-30, 30); // -2~+2
                        value = baseCurrent + currentFluctuation;
                        break;
                    case "electric/power":
                        // 电功率：1500W ±100W 波动
                        int basePower = 1500;
                        int powerFluctuation = RandomUtil.randomInt(-500, 501); // -100~+100
                        value = basePower + powerFluctuation;
                        break;
                    case "electric/consumption":
                        // 用电量：基于时间递增 + 随机增量（保证严格递增）
                        value = getTestVal();
                        break;
                }
                String valueStr = value + "";

                // 3. 构建参数映射
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("clientId", clientId);
                paramMap.put("value", valueStr);
                paramMap.put("createTime", createTime);
                paramMap.put("topicType", topicType);

                // 4. 发送 GET 请求（Hutool 自动处理 URL 编码）
                String response = HttpUtil.post(url, paramMap);

                // 5. 打印结果
                // System.out.println("请求参数: " + paramMap);
                // System.out.println("响应结果: " + response);
                logger.info("[完成]-定时任务-测试向MQTT发送电表数据-请求参数={}", JSONUtil.toJsonStr(paramMap));
            }
        }

    }

    /**
     * 测试向MQTT发送水表数据
     */
    @XxlJob("sendWaterDataToMqttTest")
    public void sendWaterDataToMqttTest() {
        String url = "http://localhost:" + serverPort + "/autoee-iot-ems/system/mqtt/sendEquipmentData";
        logger.info("[开始]-定时任务-测试向MQTT发送水表数据-请求地址：{}" + url);
        // 1. 定义接口地址和固定参数
        String value = getTestVal() + "";

        // 2. 生成动态参数
        // 当前时间（格式：yyyy-MM-dd HH:mm:ss）
        String createTime = DateUtil.now();

        // 随机选择一个 topicType（从英文标识符列表选）
        String[] topicTypes = {"water/consumption"};
        String topicType = RandomUtil.randomEle(topicTypes);

        String clientId = "202_水表01"; // 中文无需手动编码，Hutool 自动处理
        String[] clientIds = {"202_水表01",
            "101_水表01",
            "102_水表01",
            "201_水表01"
        };

        for (int i = 0; i < clientIds.length; i++) {
            clientId = clientIds[i];

            // 3. 构建参数映射
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("clientId", clientId);
            paramMap.put("value", value);
            paramMap.put("createTime", createTime);
            paramMap.put("topicType", topicType);

            // 4. 发送 GET 请求（Hutool 自动处理 URL 编码）
            String response = HttpUtil.post(url, paramMap);

            // 5. 打印结果
            // System.out.println("请求参数: " + paramMap);
            // System.out.println("响应结果: " + response);
            logger.info("[完成]-定时任务-测试向MQTT发送水表数据-请求参数={}-响应结果={}", JSONUtil.toJsonStr(paramMap), JSONUtil.toJsonStr(response));
        }
    }

    /**
     * 统计电能耗
     */
    @XxlJob("statisticsElectricityEnergy")
    public void statisticsElectricityEnergyByHour() {
        energyService.statisticsEnergyByHour(EnergyType.ELECTRICITY.getCode());
    }

    /**
     * 统计水能耗
     */
    @XxlJob("statisticsWaterEnergy")
    public void statisticsWaterEnergy() {
        energyService.statisticsEnergyByHour(EnergyType.WATER.getCode());
    }

    /**
     * 统计电功率峰谷值平均值
     */
    @XxlJob("statisticsPower")
    public void statisticsPower() {
        energyService.statisticsPowerByHour(EnergyType.ELECTRICITY.getCode());
    }

    /**
     * 统计电流峰谷值平均值
     */
    @XxlJob("statisticsCurrent")
    public void statisticsCurrent() {
        energyService.statisticsCurrentByHour(EnergyType.ELECTRICITY.getCode());
    }

    /**
     * 统计电压峰谷值平均值
     */
    @XxlJob("statisticsVoltage")
    public void statisticsVoltage() {
        energyService.statisticsVoltageByHour(EnergyType.ELECTRICITY.getCode());
    }

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demoJobHandler")
    public void demoJobHandler() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            XxlJobHelper.log("beat at:" + i);
        }
        // default success
    }

    /**
     * 2、分片广播任务
     */
    @XxlJob("shardingJobHandler")
    public void shardingJobHandler() throws Exception {

        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        XxlJobHelper.log("分片参数：当前分片序号 = {}, 总分片数 = {}", shardIndex, shardTotal);

        // 业务逻辑
        for (int i = 0; i < shardTotal; i++) {
            if (i == shardIndex) {
                XxlJobHelper.log("第 {} 片, 命中分片开始处理", i);
            } else {
                XxlJobHelper.log("第 {} 片, 忽略", i);
            }
        }

    }

    /**
     * 3、命令行任务
     */
    @XxlJob("commandJobHandler")
    public void commandJobHandler() throws Exception {
        String command = XxlJobHelper.getJobParam();
        int exitValue = -1;

        BufferedReader bufferedReader = null;
        try {
            // command process
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            //Process process = Runtime.getRuntime().exec(command);

            BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            // command log
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                XxlJobHelper.log(line);
            }

            // command exit
            process.waitFor();
            exitValue = process.exitValue();
        } catch (Exception e) {
            XxlJobHelper.log(e);
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        if (exitValue == 0) {
            // default success
        } else {
            XxlJobHelper.handleFail("command exit value(" + exitValue + ") is failed");
        }

    }

    /**
     * 4、跨平台Http任务
     * 参数示例：
     * "url: http://www.baidu.com\n" +
     * "method: get\n" +
     * "data: content\n";
     */
    @XxlJob("httpJobHandler")
    public void httpJobHandler() throws Exception {

        // param parse
        String param = XxlJobHelper.getJobParam();
        if (param == null || param.trim().length() == 0) {
            XxlJobHelper.log("param[" + param + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }

        String[] httpParams = param.split("\n");
        String url = null;
        String method = null;
        String data = null;
        for (String httpParam : httpParams) {
            if (httpParam.startsWith("url:")) {
                url = httpParam.substring(httpParam.indexOf("url:") + 4).trim();
            }
            if (httpParam.startsWith("method:")) {
                method = httpParam.substring(httpParam.indexOf("method:") + 7).trim().toUpperCase();
            }
            if (httpParam.startsWith("data:")) {
                data = httpParam.substring(httpParam.indexOf("data:") + 5).trim();
            }
        }

        // param valid
        if (url == null || url.trim().length() == 0) {
            XxlJobHelper.log("url[" + url + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        if (method == null || !Arrays.asList("GET", "POST").contains(method)) {
            XxlJobHelper.log("method[" + method + "] invalid.");

            XxlJobHelper.handleFail();
            return;
        }
        boolean isPostMethod = method.equals("POST");

        // request
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            // connection
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();

            // connection setting
            connection.setRequestMethod(method);
            connection.setDoOutput(isPostMethod);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");

            // do connection
            connection.connect();

            // data
            if (isPostMethod && data != null && data.trim().length() > 0) {
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(data.getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();
            }

            // valid StatusCode
            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }

            // result
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            String responseMsg = result.toString();

            XxlJobHelper.log(responseMsg);

            return;
        } catch (Exception e) {
            XxlJobHelper.log(e);

            XxlJobHelper.handleFail();
            return;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e2) {
                XxlJobHelper.log(e2);
            }
        }

    }

    /**
     * 5、生命周期任务示例：任务初始化与销毁时，支持自定义相关逻辑；
     */
    @XxlJob(value = "demoJobHandler2", init = "init", destroy = "destroy")
    public void demoJobHandler2() throws Exception {
        XxlJobHelper.log("XXL-JOB, Hello World.");
    }

    public void init() {
        log.info("init");
    }

    public void destroy() {
        log.info("destory");
    }

    /**
     * 自动巡检产生巡检记录（日）
     */
    @XxlJob("inspectionDayHandler")
    public void inspectionDayHandler() throws Exception {
        XxlJobHelper.log("inspectionDayHandler");
        iInspectionPlanService.inspectionDayHandler();
    }

    /**
     * 自动巡检产生巡检记录（周）
     */
    @XxlJob("inspectionWeekHandler")
    public void inspectionWeekHandler() throws Exception {
        XxlJobHelper.log("inspectionWeekHandler");
        iInspectionPlanService.inspectionWeekHandler();
    }

    /**
     * 自动巡检产生巡检记录（月）
     */
    @XxlJob("inspectionMonthHandler")
    public void inspectionMonthHandler() throws Exception {
        XxlJobHelper.log("inspectionMonthHandler");
        iInspectionPlanService.inspectionMonthHandler();
    }
}
