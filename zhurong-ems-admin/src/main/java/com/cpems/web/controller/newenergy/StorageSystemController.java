package com.cpems.web.controller.newenergy;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.StorageSystem;
import com.ruoyi.system.service.IStorageSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 储能系统Controller
 * 
 * @author cpems
 * @date 2026-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/newenergy/storage")
public class StorageSystemController extends BaseController {

    private final IStorageSystemService storageSystemService;

    /**
     * 查询储能系统列表
     */
    @GetMapping("/list")
    public R<List<StorageSystem>> list(StorageSystem storageSystem) {
        List<StorageSystem> list = storageSystemService.list();
        return R.ok(list);
    }

    /**
     * 获取储能系统详细信息
     */
    @GetMapping("/{systemId}")
    public R<StorageSystem> getInfo(@PathVariable Long systemId) {
        return R.ok(storageSystemService.getById(systemId));
    }

    /**
     * 新增储能系统
     */
    @PostMapping
    public R<Void> add(@RequestBody StorageSystem storageSystem) {
        storageSystemService.save(storageSystem);
        return R.ok();
    }

    /**
     * 修改储能系统
     */
    @PutMapping
    public R<Void> edit(@RequestBody StorageSystem storageSystem) {
        storageSystemService.updateById(storageSystem);
        return R.ok();
    }

    /**
     * 删除储能系统
     */
    @DeleteMapping("/{systemIds}")
    public R<Void> remove(@PathVariable Long[] systemIds) {
        storageSystemService.removeByIds(Arrays.asList(systemIds));
        return R.ok();
    }

    /**
     * 导出储能系统
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, StorageSystem storageSystem) {
        List<StorageSystem> list = storageSystemService.list();
        ExcelUtil<StorageSystem> util = new ExcelUtil<StorageSystem>(StorageSystem.class);
        util.exportExcel(response, list, "储能系统数据");
    }
}
