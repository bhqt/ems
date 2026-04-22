package com.ruoyi.generator.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.core.domain.entity.SysDictType;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.text.CharsetKit;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.config.GenConfig;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import com.ruoyi.generator.domain.GenTableColumnExtendInfo;
import com.ruoyi.generator.domain.GenTableExtendInfo;
import com.ruoyi.generator.mapper.GenTableColumnMapper;
import com.ruoyi.generator.mapper.GenTableMapper;
import com.ruoyi.generator.util.GenUtils;
import com.ruoyi.generator.util.VelocityInitializer;
import com.ruoyi.generator.util.VelocityUtils;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysMenuService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 * @author ruoyi
 */
@Service
public class GenTableServiceImpl implements IGenTableService {
	private static final Logger logger = LoggerFactory.getLogger(GenTableServiceImpl.class);

	@Autowired
	private GenTableMapper genTableMapper;

	@Autowired
	private GenTableColumnMapper genTableColumnMapper;
	@Autowired
	private ISysDictDataService dictDataService;
	@Autowired
	private ISysDictTypeService sysDictTypeService;
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 查询业务信息
	 * @param id 业务ID
	 * @return 业务信息
	 */
	@Override
	public GenTable selectGenTableById(Long id) {
		GenTable genTable = genTableMapper.selectGenTableById(id);
		setTableFromOptions(genTable);
		return genTable;
	}

	/**
	 * 查询业务列表
	 * @param genTable 业务信息
	 * @return 业务集合
	 */
	@Override
	public List<GenTable> selectGenTableList(GenTable genTable) {
		return genTableMapper.selectGenTableList(genTable);
	}

	/**
	 * 查询据库列表
	 * @param genTable 业务信息
	 * @return 数据库表集合
	 */
	@Override
	public List<GenTable> selectDbTableList(GenTable genTable) {
		return genTableMapper.selectDbTableList(genTable);
	}

	/**
	 * 查询据库列表
	 * @param tableNames 表名称组
	 * @return 数据库表集合
	 */
	@Override
	public List<GenTable> selectDbTableListByNames(String[] tableNames) {
		return genTableMapper.selectDbTableListByNames(tableNames);
	}

	/**
	 * 查询所有表信息
	 * @return 表信息集合
	 */
	@Override
	public List<GenTable> selectGenTableAll() {
		return genTableMapper.selectGenTableAll();
	}

	/**
	 * 修改业务
	 * @param genTable 业务信息
	 * @return 结果
	 */
	@Override
	@Transactional
	public void updateGenTableAndTableColumns(GenTable genTable) {
		String options = JSON.toJSONString(genTable.getParams());
		genTable.setOptions(options);
		int row = genTableMapper.updateGenTable(genTable);
		if (row > 0) {
			for (GenTableColumn cenTableColumn : genTable.getColumns()) {
				genTableColumnMapper.updateGenTableColumn(cenTableColumn);
			}
		}
	}

	/**
	 * 更新
	 * @param genTable
	 */
	public void updateGenTable(GenTable genTable) {
		int row = genTableMapper.updateGenTable(genTable);
	}

	/**
	 * 删除业务对象
	 * @param tableIds 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	@Transactional
	public void deleteGenTableByIds(Long[] tableIds) {
		genTableMapper.deleteGenTableByIds(tableIds);
		genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
	}

	/**
	 * 导入表结构
	 * @param tableList 导入表列表
	 */
	@Override
	@Transactional
	public void importGenTable(List<GenTable> tableList) {
		String operName = SecurityUtils.getUsername();
		try {
			for (GenTable table : tableList) {
				String tableName = table.getTableName();
				GenUtils.initTable(table, operName);

				// 初始化表备注信息
				GenTableExtendInfo genTableExtendInfo = new GenTableExtendInfo();
				genTableExtendInfo.setOrderByStr("order by t.update_time desc, t.id desc");
				genTableExtendInfo.setTableDefaultOrderByStr("updateTime");
				// 主页面和主页面列表中隐藏的按钮：add|edit|delete|import|export 等
				genTableExtendInfo.setShowButtons("handleQuery|resetQuery|add|delete|gridDelete|handleImport|handleExport|update|gridUpdate|gridDetail");
				genTableExtendInfo.setExtendInfo("needGridBorder|needRealDelete");
				// 默认查询时间范围，单位：小时
				genTableExtendInfo.setQueryDateRange("360*24");
				genTableExtendInfo.setAddDialogWidth("1000px");
				genTableExtendInfo.setAddDialogSpan("12");
				genTableExtendInfo.setDetailDialogWidth("1000px");
				genTableExtendInfo.setDetailDialogSpan("12");
				table.setRemark(JSONUtil.toJsonStr(genTableExtendInfo));

				// 设置默认的上级菜单
				// 将读取的内容转换为UTF-8编码的字节数组
				byte[] utf8Bytes = GenConfig.parentMenuName.getBytes("ISO-8859-1");
				String utf8String = new String(utf8Bytes, "UTF-8");
				List<SysMenu> sysMenuList = sysMenuService.selectMenuListByMenuname(utf8String);
				if (sysMenuList.size() == 1) {
					SysMenu sysMenu = sysMenuList.get(0);
					HashMap<String, String> map = new HashMap();
					map.put("parentMenuId", sysMenu.getMenuId() + "");
					map.put("parentMenuName", sysMenu.getMenuName());
					table.setOptions(JSONUtil.toJsonStr(map));
				}

				int row = genTableMapper.insertGenTable(table);
				if (row > 0) {
					// 保存列信息
					List<GenTableColumn> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
					// for (GenTableColumn column : genTableColumns) {
					GenTableColumn column = null;
					for (int i = 0; i < genTableColumns.size(); i++) {
						column = genTableColumns.get(i);
						GenUtils.initColumnField(column, table, i);
						// GenUtils.initColumnField(column, table);

						// Double 设置下拉框转码信息
						setColSelectInfoAndOtherInfo(column, table);

						genTableColumnMapper.insertGenTableColumn(column);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("导入失败：" + e.getMessage());
		}
	}

	// 设置下拉框转码信息
	private void setColSelectInfoAndOtherInfo(GenTableColumn column, GenTable table) {
		try {
			String columnName = column.getColumnName();
			// 有些字段没法提前设置为下拉框，直接判断所有字段，是否有对应的字典信息，有则修改为下拉框，并配置字典类型
			// 通过initColumnField方法中判断当前字段为下拉框时，为其自动赋值转码类型
			// if (GenConstants.HTML_SELECT.equals(column.getHtmlType()) ||
			// 		GenConstants.HTML_RADIO.equals(column.getHtmlType()) ||
			// 		GenConstants.HTML_CHECKBOX.equals(column.getHtmlType())) {

			logger.info("[开始]-字段[{}]设置转码信息", columnName);

			// 通过initColumnField方法中判断当前字段为下拉框时，为其自动赋值转码类型
			// Fuxs 通过字段名称查询字典表中是否有对应的字典类型，有则直接设置为对应的转码类型，生成代码前可以将字典表提前建好对应的字典类型
			SysDictType dictType = sysDictTypeService.selectDictTypeByType(columnName);
			// if (null == dictType) {
			// 通过字段名称，查找是否有字段类型为字段名称的右后部分，用于匹配多个字段使用同一个字典类型
			// dictType = sysDictTypeService.selectDictTypeByLikeColumnName(columnName);
			// }
			if (null != dictType) {
				logger.info("[获取]-字段：{}-对应的字典：{}", columnName, JSONUtil.toJsonStr(dictType));
				column.setDictType(dictType.getDictType());
				if (GenConstants.HTML_RADIO.equals(column.getHtmlType()) || GenConstants.HTML_CHECKBOX.equals(column.getHtmlType())) {
					// 已经是radio或者checkBox，不修改显示类型，否则修改为下拉框
				} else {
					column.setHtmlType(GenConstants.HTML_SELECT);
				}
				logger.info("[获取]-字段[{}]转码类型为[{}]", columnName, columnName);
			} else {
				// Fuxs 通过代码表中gen_code_select_col_rela类型中配置的字段与字典类型对应关系设置转码类型
				String codeSelectColRela = dictDataService.selectDictLabel("gen_code_select_col_rela", columnName);
				if (StrUtil.isNotBlank(codeSelectColRela)) {
					column.setDictType(codeSelectColRela);
					logger.info("[获取]-字段[{}]转码类型为[{}]", columnName, codeSelectColRela);
					if (GenConstants.HTML_RADIO.equals(column.getHtmlType()) || GenConstants.HTML_CHECKBOX.equals(column.getHtmlType())) {
						// 已经是radio或者checkBox，不修改显示类型，否则修改为下拉框
					} else {
						column.setHtmlType(GenConstants.HTML_SELECT);
					}
				}
			}

			String tableName = table.getTableName();
			// 自定义字典类型 - 通过配置文件初始化
			// 初始化列扩展信息
			GenTableColumnExtendInfo genTableColumnExtendInfo = new GenTableColumnExtendInfo();
			// generator.yml配置文件中逗号分隔的字符串自动转为了数组
			for (String selfDefinedDictDataStr : GenConfig.getSelfDict()) {
				String[] split = selfDefinedDictDataStr.split("#");
				System.out.println(split[0] + "--" + column.getColumnName());
				// 当前列为generator.yml配置的需要自定义转码的列，同时当前表不是源表，因为源表中的该字段是需要手动录入的，只有其他表使用该字段时需要通过源表进行转码
				if (split[0].equals(column.getColumnName())  && !tableName.equals(split[1]) ) {
					column.setDictType(split[1]);
					column.setHtmlType(GenConstants.HTML_SELECT_SELF_DEFINE);
					genTableColumnExtendInfo.setSelfDict("1");
					genTableColumnExtendInfo.setSelfDictType(split[1]);
					genTableColumnExtendInfo.setSelfDictRelaTable(split[1]);
					genTableColumnExtendInfo.setSelfDictRelaCol(split[2]);
					genTableColumnExtendInfo.setSelfDictShowCol(split[3]);
					column.setExtend8(JSONUtil.toJsonStr(genTableColumnExtendInfo));
				}
			}

			logger.info("[开始]-字段[{}]设置查询类型", columnName);
			// INPUT或TEXTAREA，设置查询类型为like
			if (column.getHtmlType().equals(GenConstants.HTML_INPUT) || column.getHtmlType().equals(GenConstants.HTML_TEXTAREA)) {
				column.setQueryType(GenConstants.QUERY_LIKE);
				if (ArrayUtil.contains(GenConstants.JAVA_TYPE_NUMBER, column.getJavaType())) {
					// 数字类型，设置查询类型为BETWEEN
					column.setQueryType(GenConstants.QUERY_BETWEEN);
				}
			}
			logger.info("[完成]-字段[{}]设置查询类型=", columnName, column.getQueryType());
		} catch (Exception e) {
			logger.error("【异常】-进行设置下拉框转码信息处理时出现异常！", e);
		}

	}

	/**
	 * 预览代码
	 * @param tableId 表编号
	 * @return 预览数据列表
	 */
	@Override
	public Map<String, String> previewCode(Long tableId) {
		Map<String, String> dataMap = new LinkedHashMap<>();
		// 查询表信息
		GenTable table = genTableMapper.selectGenTableById(tableId);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);
		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			dataMap.put(template, sw.toString());
		}
		return dataMap;
	}

	/**
	 * 生成代码（下载方式）
	 * @param tableName 表名称
	 * @return 数据
	 */
	@Override
	public byte[] downloadCode(String tableName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		generatorCode(tableName, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 生成代码（自定义路径）
	 * @param tableName 表名称
	 */
	@Override
	public void generatorCode(String tableName) {
		// 查询表信息
		GenTable table = genTableMapper.selectGenTableByName(tableName);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);

		// 通过备注字段解析额外的配置信息：如排序字符串 Fuxs
		// 新增模板用字段后，需要在下面的VelocityUtils.prepareContext方法中设置对应的模板字段
		String remark = table.getRemark();
		GenTableExtendInfo genTableExtendInfo = new GenTableExtendInfo();
		if (!StrUtil.isBlankOrUndefined(remark)) {
			try {
				genTableExtendInfo = JSONUtil.toBean(remark, GenTableExtendInfo.class);
			} catch (Exception e) {
				logger.warn("[提示]-解析传入的备注信息失败！");
				throw new RuntimeException("解析传入的备注信息失败！" + remark);
			}
			// 新增模板用字段后，需要在下面的VelocityUtils.prepareContext方法中设置对应的模板字段
			table.setGenTableExtendInfo(genTableExtendInfo);
			logger.info("[获取]-配置的代码生成扩展信息=[{}]", JSONUtil.toJsonStr(table.getGenTableExtendInfo()));
		}

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		String extend1 = StrUtil.nullToEmpty(table.getTableExtend1());
		// TODO 暂时固定生成app端文件
		extend1 = extend1 + ",uniappList.vue.vm,uniappAdd.vue.vm,uniappDetail.vue.vm";

		String[] canGenFilesArr = extend1.split("\\,");
		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
		for (String template : templates) {
			// if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm"))
			// {
			// 渲染模板
			if ("1".equals(table.getLocktable())) {
				if (StringUtils.containsAny(template, "java.vm", "mapper.xml.vm", "sql.vm")
						|| StrUtil.containsAny(template, canGenFilesArr)) {
					logger.info("当前表已经锁定，可以生成模版代码：" + template);
				} else {
					logger.info("当前表已经锁定，不能生成模版对应代码：" + template);
					continue;
				}

			}

			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			try {
				String path = getGenPath(table, template);

				if (StrUtil.containsAny(template, canGenFilesArr)) {
					logger.info("可以生成模版代码：" + template);
				} else {
					if (FileUtil.exist(path)) {
						logger.info("模版代码：" + template + "-已经修改，不进行覆盖：");
						continue;
					} else {
						logger.info("模版代码：" + template + "-尚未修改，生成文件：" + path);
					}
				}

				// if (StringUtils.contains(template, "serviceExtend.java.vm")) {
				// 	if (StrUtil.containsAny(template, canGenFilesArr)) {
				// 		logger.info("手动选择可以生成模版代码：" + template);
				// 	} else {
				// 		if (FileUtil.exist(path)) {
				// 			logger.info("serviceExtend.java文件已经存在，不进行覆盖：" + path);
				// 			continue;
				// 		} else {
				// 			logger.info("serviceExtend.java文件尚不存在，生成文件：" + path);
				// 		}
				// 	}
				// }
				// if (StringUtils.contains(template, "apiExtend.js.vm")) {
				// 	if (StrUtil.containsAny(template, canGenFilesArr)) {
				// 		logger.info("手动选择可以生成模版代码：" + template);
				// 	} else {
				// 		if (FileUtil.exist(path)) {
				// 			logger.info("apiExtend.js文件已经存在，不进行覆盖：" + path);
				// 			continue;
				// 		} else {
				// 			logger.info("apiExtend.js文件尚不存在，生成文件：" + path);
				// 		}
				// 	}
				// }
				// if (StringUtils.contains(template, "mapperExtend.java.vm")) {
				// 	if (StrUtil.containsAny(template, canGenFilesArr)) {
				// 		logger.info("手动选择可以生成模版代码：" + template);
				// 	} else {
				// 		if (FileUtil.exist(path)) {
				// 			logger.info("mapperExtend.java文件已经存在，不进行覆盖：" + path);
				// 			continue;
				// 		} else {
				// 			logger.info("mapperExtend.java文件尚不存在，生成文件：" + path);
				// 		}
				// 	}
				// }
				// if (StringUtils.contains(template, "mapperExtend.xml.vm")) {
				// 	if (StrUtil.containsAny(template, canGenFilesArr)) {
				// 		logger.info("手动选择可以生成模版代码：" + template);
				// 	} else {
				// 		if (FileUtil.exist(path)) {
				// 			logger.info("mapperExtend.xml文件已经存在，不进行覆盖：" + path);
				// 			continue;
				// 		} else {
				// 			logger.info("mapperExtend.xml文件尚不存在，生成文件：" + path);
				// 		}
				// 	}
				// }

				logger.info("[获取]-模版=[{}]的生成路径：{}", template, path);
				FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
				throw new ServiceException("渲染模板失败，表名：" + table.getTableName());
			}
			// }
		}
	}

	@Autowired
	private IGenTableService genTableService;

	/**
	 * 同步数据库 Double
	 * @param tableName 表名称
	 */
	@Override
	@Transactional
	public void synchDb(String tableName) {
		String[] atableName = {tableName};
		List<GenTable> tableList = genTableService.selectDbTableListByNames(atableName);

		GenTable oldTable = genTableMapper.selectGenTableByName(tableName);

		if (tableList.size()>0) {
			String tableComment = tableList.get(0).getTableComment();
			oldTable.setFunctionName(GenUtils.replaceText(tableComment));
			genTableMapper.updateGenTable(oldTable);
		}

		List<GenTableColumn> oldTableColumns = oldTable.getColumns();
		Map<String, GenTableColumn> oldTableColumnMap = oldTableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumnName, Function.identity()));

		List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
		if (StringUtils.isEmpty(dbTableColumns)) {
			throw new ServiceException("同步数据失败，原表结构不存在");
		}
		List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

		GenTableColumn newColumn = null;
		for (int i = 0; i < dbTableColumns.size(); i++) {
			newColumn = dbTableColumns.get(i);
			logger.info("[获取]-列[{}]-是否必填={}", newColumn.getColumnName(), newColumn.getIsRequired());
			// }
			// dbTableColumns.forEach(newColumn -> {
			GenUtils.initColumnField(newColumn, oldTable, 999);
			if (oldTableColumnMap.containsKey(newColumn.getColumnName())) {
				GenTableColumn oldColumn = oldTableColumnMap.get(newColumn.getColumnName());
				newColumn.setColumnId(oldColumn.getColumnId());

				// 保留之前的配置信息
				// 保留列扩展配置信息
				if (StrUtil.isNotBlank(oldColumn.getExtend8())) {
					newColumn.setExtend8(oldColumn.getExtend8());
				}

				// 如果之前为下拉框继续保留为下拉框
				if (StrUtil.equals(oldColumn.getHtmlType(), GenConstants.HTML_SELECT)) {
					newColumn.setHtmlType(oldColumn.getHtmlType());
				}
				if (StrUtil.equals(oldColumn.getHtmlType(), GenConstants.HTML_SELECT_SELF_DEFINE)) {
					newColumn.setHtmlType(oldColumn.getHtmlType());
					newColumn.setDictType(oldColumn.getDictType());
				}

				if (newColumn.isList()) {
					// 如果是列表，继续保留查询方式/字典类型选项
					if (StrUtil.isBlank(newColumn.getDictType()) && StrUtil.isNotBlank(oldColumn.getDictType())) {
						newColumn.setDictType(oldColumn.getDictType());
					}
					if (StrUtil.isBlank(newColumn.getQueryType()) && StrUtil.isNotBlank(oldColumn.getQueryType())) {
						newColumn.setQueryType(oldColumn.getQueryType());
					}
				}

				// 继续保留必填
				if (StrUtil.equals(newColumn.getIsRequired(), "0") && StrUtil.equals(oldColumn.getIsRequired(), "1")) {
					newColumn.setIsRequired("1");
				}
				// // 继续保留显示类型选项
				// if (StrUtil.isBlank(newColumn.getHtmlType()) && StrUtil.isNotBlank(oldColumn.getHtmlType())) {
				// 	newColumn.setHtmlType(oldColumn.getHtmlType());
				// }
				// if (StrUtil.isNotBlank(newColumn.getDictType())) {
				// 	newColumn.setHtmlType(GenConstants.HTML_SELECT);
				// }

				//继续保留是否插入，如果之前字段设置为不显示，则同步数据库后仍设置为不显示
				if (StrUtil.isNotBlank(oldColumn.getIsInsert())) {
					// if (StrUtil.isBlank(newColumn.getIsInsert()) && StrUtil.isNotBlank(oldColumn.getIsInsert())) {
					newColumn.setIsInsert(oldColumn.getIsInsert());
				}
				//继续保留是否编辑，如果之前字段设置为不显示，则同步数据库后仍设置为不显示
				if (StrUtil.isNotBlank(oldColumn.getIsEdit())) {
					// if (StrUtil.isBlank(newColumn.getIsEdit()) && StrUtil.isNotBlank(oldColumn.getIsEdit())) {
					newColumn.setIsEdit(oldColumn.getIsEdit());
				}
				//继续保留是否导出
				newColumn.setExtend5(oldColumn.getExtend5());
				//继续保留是否导入
				newColumn.setExtend6(oldColumn.getExtend6());
				//继续保留校验
				newColumn.setExtend7(oldColumn.getExtend7());
				//继续保留是否详细，如果之前字段设置为不显示，则同步数据库后仍设置为不显示
				// if (StrUtil.isNotBlank(oldColumn.getIsDetail())) {
				// if (StrUtil.isBlank(newColumn.getIsEdit()) && StrUtil.isNotBlank(oldColumn.getIsEdit())) {
				newColumn.setIsDetail(oldColumn.getIsDetail());
				newColumn.setIsAppDetail(oldColumn.getIsAppDetail());
				// }
				//继续保留是否只读 Fuxs
				// if (StrUtil.isBlank(newColumn.getIsReadonly()) && StrUtil.isNotBlank(oldColumn.getIsReadonly())) {
				// 	newColumn.setIsReadonly(oldColumn.getIsReadonly());
				// }
				// //继续保留disabled Fuxs
				// if (StrUtil.isBlank(newColumn.getIsDisabled()) && StrUtil.isNotBlank(oldColumn.getIsDisabled())) {
				// 	newColumn.setIsDisabled(oldColumn.getIsDisabled());
				// }
				// //继续保留是否详细 Fuxs
				// if (StrUtil.isBlank(newColumn.getIsDetail()) && StrUtil.isNotBlank(oldColumn.getIsDetail())) {
				// 	newColumn.setIsDetail(oldColumn.getIsDetail());
				// }
				//继续保留是否列表显示 Fuxs
				if (StrUtil.isNotBlank(oldColumn.getIsList())) {
					newColumn.setIsList(oldColumn.getIsList());
				}
				if (StrUtil.isNotBlank(oldColumn.getIsAppList())) {
					newColumn.setIsAppList(oldColumn.getIsAppList());
				}
				//继续保留是否查询 Fuxs
				if (StrUtil.isBlank(newColumn.getIsQuery()) && StrUtil.isNotBlank(oldColumn.getIsQuery())) {
					newColumn.setIsQuery(oldColumn.getIsQuery());
				}
				if (StrUtil.isBlank(newColumn.getIsAppQuery()) && StrUtil.isNotBlank(oldColumn.getIsAppQuery())) {
					newColumn.setIsAppDetail(oldColumn.getIsAppQuery());
				}
				//继续保留查询方式 Fuxs
				if (StrUtil.isBlank(newColumn.getQueryType()) && StrUtil.isNotBlank(oldColumn.getQueryType())) {
					newColumn.setQueryType(oldColumn.getQueryType());
				}

				//继续保留是否必填 Fuxs
				if (StrUtil.isBlank(newColumn.getIsRequired()) && StrUtil.isNotBlank(oldColumn.getIsRequired())) {
					newColumn.setIsRequired(oldColumn.getIsRequired());
				}

				// //继续保留字典类型 Fuxs
				// if (StrUtil.isBlank(newColumn.getDictType()) && StrUtil.isNotBlank(oldColumn.getDictType())) {
				// 	newColumn.setDictType(oldColumn.getDictType());
				// }
				// //继续保留扩展配置 Fuxs
				// if (StrUtil.isBlank(newColumn.getExtend()) && StrUtil.isNotBlank(oldColumn.getExtend())) {
				// 	newColumn.setExtend(oldColumn.getExtend());
				// }
				// //继续保留以下配置 Fuxs
				// if (StrUtil.isBlank(newColumn.getIfHasRoleShowDetail()) && StrUtil.isNotBlank(oldColumn.getIfHasRoleShowDetail())) {
				// 	newColumn.setIfHasRoleShowDetail(oldColumn.getIfHasRoleShowDetail());
				// }
				// if (StrUtil.isBlank(newColumn.getFirstLevelGridHead()) && StrUtil.isNotBlank(oldColumn.getFirstLevelGridHead())) {
				// 	newColumn.setFirstLevelGridHead(oldColumn.getFirstLevelGridHead());
				// }
				// if (StrUtil.isBlank(newColumn.getSecondLevelGridHead()) && StrUtil.isNotBlank(oldColumn.getSecondLevelGridHead())) {
				// 	newColumn.setSecondLevelGridHead(oldColumn.getSecondLevelGridHead());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend1()) && StrUtil.isNotBlank(oldColumn.getExtend1())) {
				// 	newColumn.setExtend1(oldColumn.getExtend1());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend2()) && StrUtil.isNotBlank(oldColumn.getExtend2())) {
				// 	newColumn.setExtend2(oldColumn.getExtend2());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend3()) && StrUtil.isNotBlank(oldColumn.getExtend3())) {
				// 	newColumn.setExtend3(oldColumn.getExtend3());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend4()) && StrUtil.isNotBlank(oldColumn.getExtend4())) {
				// 	newColumn.setExtend4(oldColumn.getExtend4());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend5()) && StrUtil.isNotBlank(oldColumn.getExtend5())) {
				// 	newColumn.setExtend5(oldColumn.getExtend5());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend6()) && StrUtil.isNotBlank(oldColumn.getExtend6())) {
				// 	newColumn.setExtend6(oldColumn.getExtend6());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend7()) && StrUtil.isNotBlank(oldColumn.getExtend7())) {
				// 	newColumn.setExtend7(oldColumn.getExtend7());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend8()) && StrUtil.isNotBlank(oldColumn.getExtend8())) {
				// 	newColumn.setExtend8(oldColumn.getExtend8());
				// }
				// if (StrUtil.isBlank(newColumn.getExtend9()) && StrUtil.isNotBlank(oldColumn.getExtend9())) {
				// 	newColumn.setExtend9(oldColumn.getExtend9());
				// }
				// //原来勾销了唯一时，继续保留唯一校验字段 Fuxs
				// if ("1".equals(oldColumn.getIsUnique())) {
				// 	newColumn.setIsUnique(oldColumn.getIsUnique());
				// }

				// Fuxs 设置下拉框转码和其他信息
				setColSelectInfoAndOtherInfo(newColumn, oldTable);

				genTableColumnMapper.updateGenTableColumn(newColumn);
			} else {
				// 同步时，如果是新增的字段，或者字典名称修改了，则前6个字段默认选中以下配置
				if (i<6) {
					newColumn.setIsList(GenConstants.YES);
					newColumn.setIsQuery(GenConstants.YES);
					newColumn.setIsDetail(GenConstants.YES);
					newColumn.setIsInsert(GenConstants.YES);
					newColumn.setIsEdit(GenConstants.YES);
					newColumn.setExtend5(GenConstants.YES);
					newColumn.setExtend6(GenConstants.YES);
					newColumn.setIsAppQuery(GenConstants.YES);
					// newColumn.setIsAppList(GenConstants.YES);
					newColumn.setIsAppDetail(GenConstants.YES);
				}
				genTableColumnMapper.insertGenTableColumn(newColumn);
			}
			// });
		}

		List<GenTableColumn> delColumns = oldTableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
		if (StringUtils.isNotEmpty(delColumns)) {
			genTableColumnMapper.deleteGenTableColumns(delColumns);
		}
	}
	// /**
	//  * 同步数据库
	//  * @param tableName 表名称
	//  */
	// @Override
	// @Transactional
	// public void synchDb(String tableName) {
	// 	GenTable table = genTableMapper.selectGenTableByName(tableName);
	// 	List<GenTableColumn> tableColumns = table.getColumns();
	// 	Map<String, GenTableColumn> tableColumnMap = tableColumns.stream().collect(Collectors.toMap(GenTableColumn::getColumnName, Function.identity()));
	//
	// 	List<GenTableColumn> dbTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
	// 	if (StringUtils.isEmpty(dbTableColumns)) {
	// 		throw new ServiceException("同步数据失败，原表结构不存在");
	// 	}
	// 	List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());
	//
	// 	// dbTableColumns.forEach(column -> {
	// 	// 	GenUtils.initColumnField(column, table, 999);
	// 	// 	if (tableColumnMap.containsKey(column.getColumnName())) {
	// 	// 		GenTableColumn prevColumn = tableColumnMap.get(column.getColumnName());
	// 	// 		column.setColumnId(prevColumn.getColumnId());
	// 	// 		if (column.isList()) {
	// 	// 			// 如果是列表，继续保留查询方式/字典类型选项
	// 	// 			column.setDictType(prevColumn.getDictType());
	// 	// 			column.setQueryType(prevColumn.getQueryType());
	// 	// 		}
	// 	// 		if (StringUtils.isNotEmpty(prevColumn.getIsRequired()) && !column.isPk()
	// 	// 				&& (column.isInsert() || column.isEdit())
	// 	// 				&& ((column.isUsableColumn()) || (!column.isSuperColumn()))) {
	// 	// 			// 如果是(新增/修改&非主键/非忽略及父属性)，继续保留必填/显示类型选项
	// 	// 			column.setIsRequired(prevColumn.getIsRequired());
	// 	// 			column.setHtmlType(prevColumn.getHtmlType());
	// 	// 		}
	// 	//
	// 	// 		// Double 设置下拉框转码信息
	// 	// 		setColSelectInfo(column);
	// 	//
	// 	// 		genTableColumnMapper.updateGenTableColumn(column);
	// 	// 	} else {
	// 	// 		genTableColumnMapper.insertGenTableColumn(column);
	// 	// 	}
	// 	// });
	//
	// 	List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
	// 	if (StringUtils.isNotEmpty(delColumns)) {
	// 		genTableColumnMapper.deleteGenTableColumns(delColumns);
	// 	}
	// }

	/**
	 * 批量生成代码（下载方式）
	 * @param tableNames 表数组
	 * @return 数据
	 */
	@Override
	public byte[] downloadCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (String tableName : tableNames) {
			generatorCode(tableName, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/**
	 * 查询表信息并生成代码
	 */
	private void generatorCode(String tableName, ZipOutputStream zip) {
		// 查询表信息
		GenTable table = genTableMapper.selectGenTableByName(tableName);
		// 设置主子表信息
		setSubTable(table);
		// 设置主键列信息
		setPkColumn(table);

		// 通过备注字段解析额外的配置信息：如排序字符串 Fuxs
		// 新增模板用字段后，需要在下面的VelocityUtils.prepareContext方法中设置对应的模板字段
		String remark = table.getRemark();
		GenTableExtendInfo genTableExtendInfo = new GenTableExtendInfo();
		if (!StrUtil.isBlankOrUndefined(remark)) {
			try {
				genTableExtendInfo = JSONUtil.toBean(remark, GenTableExtendInfo.class);
			} catch (Exception e) {
				logger.warn("[提示]-解析传入的备注信息失败！");
				throw new RuntimeException("解析传入的备注信息失败！" + remark);
			}
			// 新增模板用字段后，需要在下面的VelocityUtils.prepareContext方法中设置对应的模板字段
			table.setGenTableExtendInfo(genTableExtendInfo);
			logger.info("[获取]-配置的代码生成扩展信息=[{}]", JSONUtil.toJsonStr(table.getGenTableExtendInfo()));
		}

		VelocityInitializer.initVelocity();

		VelocityContext context = VelocityUtils.prepareContext(table);

		// 获取模板列表
		List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getTplWebType());
		for (String template : templates) {
			// 渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, Constants.UTF8);
			tpl.merge(context, sw);
			try {
				// 添加到zip
				zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
				IOUtils.write(sw.toString(), zip, Constants.UTF8);
				IOUtils.closeQuietly(sw);
				zip.flush();
				zip.closeEntry();
			} catch (IOException e) {
				logger.error("渲染模板失败，表名：" + table.getTableName(), e);
			}
		}
	}

	/**
	 * 修改保存参数校验
	 * @param genTable 业务信息
	 */
	@Override
	public void validateEdit(GenTable genTable) {
		if (GenConstants.TPL_TREE.equals(genTable.getTplCategory())) {
			String options = JSON.toJSONString(genTable.getParams());
			JSONObject paramsObj = JSON.parseObject(options);
			if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE))) {
				throw new ServiceException("树编码字段不能为空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE))) {
				throw new ServiceException("树父编码字段不能为空");
			} else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME))) {
				throw new ServiceException("树名称字段不能为空");
			} else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory())) {
				if (StringUtils.isEmpty(genTable.getSubTableName())) {
					throw new ServiceException("关联子表的表名不能为空");
				} else if (StringUtils.isEmpty(genTable.getSubTableFkName())) {
					throw new ServiceException("子表关联的外键名不能为空");
				}
			}
		}

		for (GenTableColumn column : genTable.getColumns()) {
			if (column.getHtmlType().equals(GenConstants.HTML_SELECT) && column.getDictType().isEmpty()) {
				throw new ServiceException("字段【" + column.getColumnComment() + "】为下拉框，需要配置对应的字典类型！");
			}
		}
	}

	/**
	 * 设置主键列信息
	 * @param table 业务表信息
	 */
	public void setPkColumn(GenTable table) {
		for (GenTableColumn column : table.getColumns()) {
			if (column.isPk()) {
				table.setPkColumn(column);
				break;
			}
		}
		if (StringUtils.isNull(table.getPkColumn())) {
			table.setPkColumn(table.getColumns().get(0));
		}
		if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
			for (GenTableColumn column : table.getSubTable().getColumns()) {
				if (column.isPk()) {
					table.getSubTable().setPkColumn(column);
					break;
				}
			}
			if (StringUtils.isNull(table.getSubTable().getPkColumn())) {
				table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
			}
		}
	}

	/**
	 * 设置主子表信息
	 * @param table 业务表信息
	 */
	public void setSubTable(GenTable table) {
		String subTableName = table.getSubTableName();
		if (StringUtils.isNotEmpty(subTableName)) {
			table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
		}
	}

	/**
	 * 设置代码生成其他选项值
	 * @param genTable 设置后的生成对象
	 */
	public void setTableFromOptions(GenTable genTable) {
		JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
		if (StringUtils.isNotNull(paramsObj)) {
			String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
			String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
			String treeName = paramsObj.getString(GenConstants.TREE_NAME);
			String parentMenuId = paramsObj.getString(GenConstants.PARENT_MENU_ID);
			String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);

			genTable.setTreeCode(treeCode);
			genTable.setTreeParentCode(treeParentCode);
			genTable.setTreeName(treeName);
			genTable.setParentMenuId(parentMenuId);
			genTable.setParentMenuName(parentMenuName);
		}
	}

	/**
	 * 获取代码生成地址
	 * @param table    业务表信息
	 * @param template 模板文件路径
	 * @return 生成地址
	 */
	public static String getGenPath(GenTable table, String template) {
		String genPath = table.getGenPath();
		if (StringUtils.equals(genPath, "/")) {
			return System.getProperty("user.dir") + File.separator + VelocityUtils.getFileName(template, table);
		}
		return genPath + File.separator + VelocityUtils.getFileName(template, table);
	}
}
