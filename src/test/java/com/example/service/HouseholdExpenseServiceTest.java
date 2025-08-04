package com.example.service;

import com.example.dto.ChartDataDto;
import com.example.entity.HouseholdExpense;
import com.example.repository.HouseholdExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * HouseholdExpenseServiceのテストクラス
 */
@ExtendWith(MockitoExtension.class)
class HouseholdExpenseServiceTest {

    @Mock
    private HouseholdExpenseRepository householdExpenseRepository;

    @InjectMocks
    private HouseholdExpenseService householdExpenseService;

    @Test
    void 支出チャートデータ取得_正常系() {
        // Given
        YearMonth targetYm = YearMonth.of(2025, 7);
        LocalDate startDate = targetYm.atDay(1);
        LocalDate endDate = targetYm.atEndOfMonth();

        List<HouseholdExpense> expenses = Arrays.asList(
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1000"), "朝食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 2), "食費", new BigDecimal("1500"), "昼食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 3), "交通費", new BigDecimal("500"), "電車代"),
                new HouseholdExpense(LocalDate.of(2025, 7, 4), "光熱費", new BigDecimal("8000"), "電気代")
        );

        when(householdExpenseRepository.findByExpenseDateBetweenOrderByAmountDesc(startDate, endDate))
                .thenReturn(expenses);

        // When
        List<ChartDataDto> result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm);

        // Then
        assertThat(result).hasSize(3);

        // カテゴリ別の集計結果を確認
        ChartDataDto foodData = result.stream()
                .filter(data -> "食費".equals(data.getCategory()))
                .findFirst()
                .orElse(null);
        assertThat(foodData).isNotNull();
        assertThat(foodData.getAmount()).isEqualByComparingTo(new BigDecimal("2500")); // 1000 + 1500

        ChartDataDto transportData = result.stream()
                .filter(data -> "交通費".equals(data.getCategory()))
                .findFirst()
                .orElse(null);
        assertThat(transportData).isNotNull();
        assertThat(transportData.getAmount()).isEqualByComparingTo(new BigDecimal("500"));

        ChartDataDto utilityData = result.stream()
                .filter(data -> "光熱費".equals(data.getCategory()))
                .findFirst()
                .orElse(null);
        assertThat(utilityData).isNotNull();
        assertThat(utilityData.getAmount()).isEqualByComparingTo(new BigDecimal("8000"));
    }

    @Test
    void 支出チャートデータ取得_データが空の場合() {
        // Given
        YearMonth targetYm = YearMonth.of(2025, 1);
        LocalDate startDate = targetYm.atDay(1);
        LocalDate endDate = targetYm.atEndOfMonth();

        when(householdExpenseRepository.findByExpenseDateBetweenOrderByAmountDesc(startDate, endDate))
                .thenReturn(Arrays.asList());

        // When
        List<ChartDataDto> result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void 支出チャートデータ取得_同一カテゴリ複数データ() {
        // Given
        YearMonth targetYm = YearMonth.of(2025, 7);
        LocalDate startDate = targetYm.atDay(1);
        LocalDate endDate = targetYm.atEndOfMonth();

        List<HouseholdExpense> expenses = Arrays.asList(
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1000"), "朝食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("1200"), "昼食"),
                new HouseholdExpense(LocalDate.of(2025, 7, 1), "食費", new BigDecimal("800"), "夕食")
        );

        when(householdExpenseRepository.findByExpenseDateBetweenOrderByAmountDesc(startDate, endDate))
                .thenReturn(expenses);

        // When
        List<ChartDataDto> result = householdExpenseService.getExpenseChartDataByYearAndMonth(targetYm);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("食費");
        assertThat(result.get(0).getAmount()).isEqualByComparingTo(new BigDecimal("3000")); // 1000 + 1200 + 800
    }

    @Test
    void 指定年月の支出データ取得_金額降順ソート() {
        // Given
        YearMonth targetYm = YearMonth.of(2025, 8);
        LocalDate startDate = targetYm.atDay(1);
        LocalDate endDate = targetYm.atEndOfMonth();

        List<HouseholdExpense> expenses = Arrays.asList(
                new HouseholdExpense(LocalDate.of(2025, 8, 1), "光熱費", new BigDecimal("15000"), "電気・ガス代"),
                new HouseholdExpense(LocalDate.of(2025, 8, 2), "食費", new BigDecimal("8000"), "スーパーでの買い物"),
                new HouseholdExpense(LocalDate.of(2025, 8, 3), "娯楽費", new BigDecimal("2500"), "映画鑑賞")
        );

        when(householdExpenseRepository.findByExpenseDateBetweenOrderByAmountDesc(startDate, endDate))
                .thenReturn(expenses);

        // When
        List<HouseholdExpense> result = householdExpenseService.getExpensesByYearAndMonth(targetYm);

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getAmount()).isEqualByComparingTo(new BigDecimal("15000")); // 最高額が最初
        assertThat(result.get(1).getAmount()).isEqualByComparingTo(new BigDecimal("8000"));  // 中間額が2番目
        assertThat(result.get(2).getAmount()).isEqualByComparingTo(new BigDecimal("2500"));  // 最低額が最後
    }
}
