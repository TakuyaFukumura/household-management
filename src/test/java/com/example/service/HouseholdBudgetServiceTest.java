package com.example.service;

import com.example.dto.ChartDataDto;
import com.example.entity.HouseholdBudget;
import com.example.repository.HouseholdBudgetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * HouseholdBudgetServiceのテストクラス
 */
@ExtendWith(MockitoExtension.class)
class HouseholdBudgetServiceTest {

    @Mock
    private HouseholdBudgetRepository householdBudgetRepository;

    @InjectMocks
    private HouseholdBudgetService householdBudgetService;

    @Test
    void 予算チャートデータ取得_正常系() {
        // Given
        List<HouseholdBudget> budgets = Arrays.asList(
                new HouseholdBudget(1L, "住居費", new BigDecimal("42000"), null, null),
                new HouseholdBudget(2L, "食費", new BigDecimal("30000"), null, null),
                new HouseholdBudget(3L, "娯楽費", new BigDecimal("20000"), null, null),
                new HouseholdBudget(4L, "光熱費", new BigDecimal("15000"), null, null)
        );

        when(householdBudgetRepository.findAllByOrderByCategory()).thenReturn(budgets);

        // When
        List<ChartDataDto> result = householdBudgetService.getBudgetChartData();

        // Then
        assertThat(result).hasSize(4);

        assertThat(result.get(0).getCategory()).isEqualTo("住居費");
        assertThat(result.get(0).getAmount()).isEqualByComparingTo(new BigDecimal("42000"));

        assertThat(result.get(1).getCategory()).isEqualTo("食費");
        assertThat(result.get(1).getAmount()).isEqualByComparingTo(new BigDecimal("30000"));

        assertThat(result.get(2).getCategory()).isEqualTo("娯楽費");
        assertThat(result.get(2).getAmount()).isEqualByComparingTo(new BigDecimal("20000"));

        assertThat(result.get(3).getCategory()).isEqualTo("光熱費");
        assertThat(result.get(3).getAmount()).isEqualByComparingTo(new BigDecimal("15000"));
    }

    @Test
    void 予算チャートデータ取得_データが空の場合() {
        // Given
        when(householdBudgetRepository.findAllByOrderByCategory()).thenReturn(Arrays.asList());

        // When
        List<ChartDataDto> result = householdBudgetService.getBudgetChartData();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void 予算チャートデータ取得_単一データ() {
        // Given
        List<HouseholdBudget> budgets = Arrays.asList(
                new HouseholdBudget(1L, "食費", new BigDecimal("30000"), null, null)
        );

        when(householdBudgetRepository.findAllByOrderByCategory()).thenReturn(budgets);

        // When
        List<ChartDataDto> result = householdBudgetService.getBudgetChartData();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("食費");
        assertThat(result.get(0).getAmount()).isEqualByComparingTo(new BigDecimal("30000"));
    }
}
