'use strict';

exports.main = async (event, context) => {
	console.log("本地云函数getPhoneNumber", event.access_token);
	console.log("本地云函数getPhoneNumber", event.openid);
	// event里包含着客户端提交的参数
	const res = await uniCloud.getPhoneNumber({
		// 替换成自己开通一键登录的应用的DCloud appid
		appid: '__UNI__99362CD',
		provider: 'univerify',
		// 手机号码一键登录：需保密内容
		// 开通uni手机号一键登录服务地址： https://doc.dcloud.net.cn/uniCloud/uni-login/service.html
		apiKey: 'cf3d623c3506a85c29d1xxxxxxxxx', // 在开发者中心开通服务并获取apiKey
		apiSecret: '9a918a67672bfb160fccxxxxxxxxxx', // 在开发者中心开通服务并获取apiSecret
		access_token: event.access_token,
		openid: event.openid
	});

	console.log(res); // res里包含手机号
	// 执行用户信息入库等操作，正常情况下不要把完整手机号返回给前端
	// 如果数据库在uniCloud上，可以直接入库
	// 如果数据库不在uniCloud上，可以通过 uniCloud.httpclient API，将手机号通过http方式传递给其他服务器的接口，详见：https://uniapp.dcloud.net.cn/uniCloud/cf-functions?id=httpclient
	return {
		code: 0,
		message: '获取手机号成功',
		phoneNumber: res.phoneNumber
	}
}