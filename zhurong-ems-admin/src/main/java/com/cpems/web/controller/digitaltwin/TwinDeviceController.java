package com.cpems.web.controller.digitaltwin;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.TwinDevice;
import com.ruoyi.system.service.ITwinDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/system/digitaltwin/device")
public class TwinDeviceController extends BaseController {

    private final ITwinDeviceService deviceService;

    @GetMapping("/list")
    public R<List<TwinDevice>> list() {
        return R.ok(deviceService.list());
    }

    @GetMapping("/{deviceId}")
    public R<TwinDevice> getInfo(@PathVariable Long deviceId) {
        return R.ok(deviceService.getById(deviceId));
    }

    @PostMapping
    public R<Void> add(@RequestBody TwinDevice device) {
        deviceService.save(device);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody TwinDevice device) {
        deviceService.updateById(device);
        return R.ok();
    }

    @DeleteMapping("/{deviceIds}")
    public R<Void> remove(@PathVariable Long[] deviceIds) {
        deviceService.removeByIds(Arrays.asList(deviceIds));
        return R.ok();
    }
}
