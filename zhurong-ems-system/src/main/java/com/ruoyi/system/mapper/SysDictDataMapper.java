package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author cpems
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictDataMapper, SysDictData, SysDictData> {

    default List<SysDictData> selectDictDataByType(String dictType) {
        return selectList(
            new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getStatus, UserConstants.DICT_NORMAL)
                .eq(SysDictData::getDictType, dictType)
                .orderByAsc(SysDictData::getDictSort));
    }


    public String selectDictValueByDictLabel(@Param("dictType") String dictType, @Param("dictLabel") String dictLabel);

	public List<SysDictData> selectListByDictLabel(@Param("dictType") String dictType, @Param("dictLabel") String dictLabel);

	public List<SysDictData> selectListByDictValue(@Param("dictType") String dictType, @Param("dictValue") String dictValue);

	public String selectDictValueByDictTypeAndDictLabel(@Param("dictType") String dictType, @Param("dictLabel") String dictLabel);

	public String selectAllDictLableStrByDictType(@Param("dictType") String dictType);

	public int selectMaxDictSortByDictType(@Param("dictType") String dictType);
}
