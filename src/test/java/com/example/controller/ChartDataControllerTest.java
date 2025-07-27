package com.example.controller;

import com.example.dto.ChartDataDto;
import com.example.service.HouseholdBudgetService;
import com.example.service.HouseholdExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ChartDataControllerのテストクラス
 */
@WebMvcTest(ChartDataController.class)
class ChartDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseholdExpenseService householdExpenseService;

    @MockBean
    private HouseholdBudgetService householdBudgetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 支出チャートデータ取得_年月指定あり() throws Exception {
        // Given
        List<ChartDataDto> expectedData = Arrays.asList(
                new ChartDataDto("食費", new BigDecimal("30000")),
                new ChartDataDto("光熱費", new BigDecimal("15000"))
        );
        when(householdExpenseService.getExpenseChartDataByYearAndMonth(YearMonth.of(2025, 7)))
                .thenReturn(expectedData);

        // When & Then
        mockMvc.perform(get("/api/chart/expenses")
                        .param("year", "2025")
                        .param("month", "7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("食費"))
                .andExpect(jsonPath("$[0].amount").value(30000))
                .andExpect(jsonPath("$[1].category").value("光熱費"))
                .andExpect(jsonPath("$[1].amount").value(15000));
    }

    @Test
    void 支出チャートデータ取得_年月指定なし() throws Exception {
        // Given
        YearMonth currentYearMonth = YearMonth.now();
        List<ChartDataDto> expectedData = Arrays.asList(
                new ChartDataDto("食費", new BigDecimal("25000"))
        );
        when(householdExpenseService.getExpenseChartDataByYearAndMonth(currentYearMonth))
                .thenReturn(expectedData);

        // When & Then
        mockMvc.perform(get("/api/chart/expenses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("食費"))
                .andExpect(jsonPath("$[0].amount").value(25000));
    }

    @Test
    void 予算チャートデータ取得() throws Exception {
        // Given
        List<ChartDataDto> expectedData = Arrays.asList(
                new ChartDataDto("住居費", new BigDecimal("42000")),
                new ChartDataDto("食費", new BigDecimal("30000")),
                new ChartDataDto("娯楽費", new BigDecimal("20000"))
        );
        when(householdBudgetService.getBudgetChartData()).thenReturn(expectedData);

        // When & Then
        mockMvc.perform(get("/api/chart/budgets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].category").value("住居費"))
                .andExpect(jsonPath("$[0].amount").value(42000))
                .andExpect(jsonPath("$[1].category").value("食費"))
                .andExpect(jsonPath("$[1].amount").value(30000))
                .andExpect(jsonPath("$[2].category").value("娯楽費"))
                .andExpect(jsonPath("$[2].amount").value(20000));
    }

    @Test
    void 支出チャートデータ取得_データが空の場合() throws Exception {
        // Given
        when(householdExpenseService.getExpenseChartDataByYearAndMonth(YearMonth.of(2025, 1)))
                .thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/chart/expenses")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
