package com.cpems.web.controller.energy;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.bo.EnergyQualityBo;
import com.ruoyi.system.domain.vo.EnergyQualityVo;
import com.ruoyi.system.service.IEnergyQualityService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 能源质量管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/energy/quality")
@RequiredArgsConstructor
public class EnergyQualityController {

    private final IEnergyQualityService energyQualityService;

    /**
     * 查询能源质量列表
     */
    @GetMapping("/list")
    public R<TableDataInfo<EnergyQualityVo>> list(EnergyQualityBo bo, PageQuery pageQuery) {
        return R.ok(energyQualityService.queryPageList(bo, pageQuery.getPageNum(), pageQuery.getPageSize()));
    }

    /**
     * 导出能源质量列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, EnergyQualityBo bo) {
        List<EnergyQualityVo> list = energyQualityService.queryList(bo);
        ExcelUtil.exportExcel(list, "能源质量", EnergyQualityVo.class, response);
    }

    /**
     * 获取能源质量详细信息
     */
    @GetMapping("/getInfo/{qualityId}")
    public R<EnergyQualityVo> getInfo(@PathVariable Long qualityId) {
        return R.ok(energyQualityService.queryById(qualityId));
    }

    /**
     * 新增能源质量
     */
    @PostMapping("/add")
    public R<Void> add(@Validated @RequestBody EnergyQualityBo bo) {
        return energyQualityService.insertByBo(bo) ? R.ok() : R.fail();
    }

    /**
     * 修改能源质量
     */
    @PutMapping("/edit")
    public R<Void> edit(@Validated @RequestBody EnergyQualityBo bo) {
        return energyQualityService.updateByBo(bo) ? R.ok() : R.fail();
    }

    /**
     * 删除能源质量
     */
    @DeleteMapping("/remove/{qualityIds}")
    public R<Void> remove(@PathVariable Long[] qualityIds) {
        return energyQualityService.deleteWithValidByIds(Arrays.asList(qualityIds), true) ? R.ok() : R.fail();
    }
}
