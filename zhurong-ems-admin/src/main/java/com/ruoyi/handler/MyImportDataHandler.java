package com.ruoyi.handler;

import com.ruoyi.common.utils.poi.ExcelHandlerAdapter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Title: <br>
 * Desc: <br>
 * Date: 2024/11/10 <br>
 * @author Double
 * @version 1.0.0
 */
public class MyImportDataHandler implements ExcelHandlerAdapter
{
    @Override
    public Object format(Object value, String[] args, Cell cell, Workbook wb)
    {
        // value 为返回单元格显示内容值
		// args 为excel注解传递的args数组值
		// cell 为单元格对象
		// wb 为工作簿对象
		return value;
    }
}
