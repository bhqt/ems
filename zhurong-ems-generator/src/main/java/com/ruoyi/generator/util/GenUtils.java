package com.ruoyi.generator.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.generator.domain.GenTableColumnExtendInfo;
import org.apache.commons.lang3.RegExUtils;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.config.GenConfig;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;

/**
 * 代码生成器 工具类
 * @author ruoyi
 */
public class GenUtils {
	/**
	 * 初始化表信息
	 */
	public static void initTable(GenTable genTable, String operName) {
		genTable.setGenType("1");
		genTable.setGenPath("/");
		genTable.setClassName(convertClassName(genTable.getTableName()));
		genTable.setPackageName(GenConfig.getPackageName());
		genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
		genTable.setBusinessName(getBusinessName(genTable.getTableName()));
		genTable.setFunctionName(replaceText(genTable.getTableComment()));
		genTable.setFunctionAuthor(GenConfig.getAuthor());
		genTable.setCreateBy(operName);
		genTable.setTableExtend1(
				"mapperExtend.xml,serviceExtend.java,dictHandler.java,index.vue,addUpdateDialog.vue,detailDialog.vue,importDialog.vue,api.js,apiExtend.js,api-app.js,serviceImpl.java,service.java,controller.java,domain.java,mapper.java,mapperExtend.java,mapper.xml,sql");
	}

	/**
	 * 初始化列属性字段
	 */
	public static void initColumnField(GenTableColumn column, GenTable table, int index) {
		// 初始化列扩展信息
		GenTableColumnExtendInfo genTableColumnExtendInfo = new GenTableColumnExtendInfo();
		genTableColumnExtendInfo.setSelfDict("0");
		genTableColumnExtendInfo.setSelfDictRelaTable("关联主表表名");
		genTableColumnExtendInfo.setSelfDictRelaCol("关联主表字段");
		genTableColumnExtendInfo.setSelfDictShowCol("关联主表显示的字段");
		column.setExtend8(JSONUtil.toJsonStr(genTableColumnExtendInfo));
		String dataType = getDbType(column.getColumnType());
		String columnName = column.getColumnName();
		String columnComment = column.getColumnComment();
		column.setTableId(table.getTableId());
		column.setCreateBy(table.getCreateBy());
		// 设置java字段名
		column.setJavaField(StringUtils.toCamelCase(columnName));
		// 设置默认类型
		column.setJavaType(GenConstants.TYPE_STRING);
		// 设置默认查询方式
		column.setQueryType(GenConstants.QUERY_EQ);
		Integer columnLength = getColumnLength(column.getColumnType());
		if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
			// 字符串长度超过500设置为文本域
			String htmlType = columnLength >= GenConfig.getSetTextareaLength() || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType) ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
			column.setHtmlType(htmlType);
		} else if (arraysContains(GenConstants.COLUMNTYPE_DATE, dataType)) {
			column.setJavaType(GenConstants.TYPE_DATE);
			column.setHtmlType(GenConstants.HTML_DATE);
			// 日期控件查询方式
			column.setQueryType(GenConstants.QUERY_BETWEEN);
		} else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
			column.setJavaType(GenConstants.TYPE_DATE);
			column.setHtmlType(GenConstants.HTML_DATETIME);
			// 日期时间控件查询方式
			column.setQueryType(GenConstants.QUERY_BETWEEN);
		} else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
			column.setHtmlType(GenConstants.HTML_INPUT);

			// 如果是浮点型 统一用BigDecimal
			String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
			if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
				column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
			}
			// 如果是整形
			else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 32) {
				column.setJavaType(GenConstants.TYPE_INTEGER);
			}
			// 如果是整形
			else if (column.getColumnType().equals("int")) {
				column.setJavaType(GenConstants.TYPE_INTEGER);
			}
			// 长整形
			else {
				column.setJavaType(GenConstants.TYPE_LONG);
			}
		}

		boolean columnRequired = column.isRequired();
		// 需要新增列
		boolean columnNotInsert = arraysContains(GenConstants.COLUMNNAME_NOT_INSERT, columnName);
		boolean columnNeedInsert = !columnNotInsert;
		// 需要编辑列
		boolean columnNotEdit = arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName);
		boolean columnNeedEdit = !columnNotEdit;

		boolean columnNotList = arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName);
		boolean columnNeedList = !columnNotList;

		// 插入字段（默认所有字段都需要插入）
		// column.setIsInsert(GenConstants.YES);
		// 新增字段
		if ((columnNeedInsert || columnRequired) && !column.isPk()) {
			column.setIsInsert(GenConstants.YES);
		} else {
			column.setIsInsert(GenConstants.NO);
			// column.setIsDetail(GenConstants.YES);
		}
		// 导入字段
		if ((!arraysContains(GenConstants.COLUMNNAME_NOT_IMPORT, columnName) || columnRequired) && !column.isPk()) {
			column.setExtend5(GenConstants.YES);
		} else {
			column.setExtend5(GenConstants.NO);
		}
		// 导出字段
		if ((!arraysContains(GenConstants.COLUMNNAME_NOT_EXPORT, columnName) || columnRequired) && !column.isPk()) {
			column.setExtend6(GenConstants.YES);
		} else {
			column.setExtend6(GenConstants.NO);
		}
		// 编辑字段
		if ((columnNeedEdit || columnRequired) && !column.isPk()) {
			column.setIsEdit(GenConstants.YES);
			column.setIsDetail(GenConstants.YES);
			column.setIsAppDetail(GenConstants.YES);
			// 默认编辑字段为导入字段
			column.setExtend5(GenConstants.YES);
			// 默认编辑字段为导出字段
			column.setExtend6(GenConstants.YES);
		} else {
			column.setIsEdit(GenConstants.NO);
			column.setIsDetail(GenConstants.NO);
			column.setIsAppDetail(GenConstants.NO);
			// 默认编辑字段为导入字段
			column.setExtend5(GenConstants.NO);
			// 默认编辑字段为导出字段
			column.setExtend6(GenConstants.NO);
		}
		// 列表字段：不是公共字段并且不是主键，并且字段长度小于配置的长度
		if ((columnNeedList || columnRequired) && !column.isPk()) {
			// if (columnLength <= GenConfig.listColMaxLength) {
			if (index <= GenConfig.listColMaxLength) {
				column.setIsList(GenConstants.YES);
				// if (index == 0) {
				// 	column.setIsAppList("1");
				// } else if (index == 1 || index == 2) {
				column.setIsAppList("2");
				// }
			}
			// }
		}
		if (StrUtil.equals("update_time", columnName)) {
			column.setIsList(GenConstants.YES);
			column.setIsAppList("3");
		}
		// 查询字段
		if ((!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) || columnRequired) && !column.isPk() && !GenConstants.HTML_TEXTAREA.equals(column.getHtmlType())) {
			// 默认只设置前几个字段为查询字段 Double
			if (index <= GenConfig.queryColMaxNum) {
				column.setIsQuery(GenConstants.YES);
				column.setIsAppQuery(GenConstants.YES);
			}
			// 必填字段默认作为查询字段
			if (columnRequired && !column.isPk()) {
				column.setIsAppQuery(GenConstants.YES);
				column.setIsQuery(GenConstants.YES);
			}
		}
		// 审核状态字段作为查询项
		if (columnName.equals(GenConfig.getSubmitAuditCol())) {
			column.setIsQuery(GenConstants.YES);
			column.setIsAppQuery(GenConstants.YES);
		}
		// // 编辑字段
		// if (columnNeedEdit && !column.isPk()) {
		// 	column.setIsEdit(GenConstants.YES);
		// }
		// // 列表字段
		// if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
		// 	column.setIsList(GenConstants.YES);
		// 	column.setIsAppList(GenConstants.YES);
		// }
		// // 查询字段
		// if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
		// 	column.setIsQuery(GenConstants.YES);
		// }

		// 查询字段类型
		if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
			column.setQueryType(GenConstants.QUERY_LIKE);
		}
		// // 类型&性别字段设置下拉框
		// else if (StringUtils.endsWithIgnoreCase(columnName, "type")
		//         || StringUtils.endsWithIgnoreCase(columnName, "sex"))
		// {
		//     column.setHtmlType(GenConstants.HTML_SELECT);
		// }
		// 数据库中字段名称使用type,status,cxselect,_1,_2,_3,_4,_5,_6结尾时，导入表时，字段的前端展示类型自动设置下拉框类型。
		for (String s : GenConfig.setSelectByColName) {
			if (StringUtils.endsWithIgnoreCase(columnName, s)) {
				column.setHtmlType(GenConstants.HTML_SELECT);
				//默认设置转码类型为sys_true_false，如果字段有对应的转码类型，会重新设置为对应的转码类型
				column.setDictType("sys_true_false");
			}
		}
		//设置为下拉框：字段描述包含 方式,类型
		for (String s : GenConfig.setSelectByColComment) {
			byte[] utf8Bytes = new byte[0];
			try {
				utf8Bytes = s.getBytes("ISO-8859-1");
				String utf8String = new String(utf8Bytes, "UTF-8");
				if (StringUtils.endsWithIgnoreCase(columnComment, utf8String)) {
					column.setHtmlType(GenConstants.HTML_SELECT);
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		// 字段名称以是否开头的，设置为下拉框，下拉类型为sys_true_false
		if (StringUtils.startsWithIgnoreCase(columnComment, "是否") ||
				StringUtils.startsWithIgnoreCase(columnName, "shi_fou")) {
			column.setHtmlType(GenConstants.HTML_SELECT);
			column.setDictType("sys_true_false");
		}

		// 状态字段设置单选框
		// if (StringUtils.endsWithIgnoreCase(columnName, "status"))
		// {
		//     column.setHtmlType(GenConstants.HTML_RADIO);
		// }
		//数据库中字段名称使用file、image、images结尾时，导入表时，字段的前端展示类型自动设置为上传控件。
		for (String s : GenConfig.setUploadByColName) {
			if (StringUtils.endsWithIgnoreCase(columnName, s)) {
				column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
				// 文件、图片默认在列表中显示
				column.setIsList(GenConstants.YES);
				column.setIsAppList(GenConstants.YES);
				// 文件、图片默认不作为查询项
				column.setIsQuery(GenConstants.NO);
			}
		}
		// // 图片字段设置图片上传控件
		// else if (StringUtils.endsWithIgnoreCase(columnName, "image"))
		// {
		//     column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
		// }
		// 文件字段设置文件上传控件
		if (StringUtils.endsWithIgnoreCase(columnName, "file") || StringUtils.endsWithIgnoreCase(columnName, "files")) {
			column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
		}

		//数据库中字段名称使用content结尾时，导入表时，字段的前端展示类型自动设置为富文本控件。
		for (String s : GenConfig.setSummernoteByColName) {
			if (StringUtils.endsWithIgnoreCase(columnName, s)) {
				column.setHtmlType(GenConstants.HTML_EDITOR);
			}
		}
		// blob设置为富文本控件，不再列表中显示，并且不作为查询项
		if ("blob".equals(dataType)) {
			// column.setHtmlType(GenConstants.HTML_TEXTAREA);
			column.setHtmlType(GenConstants.HTML_EDITOR);
			column.setIsList(GenConstants.NO);
			column.setIsQuery(GenConstants.NO);
		}

		//数据库中字段名称使用_tag结尾时，导入表时，字段的前端展示类型自动设置为标签输入框。
		if (StringUtils.endsWithIgnoreCase(columnName, "_tag")) {
			column.setHtmlType(GenConstants.INPUT_TAG);
		}
		// 下拉框字段作为查询项
		if (column.getHtmlType().equals(GenConstants.HTML_SELECT)) {
			column.setIsQuery(GenConstants.YES);
			column.setIsAppQuery(GenConstants.YES);
		}
	}

	/**
	 * 校验数组是否包含指定值
	 * @param arr         数组
	 * @param targetValue 值
	 * @return 是否包含
	 */
	public static boolean arraysContains(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 获取模块名
	 * @param packageName 包名
	 * @return 模块名
	 */
	public static String getModuleName(String packageName) {
		int lastIndex = packageName.lastIndexOf(".");
		int nameLength = packageName.length();
		return StringUtils.substring(packageName, lastIndex + 1, nameLength);
	}

	/**
	 * 获取业务名
	 * @param tableName 表名
	 * @return 业务名
	 */
	public static String getBusinessName(String tableName) {
		boolean autoRemovePre = GenConfig.getAutoRemovePre();
		String tablePrefix = GenConfig.getTablePrefix();
		if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
			String[] searchList = StringUtils.split(tablePrefix, ",");
			tableName = replaceFirst(tableName, searchList);
		}
		String businessName = tableName;

		businessName = StringUtils.toCamelCase(businessName);
		return businessName;
	}

	/**
	 * 表名转换成Java类名
	 * @param tableName 表名称
	 * @return 类名
	 */
	public static String convertClassName(String tableName) {
		boolean autoRemovePre = GenConfig.getAutoRemovePre();
		String tablePrefix = GenConfig.getTablePrefix();
		if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
			String[] searchList = StringUtils.split(tablePrefix, ",");
			tableName = replaceFirst(tableName, searchList);
		}
		return StringUtils.convertToCamelCase(tableName);
	}

	/**
	 * 批量替换前缀
	 * @param replacementm 替换值
	 * @param searchList   替换列表
	 * @return
	 */
	public static String replaceFirst(String replacementm, String[] searchList) {
		String text = replacementm;
		for (String searchString : searchList) {
			if (replacementm.startsWith(searchString)) {
				text = replacementm.replaceFirst(searchString, "");
				break;
			}
		}
		return text;
	}

	/**
	 * 关键字替换
	 * @param text 需要被替换的名字
	 * @return 替换后的名字
	 */
	public static String replaceText(String text) {
		return RegExUtils.replaceAll(text, "(?:表|若依)", "");
	}

	/**
	 * 获取数据库类型字段
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static String getDbType(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			return StringUtils.substringBefore(columnType, "(");
		} else {
			return columnType;
		}
	}

	/**
	 * 获取字段长度
	 * @param columnType 列类型
	 * @return 截取后的列类型
	 */
	public static Integer getColumnLength(String columnType) {
		if (StringUtils.indexOf(columnType, "(") > 0) {
			if (columnType.startsWith("decimal")) {
				return 10;
			} else {
				String length = StringUtils.substringBetween(columnType, "(", ")");
				return Integer.valueOf(length);
			}
		} else {
			return 0;
		}
	}
}
