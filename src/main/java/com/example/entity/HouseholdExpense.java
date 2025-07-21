package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "household_expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;
    
    @Column(nullable = false, length = 100)
    private String category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(length = 255)
    private String description;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // カスタムコンストラクタ（新規作成時用）
    public HouseholdExpense(LocalDate expenseDate, String category, BigDecimal amount, String description) {
        this.expenseDate = expenseDate;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
