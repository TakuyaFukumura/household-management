package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ideal_budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdealBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100, unique = true)
    private String category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetAmount;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // カスタムコンストラクタ（新規作成時用）
    public IdealBudget(String category, BigDecimal budgetAmount) {
        this.category = category;
        this.budgetAmount = budgetAmount;
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