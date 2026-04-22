package com.cpems.web.controller.newenergy;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.VirtualPowerPlant;
import com.ruoyi.system.service.IVirtualPowerPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 虚拟电厂Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/newenergy/virtualplant")
public class VirtualPowerPlantController extends BaseController {

    private final IVirtualPowerPlantService virtualPowerPlantService;

    /**
     * 查询虚拟电厂列表
     */
    @GetMapping("/list")
    public R<List<VirtualPowerPlant>> list(VirtualPowerPlant virtualPowerPlant) {
        List<VirtualPowerPlant> list = virtualPowerPlantService.list();
        return R.ok(list);
    }

    /**
     * 获取虚拟电厂详细信息
     */
    @GetMapping("/{plantId}")
    public R<VirtualPowerPlant> getInfo(@PathVariable Long plantId) {
        return R.ok(virtualPowerPlantService.getById(plantId));
    }

    /**
     * 新增虚拟电厂
     */
    @PostMapping
    public R<Void> add(@RequestBody VirtualPowerPlant virtualPowerPlant) {
        virtualPowerPlantService.save(virtualPowerPlant);
        return R.ok();
    }

    /**
     * 修改虚拟电厂
     */
    @PutMapping
    public R<Void> edit(@RequestBody VirtualPowerPlant virtualPowerPlant) {
        virtualPowerPlantService.updateById(virtualPowerPlant);
        return R.ok();
    }

    /**
     * 删除虚拟电厂
     */
    @DeleteMapping("/{plantIds}")
    public R<Void> remove(@PathVariable Long[] plantIds) {
        virtualPowerPlantService.removeByIds(Arrays.asList(plantIds));
        return R.ok();
    }

    /**
     * 导出虚拟电厂
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, VirtualPowerPlant virtualPowerPlant) {
        List<VirtualPowerPlant> list = virtualPowerPlantService.list();
        ExcelUtil<VirtualPowerPlant> util = new ExcelUtil<VirtualPowerPlant>(VirtualPowerPlant.class);
        util.exportExcel(response, list, "虚拟电厂数据");
    }
}
