package com.ruoyi.generator.domain;

/**
 * Title: 代码生成-配置的字段扩展信息<br>
 * Desc: <br>
 * Date: 2022-7-11 <br>
 * @author Double
 * @version 1.0.0
 */
public class GenTableColumnExtendInfo {
	private String selfDict= "";
	private String selfDictType= "";
	private String selfDictRelaTable= "";
	private String selfDictRelaCol= "";
	private String selfDictShowCol= "";

	public String getSelfDictShowCol() {
		return selfDictShowCol;
	}

	public void setSelfDictShowCol(String selfDictShowCol) {
		this.selfDictShowCol = selfDictShowCol;
	}

	public String getSelfDict() {
		return selfDict;
	}

	public void setSelfDict(String selfDict) {
		this.selfDict = selfDict;
	}

	public String getSelfDictType() {
		return selfDictType;
	}

	public void setSelfDictType(String selfDictType) {
		this.selfDictType = selfDictType;
	}

	public String getSelfDictRelaTable() {
		return selfDictRelaTable;
	}

	public void setSelfDictRelaTable(String selfDictRelaTable) {
		this.selfDictRelaTable = selfDictRelaTable;
	}

	public String getSelfDictRelaCol() {
		return selfDictRelaCol;
	}

	public void setSelfDictRelaCol(String selfDictRelaCol) {
		this.selfDictRelaCol = selfDictRelaCol;
	}

}
