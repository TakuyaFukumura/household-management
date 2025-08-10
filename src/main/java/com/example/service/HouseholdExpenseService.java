package com.example.service;

import com.example.dto.ChartDataDto;
import com.example.entity.HouseholdExpense;
import com.example.repository.HouseholdExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseholdExpenseService {

    private final HouseholdExpenseRepository householdExpenseRepository;

    public HouseholdExpenseService(HouseholdExpenseRepository householdExpenseRepository) {
        this.householdExpenseRepository = householdExpenseRepository;
    }

    /**
     * 指定した年月の家計簿データを取得します（テーブル表示用・日付降順）。
     *
     * @param targetYm 取得対象の年月（YearMonth形式）
     * @return 指定年月内の家計簿データのリスト（日付降順で並び替え済み）
     */
    public List<HouseholdExpense> getExpensesByYearAndMonth(YearMonth targetYm) {
        return householdExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(
                targetYm.atDay(1), targetYm.atEndOfMonth()
        );
    }

    /**
     * 指定した年月の支出をカテゴリ別に集計してチャート用データを取得します。
     * チャート用のため、金額の大きい順で集計します。
     *
     * @param targetYm 取得対象の年月（YearMonth形式）
     * @return カテゴリ別集計データのリスト（金額降順）
     */
    public List<ChartDataDto> getExpenseChartDataByYearAndMonth(YearMonth targetYm) {
        // チャート用は金額降順でデータを取得
        List<HouseholdExpense> expenses = householdExpenseRepository.findByExpenseDateBetweenOrderByAmountDesc(
                targetYm.atDay(1), targetYm.atEndOfMonth()
        );
        
        Map<String, BigDecimal> categorySum = expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getCategoryEntity().getName(),
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                HouseholdExpense::getAmount,
                                BigDecimal::add
                        )
                ));
        
        // カテゴリ別集計結果も金額の大きい順でソート
        return categorySum.entrySet().stream()
                .map(entry -> new ChartDataDto(entry.getKey(), entry.getValue()))
                .sorted((a, b) -> b.getAmount().compareTo(a.getAmount()))
                .collect(Collectors.toList());
    }

    // IDで家計簿データを取得
    public Optional<HouseholdExpense> getExpenseById(Long id) {
        return householdExpenseRepository.findById(id);
    }

    // 家計簿データを保存・更新
    public void saveExpense(HouseholdExpense expense) {
        householdExpenseRepository.save(expense);
    }

    // 家計簿データを削除
    public void deleteExpense(Long id) {
        householdExpenseRepository.deleteById(id);
    }
}
