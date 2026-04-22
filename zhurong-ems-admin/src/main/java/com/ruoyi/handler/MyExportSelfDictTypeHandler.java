package com.ruoyi.handler;

import com.ruoyi.common.utils.poi.ExcelHandlerAdapter;
import org.apache.poi.ss.usermodel.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2024/11/10 <br>
 * @author Double
 * @version 1.0.0
 */
public class MyExportSelfDictTypeHandler implements ExcelHandlerAdapter {
	private Map<Long, String> customerNameCache = new HashMap<>();

	/**
	 * 设置单元格样式还可以，对字段进行转码，效率太多，导出数据量大时，每个字段查询一次，速度很慢
	 */
	@Override
	public Object format(Object value, String[] args, Cell cell, Workbook wb) {
		// value 为返回单元格显示内容值
		// args 为excel注解传递的args数组值
		// cell 为单元格对象
		// wb 为工作簿对象
		if (args[0].equals("customerPhoneId")) {
			Long customerPhoneId = (Long) value;
			String customerName = customerNameCache.get(customerPhoneId);
			if (customerName == null) {
				// CustomerPhoneManage customerPhoneManage = SpringUtils.getBean(CustomerPhoneManageMapper.class).selectDataByPkCustomerPhoneManage(customerPhoneId);
				// customerName = customerPhoneManage.getCustomerName();
				// customerNameCache.put(customerPhoneId, customerName);
			}
			value = customerName;

			// 自定义用户名为若依/单元格文字设置为红色
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            Font dataFont = wb.createFont();
            dataFont.setFontName("Arial");
            dataFont.setFontHeightInPoints((short) 10);
            dataFont.setColor(IndexedColors.RED.index);
            style.setFont(dataFont);
            cell.setCellStyle(style);

		} else if (args[0].equals("batchNo")) {
			//	直接返回原值即可
		}
		return value;

	}
}
