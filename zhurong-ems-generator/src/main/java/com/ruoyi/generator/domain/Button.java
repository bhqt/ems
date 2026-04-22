package com.ruoyi.generator.domain;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2022-6-24 <br>
 * @author Double
 * @version 1.0.0
 */
public class Button {
	/** 名称 */
	private String title;
	/** 方法明 */
	private String functionName;
	/** 操作确认信息 */
	private String confirmMsg;
	/** 打开弹出窗口 */
	private String openDialog;

	public String getOpenDialog() {
		return openDialog;
	}

	public void setOpenDialog(String openDialog) {
		this.openDialog = openDialog;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
}
