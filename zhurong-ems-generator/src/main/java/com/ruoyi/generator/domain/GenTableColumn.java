package com.ruoyi.generator.domain;

import javax.validation.constraints.NotBlank;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.mapper.GenTableColumnMapper;

/**
 * 代码生成业务字段表 gen_table_column
 * @author ruoyi
 */
public class GenTableColumn extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 编号 */
	private Long columnId;

	/** 归属表编号 */
	private Long tableId;

	/** 列名称 */
	private String columnName;

	/** 列描述 */
	private String columnComment;

	/** 列类型 */
	private String columnType;

	/** JAVA类型 */
	private String javaType;

	/** JAVA字段名 */
	@NotBlank(message = "Java属性不能为空")
	private String javaField;

	/** 是否主键（1是） */
	private String isPk;

	/** 是否自增（1是） */
	private String isIncrement;

	/** 是否必填（1是） */
	private String isRequired;

	/** 是否为插入字段（1是） */
	private String isInsert;

	/** 是否编辑字段（1是） */
	private String isEdit;
	private String isDetail;

	/** 是否列表字段（1是） */
	private String isList;

	/** 是否查询字段（1是） */
	private String isQuery;

	/** 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围） */
	private String queryType;

	/** 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件） */
	private String htmlType;

	/** 字典类型 */
	private String dictType;

	/** 排序 */
	private Integer sort;

	private String attrName;

	// 是否为唯一（1是）
	private String isUnique;
	private String ifHasRoleShowDetail = "";
	private String[] RoleShowDetailArr = {};
	private String firstLevelGridHead = "";
	private String firstLevelGridHeadTitle = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析firstLevelGridHeadTitle的值进行设置
	private String firstLevelGridHeadFiled = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析firstLevelGridHeadTitle的值进行设置
	private String firstLevelGridHeadCss = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析firstLevelGridHeadTitle的值进行设置
	private String secondLevelGridHead = "";
	private String secondLevelGridHeadTitle = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析secondLevelGridHead的值进行设置
	private String secondLevelGridHeadFiled = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析secondLevelGridHead的值进行设置
	private String secondLevelGridHeadCss = "";// 点击生成代码时，在VelocityUtils.prepareContext(table)中解析secondLevelGridHead的值进行设置

	/** 扩展配置 */
	private String extend;
	private String extend1;//表头样式
	private String extend2;//冻结
	private String extend3;// 级联
	private String extend4;//表头提示
	private String extend5;//导入
	private String extend6;//导出
	private String extend7;//校验
	private String extend8;//扩展信息
	private String extend9;

	private String isAppList;
	private String isAppDetail;
	private String isAppQuery;

	// 列扩展信息
	private GenTableColumnExtendInfo columnExtendInfo;

	// 添加新的字段后，以下文件需要进行对应修改，字段映射信息，查询字段信息，新增字典信息，修改字段信息
	// 如果配置遗漏，会导致模版中获取不到对应的值
	// GenTableMapper.xml
	// GenTableColumnMapper.xml

	public String getIsAppList() {
		return isAppList;
	}

	public void setIsAppList(String isAppList) {
		this.isAppList = isAppList;
	}

	public String getIsAppDetail() {
		return isAppDetail;
	}

	public void setIsAppDetail(String isAppDetail) {
		this.isAppDetail = isAppDetail;
	}

	public String getIsAppQuery() {
		return isAppQuery;
	}

	public void setIsAppQuery(String isAppQuery) {
		this.isAppQuery = isAppQuery;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public GenTableColumnExtendInfo getColumnExtendInfo() {
		return columnExtendInfo;
	}

	public void setColumnExtendInfo(GenTableColumnExtendInfo columnExtendInfo) {
		this.columnExtendInfo = columnExtendInfo;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

	public String getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}

	public String getIfHasRoleShowDetail() {
		return ifHasRoleShowDetail;
	}

	public void setIfHasRoleShowDetail(String ifHasRoleShowDetail) {
		this.ifHasRoleShowDetail = ifHasRoleShowDetail;
	}

	public String[] getRoleShowDetailArr() {
		return RoleShowDetailArr;
	}

	public void setRoleShowDetailArr(String[] roleShowDetailArr) {
		RoleShowDetailArr = roleShowDetailArr;
	}

	public String getFirstLevelGridHead() {
		return firstLevelGridHead;
	}

	public void setFirstLevelGridHead(String firstLevelGridHead) {
		this.firstLevelGridHead = firstLevelGridHead;
	}

	public String getFirstLevelGridHeadTitle() {
		return firstLevelGridHeadTitle;
	}

	public void setFirstLevelGridHeadTitle(String firstLevelGridHeadTitle) {
		this.firstLevelGridHeadTitle = firstLevelGridHeadTitle;
	}

	public String getFirstLevelGridHeadFiled() {
		return firstLevelGridHeadFiled;
	}

	public void setFirstLevelGridHeadFiled(String firstLevelGridHeadFiled) {
		this.firstLevelGridHeadFiled = firstLevelGridHeadFiled;
	}

	public String getFirstLevelGridHeadCss() {
		return firstLevelGridHeadCss;
	}

	public void setFirstLevelGridHeadCss(String firstLevelGridHeadCss) {
		this.firstLevelGridHeadCss = firstLevelGridHeadCss;
	}

	public String getSecondLevelGridHead() {
		return secondLevelGridHead;
	}

	public void setSecondLevelGridHead(String secondLevelGridHead) {
		this.secondLevelGridHead = secondLevelGridHead;
	}

	public String getSecondLevelGridHeadTitle() {
		return secondLevelGridHeadTitle;
	}

	public void setSecondLevelGridHeadTitle(String secondLevelGridHeadTitle) {
		this.secondLevelGridHeadTitle = secondLevelGridHeadTitle;
	}

	public String getSecondLevelGridHeadFiled() {
		return secondLevelGridHeadFiled;
	}

	public void setSecondLevelGridHeadFiled(String secondLevelGridHeadFiled) {
		this.secondLevelGridHeadFiled = secondLevelGridHeadFiled;
	}

	public String getSecondLevelGridHeadCss() {
		return secondLevelGridHeadCss;
	}

	public void setSecondLevelGridHeadCss(String secondLevelGridHeadCss) {
		this.secondLevelGridHeadCss = secondLevelGridHeadCss;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public String getExtend3() {
		return extend3;
	}

	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	public String getExtend4() {
		return extend4;
	}

	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}

	public String getExtend5() {
		return extend5;
	}

	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}

	public String getExtend6() {
		return extend6;
	}

	public void setExtend6(String extend6) {
		this.extend6 = extend6;
	}

	public String getExtend7() {
		return extend7;
	}

	public void setExtend7(String extend7) {
		this.extend7 = extend7;
	}

	public String getExtend8() {
		return extend8;
	}

	public void setExtend8(String extend8) {
		this.extend8 = extend8;
	}

	public String getExtend9() {
		return extend9;
	}

	public void setExtend9(String extend9) {
		this.extend9 = extend9;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaField(String javaField) {
		this.javaField = javaField;
	}

	public String getJavaField() {
		return javaField;
	}

	public String getCapJavaField() {
		return StringUtils.capitalize(javaField);
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	public String getIsPk() {
		return isPk;
	}

	public boolean isPk() {
		return isPk(this.isPk);
	}

	public boolean isPk(String isPk) {
		return isPk != null && StringUtils.equals("1", isPk);
	}

	public String getIsIncrement() {
		return isIncrement;
	}

	public void setIsIncrement(String isIncrement) {
		this.isIncrement = isIncrement;
	}

	public boolean isIncrement() {
		return isIncrement(this.isIncrement);
	}

	public boolean isIncrement(String isIncrement) {
		return isIncrement != null && StringUtils.equals("1", isIncrement);
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public boolean isRequired() {
		return isRequired(this.isRequired);
	}

	public boolean isRequired(String isRequired) {
		return isRequired != null && StringUtils.equals("1", isRequired);
	}

	public void setIsInsert(String isInsert) {
		this.isInsert = isInsert;
	}

	public String getIsInsert() {
		return isInsert;
	}

	public boolean isInsert() {
		return isInsert(this.isInsert);
	}

	public boolean isInsert(String isInsert) {
		return isInsert != null && StringUtils.equals("1", isInsert);
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public boolean isEdit() {
		return isInsert(this.isEdit);
	}

	public boolean isEdit(String isEdit) {
		return isEdit != null && StringUtils.equals("1", isEdit);
	}

	public boolean isDetail(String isDetail) {
		return isDetail != null && StringUtils.equals("1", isDetail);
	}

	public boolean isDetail() {
		return isInsert(this.isDetail);
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsList() {
		return isList;
	}

	public boolean isList() {
		return isList(this.isList);
	}

	public boolean isList(String isList) {
		return isList != null && StringUtils.equals("1", isList);
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public boolean isQuery() {
		return isQuery(this.isQuery);
	}

	public boolean isQuery(String isQuery) {
		return isQuery != null && StringUtils.equals("1", isQuery);
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryType() {
		return queryType;
	}

	public String getHtmlType() {
		return htmlType;
	}

	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return sort;
	}

	public boolean isSuperColumn() {
		return isSuperColumn(this.javaField);
	}

	public static boolean isSuperColumn(String javaField) {
		return StringUtils.equalsAnyIgnoreCase(javaField,
				// BaseEntity
				"createBy", "createTime", "updateBy", "updateTime", "remark", "delFlag", "delBy", "delTime",
				// TreeEntity
				"parentName", "parentId", "orderNum", "ancestors");
	}

	public boolean isUsableColumn() {
		return isUsableColumn(javaField);
	}

	public static boolean isUsableColumn(String javaField) {
		// isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
		return StringUtils.equalsAnyIgnoreCase(javaField, "parentId", "orderNum", "remark");
	}

	public String readConverterExp() {
		String remarks = StringUtils.substringBetween(this.columnComment, "（", "）");
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(remarks)) {
			for (String value : remarks.split(" ")) {
				if (StringUtils.isNotEmpty(value)) {
					Object startStr = value.subSequence(0, 1);
					String endStr = value.substring(1);
					sb.append("").append(startStr).append("=").append(endStr).append(",");
				}
			}
			return sb.deleteCharAt(sb.length() - 1).toString();
		} else {
			return this.columnComment;
		}
	}
}
