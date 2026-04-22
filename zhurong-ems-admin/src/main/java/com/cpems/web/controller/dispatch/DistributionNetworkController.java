package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.DistributionNetwork;
import com.ruoyi.system.service.IDistributionNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/network")
public class DistributionNetworkController extends BaseController {

    private final IDistributionNetworkService networkService;

    @GetMapping("/list")
    public R<List<DistributionNetwork>> list(DistributionNetwork network) {
        List<DistributionNetwork> list = networkService.list();
        return R.ok(list);
    }

    @GetMapping("/{networkId}")
    public R<DistributionNetwork> getInfo(@PathVariable Long networkId) {
        return R.ok(networkService.getById(networkId));
    }

    @PostMapping
    public R<Void> add(@RequestBody DistributionNetwork network) {
        networkService.save(network);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody DistributionNetwork network) {
        networkService.updateById(network);
        return R.ok();
    }

    @DeleteMapping("/{networkIds}")
    public R<Void> remove(@PathVariable Long[] networkIds) {
        networkService.removeByIds(Arrays.asList(networkIds));
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, DistributionNetwork network) {
        List<DistributionNetwork> list = networkService.list();
        ExcelUtil<DistributionNetwork> util = new ExcelUtil<>(DistributionNetwork.class);
        util.exportExcel(response, list, "输配网络数据");
    }
}
