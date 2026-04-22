/**
 * 字符串工具类
 * 提供常用的字符串操作方法
 */
import config from '@/config'

const iconUtil = {

  getIconCommUrl(iconName) {
	  return config.iconReqUrl + iconName
  },
  getIconSysUrl(iconName) {
	  return config.iconReqUrl +"sys/" + iconName
  },


};

export default iconUtil;
