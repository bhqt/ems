package com.cpems.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysApiInterface;
import com.ruoyi.system.service.ISysApiInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/integration/api")
public class SysApiInterfaceController extends BaseController {

    private final ISysApiInterfaceService apiInterfaceService;

    @GetMapping("/list")
    public R<List<SysApiInterface>> list(SysApiInterface api) {
        List<SysApiInterface> list = apiInterfaceService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R<SysApiInterface> getInfo(@PathVariable Long id) {
        return R.ok(apiInterfaceService.getById(id));
    }

    @PostMapping
    public R<Void> add(@RequestBody SysApiInterface api) {
        apiInterfaceService.save(api);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody SysApiInterface api) {
        apiInterfaceService.updateById(api);
        return R.ok();
    }

    @DeleteMapping("/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        apiInterfaceService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @GetMapping("/byCategory/{category}")
    public R<List<SysApiInterface>> getByCategory(@PathVariable String category) {
        return R.ok(apiInterfaceService.getByCategory(category));
    }
}
