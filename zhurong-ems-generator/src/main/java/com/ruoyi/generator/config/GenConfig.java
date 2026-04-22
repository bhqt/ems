package com.ruoyi.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = {"classpath:generator.yml"})
public class GenConfig {
	/** 作者 */
	public static String author;

	/** 生成包路径 */
	public static String packageName;

	/** 自动去除表前缀，默认是false */
	public static boolean autoRemovePre;

	/** 表前缀(类名不会包含表前缀) */
	public static String tablePrefix;

	public static String moduleName;
	// vue文件生成后存放的文件夹路径
	public static String vueFileGenPath;
	// uniapp文件生成后存放的文件夹路径
	public static String uniappFileGenPath;

	// 默认sql文件生成后存放的路径
	public static String sqlFilePath;
	public static String projectModuleName;
	public static int listColMaxLength;
	public static int queryColMaxNum;
	public static int setTextareaLength;
	public static String[] setUploadByColName;
	public static String[] setSummernoteByColName;
	public static String[] setSelectByColName;
	public static String[] setSelectByColComment;
	public static String[] setCxSelectFatherName;
	public static String[] setCxSelectFatherNameByHand;
	public static String deptDataScopeFilterCol;
	public static String submitAuditCol;
	public static String parentMenuName;
	public static String[] selfDict;

	// 【注意】
	// set方式上添加类似注解：@Value("${vueFileGenPath}")
	// set方法上必须去到static关键字，否则系统中通过get方法获取不到对应的值
	// set方法上必须去到static关键字，否则系统中通过get方法获取不到对应的值
	// set方法上必须去到static关键字，否则系统中通过get方法获取不到对应的值

	public static String getVueFileGenPath() {
		return vueFileGenPath;
	}

	@Value("${vueFileGenPath}")
	public void setVueFileGenPath(String vueFileGenPath) {
		GenConfig.vueFileGenPath = vueFileGenPath;
	}

	public static String getUniappFileGenPath() {
		return uniappFileGenPath;
	}

	@Value("${uniappFileGenPath}")
	public  void setUniappFileGenPath(String uniappFileGenPath) {
		GenConfig.uniappFileGenPath = uniappFileGenPath;
	}

	public static String[] getSelfDict() {
		return selfDict;
	}

	@Value("${selfDict}")
	public void setSelfDict(String[] selfDict) {
		GenConfig.selfDict = selfDict;
	}

	public static String getParentMenuName() {
		return parentMenuName;
	}

	@Value("${parentMenuName}")
	public void setParentMenuName(String parentMenuName) {
		GenConfig.parentMenuName = parentMenuName;
	}

	public static String[] getSetSelectByColComment() {
		return setSelectByColComment;
	}

	@Value("${setSelectByColComment}")
	public void setSetSelectByColComment(String[] setSelectByColComment) {
		GenConfig.setSelectByColComment = setSelectByColComment;
	}

	public static String getSubmitAuditCol() {
		return submitAuditCol;
	}

	@Value("${submitAuditCol}")
	public void setSubmitAuditCol(String submitAuditCol) {
		GenConfig.submitAuditCol = submitAuditCol;
	}

	public static String getDeptDataScopeFilterCol() {
		return deptDataScopeFilterCol;
	}

	@Value("${deptDataScopeFilterCol}")
	public void setDeptDataScopeFilterCol(String deptDataScopeFilterCol) {
		GenConfig.deptDataScopeFilterCol = deptDataScopeFilterCol;
	}

	public static String[] getSetCxSelectFatherNameByHand() {
		return setCxSelectFatherNameByHand;
	}

	@Value("${setCxSelectFatherNameByHand}")
	public void setSetCxSelectFatherNameByHand(String[] setCxSelectFatherNameByHand) {
		GenConfig.setCxSelectFatherNameByHand = setCxSelectFatherNameByHand;
	}

	public static String[] getSetCxSelectFatherName() {
		return setCxSelectFatherName;
	}

	@Value("${setCxSelectFatherName}")
	public void setSetCxSelectFatherName(String[] setCxSelectFatherName) {
		GenConfig.setCxSelectFatherName = setCxSelectFatherName;
	}

	public static String[] getSetSelectByColName() {
		return setSelectByColName;
	}

	@Value("${setSelectByColName}")
	public void setSetSelectByColName(String[] setSelectByColName) {
		GenConfig.setSelectByColName = setSelectByColName;
	}

	public static String[] getSetSummernoteByColName() {
		return setSummernoteByColName;
	}

	@Value("${setSummernoteByColName}")
	public void setSetSummernoteByColName(String[] setSummernoteByColName) {
		GenConfig.setSummernoteByColName = setSummernoteByColName;
	}

	public static String[] getSetUploadByColName() {
		return setUploadByColName;
	}

	@Value("${setUploadByColName}")
	public void setSetUploadByColName(String[] setUploadByColName) {
		GenConfig.setUploadByColName = setUploadByColName;
	}

	public static int getListColMaxLength() {
		return listColMaxLength;
	}

	@Value("${listColMaxLength}")
	public void setListColMaxLength(int listColMaxLength) {
		GenConfig.listColMaxLength = listColMaxLength;
	}

	public static int getQueryColMaxNum() {
		return queryColMaxNum;
	}

	@Value("${queryColMaxNum}")
	public void setQueryColMaxNum(int queryColMaxNum) {
		GenConfig.queryColMaxNum = queryColMaxNum;
	}

	public static int getSetTextareaLength() {
		return setTextareaLength;
	}

	@Value("${setTextareaLength}")
	public void setSetTextareaLength(int setTextareaLength) {
		GenConfig.setTextareaLength = setTextareaLength;
	}

	public static String getModuleName() {
		return moduleName;
	}

	@Value("${moduleName}")
	public void setModuleName(String moduleName) {
		GenConfig.moduleName = moduleName;
	}

	public static String getAuthor() {
		return author;
	}

	@Value("${author}")
	public void setAuthor(String author) {
		GenConfig.author = author;
	}

	public static String getPackageName() {
		return packageName;
	}

	@Value("${packageName}")
	public void setPackageName(String packageName) {
		GenConfig.packageName = packageName;
	}

	public static boolean getAutoRemovePre() {
		return autoRemovePre;
	}

	@Value("${autoRemovePre}")
	public void setAutoRemovePre(boolean autoRemovePre) {
		GenConfig.autoRemovePre = autoRemovePre;
	}

	public static String getTablePrefix() {
		return tablePrefix;
	}

	@Value("${tablePrefix}")
	public void setTablePrefix(String tablePrefix) {
		GenConfig.tablePrefix = tablePrefix;
	}
}
