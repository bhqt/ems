// 应用全局配置
module.exports = {
    // 当前环境：dev-开发环境，test-测试环境，prod-生产环境
    // 开发和测试环境，使用真实支付时，默认走支付宝的沙箱模式
    current_env: "dev",

    // 是否开启真实支付：
    // true时，进行真实支付，通过支付宝、微信进行支付
    // false时，进行模拟支付，直接支付成功
    openRealPay: false,

    // 使用支付宝沙箱模式测试
    useAlipaySandbox: true,

    //调用ruoyi后端服务接口
    // 本地环境
    baseUrl: 'http://192.168.1.43:8088/autoee-iot-ems',
    iconReqUrl: 'http://192.168.1.43:8088/autoee-iot-ems/profile/fontawesome/svgs/solid/',
    uploadReqUrl: 'http://192.168.1.43:8088/autoee-iot-ems/system/oss/upload',
    // 测试环境-服务器
    //baseUrl: 'http://22.22.22.22:20080/autoee-iot-ems',
    // iconReqUrl: 'http://22.22.22.22:20080/autoee-iot-ems/profile/fontawesome/svgs/solid/',
    // uploadReqUrl: 'http://22.22.22.22:20080/autoee-iot-ems/system/oss/upload',

    // 是否显示点击加载更多按钮：开发测试时PC浏览器不能上滑时可以显示，用来测试加载更多数据
    showLoadMoreButton: true,
    // 是否显示查找svg图标按钮：开发测试时可以显示，方便查找图标
    showSearchSvgIconButton: true,

    // univerify: {
    // 	ApiKey: "cf3d623c3506a85c29d1cf54xxxxxxx",
    // 	ApiSecret: "9a918a67672bfb160fcc7cbxxxxxxxxx",
    // },


    // 应用信息
    appInfo: {
        // 应用名称
        name: "autoEE电商",
        // 应用版本
        version: "1.1.0",
        // 应用logo
        logo: "/static/logo.png",
        // 官方网站
        site_url: "http://ruoyi.vip",
        // 政策协议
        agreements: [{
            title: "隐私政策",
            url: "https://ruoyi.vip/protocol.html"
        },
            {
                title: "用户服务协议",
                url: "https://ruoyi.vip/protocol.html"
            }
        ]
    }
}
