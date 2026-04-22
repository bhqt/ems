package com.cpems.web.controller.dispatch;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.NetworkNode;
import com.ruoyi.system.service.INetworkNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dispatch/node")
public class NetworkNodeController extends BaseController {

    private final INetworkNodeService nodeService;

    @GetMapping("/list")
    public R<List<NetworkNode>> list(NetworkNode node) {
        List<NetworkNode> list = nodeService.list();
        return R.ok(list);
    }

    @GetMapping("/listByNetwork/{networkId}")
    public R<List<NetworkNode>> listByNetworkId(@PathVariable Long networkId) {
        List<NetworkNode> list = nodeService.listByNetworkId(networkId);
        return R.ok(list);
    }

    @GetMapping("/{nodeId}")
    public R<NetworkNode> getInfo(@PathVariable Long nodeId) {
        return R.ok(nodeService.getById(nodeId));
    }

    @PostMapping
    public R<Void> add(@RequestBody NetworkNode node) {
        nodeService.save(node);
        return R.ok();
    }

    @PutMapping
    public R<Void> edit(@RequestBody NetworkNode node) {
        nodeService.updateById(node);
        return R.ok();
    }

    @DeleteMapping("/{nodeIds}")
    public R<Void> remove(@PathVariable Long[] nodeIds) {
        nodeService.removeByIds(Arrays.asList(nodeIds));
        return R.ok();
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, NetworkNode node) {
        List<NetworkNode> list = nodeService.list();
        ExcelUtil<NetworkNode> util = new ExcelUtil<>(NetworkNode.class);
        util.exportExcel(response, list, "网络节点数据");
    }
}
