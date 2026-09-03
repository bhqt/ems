package com.ruoyi.system.hospital.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.domain.dto.RoleDTO;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.DataScopeType;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.hospital.service.IHospitalDataScopeService;
import com.ruoyi.system.service.ISysDataScopeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 医院数据权限（多院区隔离）Service 实现
 *
 * @author cpems
 */
@Service
public class HospitalDataScopeServiceImpl implements IHospitalDataScopeService {

    @Override
    public boolean isUnrestricted() {
        if (LoginHelper.isAdmin()) {
            return true;
        }
        LoginUser user = LoginHelper.getLoginUser();
        if (user == null || CollUtil.isEmpty(user.getRoles())) {
            return false;
        }
        for (RoleDTO role : user.getRoles()) {
            DataScopeType type = DataScopeType.findCode(role.getDataScope());
            if (type == DataScopeType.ALL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> resolveAccessibleAreas() {
        // 超级管理员或全部数据范围 → 不限
        if (isUnrestricted()) {
            return null;
        }
        LoginUser user = LoginHelper.getLoginUser();
        if (user == null || CollUtil.isEmpty(user.getRoles())) {
            return new HashSet<>();
        }
        ISysDataScopeService sdss = SpringUtils.getBean(ISysDataScopeService.class);
        Set<String> areas = new HashSet<>();
        for (RoleDTO role : user.getRoles()) {
            DataScopeType type = DataScopeType.findCode(role.getDataScope());
            if (type == null) {
                continue;
            }
            switch (type) {
                case DEPT:
                    addIfNotBlank(areas, ObjectUtil.isNull(user.getDeptId()) ? null : String.valueOf(user.getDeptId()));
                    break;
                case DEPT_AND_CHILD:
                    addStrList(areas, sdss.getDeptAndChild(user.getDeptId()));
                    break;
                case CUSTOM:
                    addStrList(areas, sdss.getRoleCustom(role.getRoleId()));
                    break;
                case SELF:
                    addIfNotBlank(areas, ObjectUtil.isNull(user.getDeptId()) ? null : String.valueOf(user.getDeptId()));
                    break;
                default:
                    break;
            }
        }
        return areas;
    }

    private void addStrList(Set<String> target, String csv) {
        if (StrUtil.isBlank(csv)) {
            return;
        }
        target.addAll(Arrays.stream(csv.split(","))
            .map(String::trim).filter(StrUtil::isNotBlank).collect(Collectors.toSet()));
    }

    private void addIfNotBlank(Set<String> target, String v) {
        if (StrUtil.isNotBlank(v)) {
            target.add(v);
        }
    }
}
