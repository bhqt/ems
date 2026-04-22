package com.cpems.web.controller.digitaltwin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.ThreeDModel;
import com.ruoyi.system.service.IThreeDModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/digitaltwin/model")
public class ThreeDModelController extends BaseController {

    private final IThreeDModelService modelService;

    @GetMapping("/list")
    public R<List<ThreeDModel>> list() {
        return R.ok(modelService.list());
    }

    @GetMapping("/{modelId}")
    public R<ThreeDModel> getInfo(@PathVariable Long modelId) {
        return R.ok(modelService.getById(modelId));
    }

    @PostMapping
    public R<Void> add(@RequestBody ThreeDModel model) {
        modelService.save(model);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody ThreeDModel model) {
        modelService.updateById(model);
        return R.ok();
    }

    @DeleteMapping("/{modelIds}")
    public R<Void> remove(@PathVariable Long[] modelIds) {
        modelService.removeByIds(Arrays.asList(modelIds));
        return R.ok();
    }

    @GetMapping("/tree")
    public R<Map<String, Object>> getTree() {
        return R.ok(modelService.getModelTree());
    }
}
