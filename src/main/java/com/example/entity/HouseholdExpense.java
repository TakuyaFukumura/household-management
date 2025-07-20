package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdExpense {
    private Long id;
    private LocalDate expenseDate;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // カスタムコンストラクタ（新規作成時用）
    public HouseholdExpense(LocalDate expenseDate, String category, BigDecimal amount, String description) {
        this.expenseDate = expenseDate;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
}
