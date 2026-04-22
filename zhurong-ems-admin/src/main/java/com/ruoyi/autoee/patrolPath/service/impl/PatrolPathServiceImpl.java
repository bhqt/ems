package com.ruoyi.autoee.patrolPath.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import javax.validation.Validator;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.dao.DataIntegrityViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import com.ruoyi.common.exception.ServiceException;
import java.util.List;
import java.util.HashMap;
import java.util.Date;
import com.ruoyi.common.utils.StringUtils;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.service.CommonService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.dao.DuplicateKeyException;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapper;
import com.ruoyi.autoee.patrolPath.mapper.PatrolPathMapperExtend;
import com.ruoyi.autoee.patrolPath.domain.PatrolPath;
import com.ruoyi.autoee.patrolPath.service.IPatrolPathService;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.common.utils.ValidateUtils;

/**
 * 巡更路线Service业务层处理
 *
 * @author AutoEE亿达科技有限公司 版权所有 Spring全栈工程师
 *
 */
@Service
public class PatrolPathServiceImpl implements IPatrolPathService
{
	private static final Logger logger = LoggerFactory.getLogger(PatrolPathServiceImpl.class);
    @Autowired
    private PatrolPathMapper patrolPathMapper;
    @Autowired
    private PatrolPathMapperExtend patrolPathMapperExtend;
    @Autowired
    private PatrolPathServiceExtend patrolPathServiceExtend;
	@Autowired
    protected Validator validator;
	@Autowired
    private ISysDictDataService iSysDictDataService;
	@Autowired
    private CommonService commonService;

    /**
     * 通过主键查询巡更路线
     *
     * @param id 巡更路线主键
     * @return 巡更路线
     */
    @Override
    public PatrolPath selectDataByPkPatrolPath(Long id)
    {
        return patrolPathMapper.selectDataByPkPatrolPath(id);
    }

    /**
     * 通过主键查询巡更路线详细信息
     *
     * @param id 巡更路线主键
     * @return 巡更路线
     */
    @Override
    public PatrolPath selectDetailByPkPatrolPath(Long id)
    {
		PatrolPath patrolPath = patrolPathMapper.selectDetailByPkPatrolPath(id);
		patrolPathServiceExtend.selectDetailByPkPatrolPathEndExtend(patrolPath);
        return patrolPath;
    }

    /**
     * 查询巡更路线列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线
     */
    @Override
    public List<PatrolPath> selectDataListByLikePatrolPath(PatrolPath patrolPath)
    {
		patrolPathServiceExtend.selectListStartExtend(patrolPath);
		List<PatrolPath> list = patrolPathMapper.selectDataListByLikePatrolPath(patrolPath);
		patrolPathServiceExtend.selectListEndExtend(patrolPath, list);
        return list;
    }

    /**
     * 精确查询巡更路线列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDataListByEqPatrolPath(PatrolPath patrolPath)
	{
		patrolPathServiceExtend.selectListStartExtend(patrolPath);
		List<PatrolPath> list = patrolPathMapper.selectDataListByEqPatrolPath(patrolPath);
		patrolPathServiceExtend.selectListEndExtend(patrolPath, list);
        return list;
    }

	/**
     * 查询巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDetailListByLikePatrolPath(PatrolPath patrolPath)
	{
		patrolPathServiceExtend.selectDetailListStartExtend(patrolPath);
		List<PatrolPath> list = patrolPathMapper.selectDetailListByLikePatrolPath(patrolPath);
		patrolPathServiceExtend.selectDetailListEndExtend(patrolPath, list);
        return list;
    }

    /**
     * 精确查询巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public List<PatrolPath> selectDetailListByEqPatrolPath(PatrolPath patrolPath)
	{
		patrolPathServiceExtend.selectDetailListByEqPatrolPathStartExtend(patrolPath);
		List<PatrolPath> list = patrolPathMapper.selectDetailListByEqPatrolPath(patrolPath);
		patrolPathServiceExtend.selectDetailListByEqPatrolPathEndExtend(patrolPath, list);
        return list;
    }

	/**
     * 导出巡更路线详细列表
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
	public List<PatrolPath> selectExportDetailListPatrolPath(PatrolPath patrolPath){
		patrolPathServiceExtend.selectExportDetailListStartExtend(patrolPath);
		List<PatrolPath> list = patrolPathMapper.selectDetailListByLikePatrolPath(patrolPath);
		patrolPathServiceExtend.selectExportDetailListEndExtend(patrolPath, list);
        return list;
	}

	/**
     * 模糊查询记录数
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public int selectCountByLikePatrolPath(PatrolPath patrolPath){
		return patrolPathMapper.selectCountByLikePatrolPath(patrolPath);
	}

	/**
     * 精确查询记录数
     *
     * @param patrolPath 巡更路线
     * @return 巡更路线集合
     */
    public int selectCountByEqPatrolPath(PatrolPath patrolPath){
		return patrolPathMapper.selectCountByEqPatrolPath(patrolPath);
	}

	/**
     * 导出前校验
     */
    public void exportDataCheck(PatrolPath patrolPath){
		patrolPathServiceExtend.exportDataCheckExtend(patrolPath);
	}

	/**
     * 导出数据预处理
     */
	 public void exportDataDeal(PatrolPath patrolPath, List<PatrolPath> list){
		 patrolPathServiceExtend.exportDataDealExtend(patrolPath, list);
	 }

    /**
     * 新增巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    @Override
	@Transactional
    public int insertPatrolPath(PatrolPath patrolPath)
    {
        patrolPath.setCreateTime(DateUtils.getNowDate());
		patrolPath.setUpdateTime(patrolPath.getCreateTime());
        patrolPathServiceExtend.insertStartExtend( patrolPath);
		int rows = 0;
 		rows = patrolPathMapper.insertPatrolPath(patrolPath);
		patrolPathServiceExtend.insertEndExtend( patrolPath, rows);
        return Integer.parseInt(patrolPath.getId()+"");
    }

	/**
     * 批量新增修改巡更路线
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
	@Override
	@Transactional
    public void batchInsertOrUpdateByPatrolPath(List<PatrolPath> patrolPaths){
		int batchSize = 1000; // 每次插入的数据量
		int totalSize = patrolPaths.size();
		for (int i = 0; i < totalSize; i += batchSize) {
			int toIndex = Math.min(i + batchSize, totalSize);
			List<PatrolPath> batchList = patrolPaths.subList(i, toIndex);
			patrolPathMapper.batchInsertOrUpdateByPatrolPath(batchList);
		}
	}

    /**
     * 修改巡更路线：只能用于前端form表单的更新操作，清空的字段回写为null
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNullValueByPatrolPath(PatrolPath patrolPath)
    {
		PatrolPath oldPatrolPath = patrolPathMapper.selectDataByPkPatrolPath(patrolPath.getId());
        patrolPathServiceExtend.updateStartExtend( patrolPath, oldPatrolPath);
		int rows = 0;
		rows = patrolPathMapper.updateNullValueByPatrolPath(patrolPath);
		patrolPathServiceExtend.updateEndExtend( patrolPath,oldPatrolPath, rows);
        return rows;
    }

	/**
     * 更新设置值的字段，未设置值的字段不进行更新
     *
     * @param patrolPath 巡更路线
     * @return 结果
     */
    @Override
    @Transactional
    public int updateNotNullValueByPatrolPath(PatrolPath patrolPath)
    {
		PatrolPath oldPatrolPath = patrolPathMapper.selectDataByPkPatrolPath(patrolPath.getId());
        patrolPathServiceExtend.updateStartExtend( patrolPath, oldPatrolPath);
		int rows = 0;
		rows = patrolPathMapper.updateNotNullValueByPatrolPath(patrolPath);
		patrolPathServiceExtend.updateEndExtend( patrolPath,oldPatrolPath, rows);
        return rows;
    }

	/**
     * 删除PatrolPathById
     *
     * @param id 巡更路线主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPathById(PatrolPath patrolPath)
    {
		try {
        	return patrolPathMapper.deletePatrolPathById(patrolPath);
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

    /**
     * 批量删除PatrolPathByIds
     *
     * @param ids 需要删除的巡更路线主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deletePatrolPathByIds(PatrolPath patrolPath) {
		try {
			patrolPathServiceExtend.deleteByIdsStartExtend(patrolPath);
			int rows = patrolPathMapper.deletePatrolPathByIds(patrolPath);
			patrolPathServiceExtend.deleteByIdsEndExtend(patrolPath, rows);
			return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
    }

	/**
     * 批量删除PatrolPathByEqPatrolPath
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolPathByEqPatrolPath(PatrolPath patrolPath){
		try {
			patrolPathServiceExtend.deleteByEqPatrolPathStartExtend(patrolPath);
			int rows = patrolPathMapper.deletePatrolPathByEqPatrolPath(patrolPath);
			patrolPathServiceExtend.deleteByEqPatrolPathEndExtend(patrolPath, rows);
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}

	/**
     * 删除全部数据
     *
     * @return 结果
     */
	@Override
	@Transactional
    public int deletePatrolPathAllData(){
		try {
			int rows = patrolPathMapper.deletePatrolPathAllData();
       	 	return rows;
		} catch (DataIntegrityViolationException e) {
			// 提取根本原因
			Throwable rootCause = e.getRootCause();
			if (rootCause instanceof SQLIntegrityConstraintViolationException) {
				SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) rootCause;
				// MySQL 错误码 1451 表示外键约束冲突
				if (sqlEx.getErrorCode() == 1451) {
					throw new ServiceException("删除失败：当前删除记录下存在关联信息！请先删除所有关联信息后再进行删除。");
				}
			}
			// 其他类型异常继续抛出
			throw e;
		}
	}



   /**
     * 导入数据
     *
     * @param dataList 数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
	// @Transactional 导入不开启事务，避免事务过大，每批次写入后即提交事务，数据导入一半，可以重新导入时自动更新
    public String importPatrolPathData(List<PatrolPath> dataList, Boolean isUpdateSupport, String operName, PatrolPath pPatrolPath)
    {
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
		String headString = "<br> - ";
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
		PatrolPath patrolPath = new PatrolPath();
       	Date nowDate = DateUtils.getNowDate();
		// 导入前处理 - 如设置子表关联的父表id，自定义下拉框的转码
		patrolPathServiceExtend.importDataStartExtend(dataList, pPatrolPath, operName, nowDate);

		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> patrolRouteStatusLableValueMap =  commonService.getDictLableValueMap("patrol_route_status");
		String  patrolRouteStatusAllDictLableStr = commonService.getDictAllLableStr("patrol_route_status");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> pointListLableValueMap =  commonService.getDictLableValueMap("a_patrol_point");
		String  pointListAllDictLableStr = commonService.getDictAllLableStr("a_patrol_point");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> userIdLableValueMap =  commonService.getDictLableValueMap("sys_user");
		String  userIdAllDictLableStr = commonService.getDictAllLableStr("sys_user");
		// 如果是下拉框类型，循环前先获取出来，不用每条导入记录都进行获取
		HashMap<String, String> deptIdLableValueMap =  commonService.getDictLableValueMap("sys_dept");
		String  deptIdAllDictLableStr = commonService.getDictAllLableStr("sys_dept");


		// 将导入的汉字转为对应字典的value后存入数据库
		String dictValue = "";
        for (int i=0;i<dataList.size();i++) {
	        String checkMsg = "";
	        try {
				patrolPath =dataList.get(i);
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolPath.getRouteCode())){
					checkMsg += headString + "路线编号字段为必填项。";
				}
		        // 检查必填字段：字符串类型知道
		        if (StrUtil.isBlank(patrolPath.getRouteName())){
					checkMsg += headString + "路线名称字段为必填项。";
				}
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolPath.getPatrolRouteStatusExtend())){
					checkMsg += headString + "状态字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String patrolRouteStatus = patrolPath.getPatrolRouteStatusExtend();
                if (StrUtil.isNotBlank(patrolRouteStatus)) {
					// 通过名称取对应的字典值
                    dictValue = patrolRouteStatusLableValueMap.get(patrolRouteStatus);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolPath.setPatrolRouteStatus(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(patrolRouteStatusLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(patrolRouteStatusAllDictLableStr)) {
                        	checkMsg += headString + "状态字段的录入值["+ patrolRouteStatus +"]必须属于以下取值范围["+patrolRouteStatusAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "状态字段的录入值["+ patrolRouteStatus +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }
		        // 检查必填字段：如果是下拉框、自定义下拉框，直接取对应的扩展字段进行校验
		        if (StrUtil.isBlank(patrolPath.getPointListExtend())){
					checkMsg += headString + "巡更点列表字段为必填项。";
				}
				 // 如果是下拉框、自定义下拉框，检查对应的扩展字段录入值
                String pointList = patrolPath.getPointListExtend();
                if (StrUtil.isNotBlank(pointList)) {
					// 通过名称取对应的字典值
                    dictValue = pointListLableValueMap.get(pointList);
                    if (StrUtil.isNotBlank(dictValue)) {
                		// 如果是下拉框、自定义下拉框，值类型不为String的录入值
						patrolPath.setPointList(dictValue);
                    } else {
						//allDictLableStr = CollUtil.join(pointListLableValueMap.keySet(), ",");// 通过map获取没有排序
 		              	if (StrUtil.isNotBlank(pointListAllDictLableStr)) {
                        	checkMsg += headString + "巡更点列表字段的录入值["+ pointList +"]必须属于以下取值范围["+pointListAllDictLableStr+"]。";
						} else {
							   // 对于不能将全部选项返回给前端的情况
							checkMsg += headString + "巡更点列表字段的录入值["+ pointList +"]在系统中不存在，请确认后修改。";
		                }
                    }
                }

		        // 检查完必填字段和取值范围后，在进行重复记录校验
		        if ("".equals(checkMsg)) {
			        // 进行导入扩展校验
			        checkMsg = patrolPathServiceExtend.importDataCheckExtend(isUpdateSupport, pPatrolPath, patrolPath, operName);
			        if (!"".equals(checkMsg)) {
				       checkMsg = headString + checkMsg;
			        } else {
				        BeanValidators.validateWithException(validator, patrolPath);
			        }
		        }

	        } catch (Exception e) {
		        checkMsg += headString + "数据导入时出现异常！" + e.getMessage();
		        logger.error("数据导入时出现异常！", e);
	        }
	        if (!"".equals(checkMsg)) {
		        failureNum++;
		        String msg = "<br/>▶第【" + (i + 1) + "】条数据导入失败：" + checkMsg;
		        failureMsg.append(msg);
	        } else{
				successNum++;
	        }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共有 " + failureNum + " 条数据存在问题，请修正后再重新导入。具体如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
			try{
				// 批量插入数据
				this.batchInsertOrUpdateByPatrolPath(dataList);
				successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条。");
			} catch (Exception e){
				logger.error("数据导入进行批量写入时出现异常！", e);
				throw new ServiceException("数据导入进行批量写入时出现异常！" + e.getCause ());
			}
        }
		patrolPathServiceExtend.importDataEndExtend(dataList, pPatrolPath, operName, successNum);
        return successMsg.toString();
    }



}
