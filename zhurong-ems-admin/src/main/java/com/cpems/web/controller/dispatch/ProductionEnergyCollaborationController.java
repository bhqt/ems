package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.ProductionEnergyCollaboration;
import com.ruoyi.system.service.IProductionEnergyCollaborationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/collaboration")
public class ProductionEnergyCollaborationController extends BaseController {

    private final IProductionEnergyCollaborationService collaborationService;

    @GetMapping("/list")
    public R<List<ProductionEnergyCollaboration>> list(ProductionEnergyCollaboration collaboration) {
        List<ProductionEnergyCollaboration> list = collaborationService.list();
        return R.ok(list);
    }

    @GetMapping("/{collabId}")
    public R<ProductionEnergyCollaboration> getInfo(@PathVariable Long collabId) {
        return R.ok(collaborationService.getById(collabId));
    }

    @PostMapping
    public R<Void> add(@RequestBody ProductionEnergyCollaboration collaboration) {
        collaborationService.save(collaboration);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody ProductionEnergyCollaboration collaboration) {
        collaborationService.updateById(collaboration);
        return R.ok();
    }

    @DeleteMapping("/{collabIds}")
    public R<Void> remove(@PathVariable Long[] collabIds) {
        collaborationService.removeByIds(Arrays.asList(collabIds));
        return R.ok();
    }

    @PutMapping("/execute/{collabId}")
    public R<Void> execute(@PathVariable Long collabId) {
        collaborationService.executeCollaboration(collabId);
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, ProductionEnergyCollaboration collaboration) {
        List<ProductionEnergyCollaboration> list = collaborationService.list();
        ExcelUtil<ProductionEnergyCollaboration> util = new ExcelUtil<>(ProductionEnergyCollaboration.class);
        util.exportExcel(response, list, "生产-能源协同数据");
    }
}
