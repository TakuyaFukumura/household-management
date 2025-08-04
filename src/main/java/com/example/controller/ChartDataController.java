package com.example.controller;

import com.example.dto.ChartDataDto;
import com.example.service.HouseholdBudgetService;
import com.example.service.HouseholdExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

/**
 * チャート表示用データを提供するRESTコントローラー
 */
@RestController
@RequestMapping("/api/chart")
public class ChartDataController {

    private final HouseholdExpenseService householdExpenseService;
    private final HouseholdBudgetService householdBudgetService;

    public ChartDataController(HouseholdExpenseService householdExpenseService,
                               HouseholdBudgetService householdBudgetService) {
        this.householdExpenseService = householdExpenseService;
        this.householdBudgetService = householdBudgetService;
    }

    /**
     * 指定年月の支出データをカテゴリ別に集計してチャート用データとして返却します。
     *
     * @param year  年（任意、未指定時は現在年）
     * @param month 月（任意、未指定時は現在月）
     * @return カテゴリ別支出集計データ
     */
    @GetMapping("/expenses")
    public ResponseEntity<List<ChartDataDto>> getExpenseChartData(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month) {

        // 年月が指定されていない場合は現在月を使用
        YearMonth targetYm = YearMonth.now();
        if (year != null && month != null) {
            targetYm = YearMonth.of(year, month);
        }

        List<ChartDataDto> chartData = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm);
        return ResponseEntity.ok(chartData);
    }

    /**
     * 予算データをカテゴリ別にチャート用データとして返却します。
     *
     * @return カテゴリ別予算データ
     */
    @GetMapping("/budgets")
    public ResponseEntity<List<ChartDataDto>> getBudgetChartData() {
        List<ChartDataDto> chartData = householdBudgetService.getBudgetChartData();
        return ResponseEntity.ok(chartData);
    }
}
