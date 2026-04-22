package com.cpems.web.controller.itemizeanalysis;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.ItemizeVo;
import com.ruoyi.system.service.IItemizeAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.core.controller.BaseController;
import java.util.List;



/**
 * 分项分析
 *
 * @author cpems
 * @date 2023-04-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/data/itemizeAnalysis")
public class ItemizeAnalysisController extends BaseController {

    private final IItemizeAnalysisService iItemizeAnalysisService;

    /**
     * 分项概况
     */
    @GetMapping("/getOverview")
    @SaIgnore
    public R<List<ItemizeVo>> getOverview(String itemId, String dateType, String date) {
        return R.ok(iItemizeAnalysisService.getOverview(itemId,dateType,date));
    }

}
