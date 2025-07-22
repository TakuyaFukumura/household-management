package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 予算
 */
@Entity
@Table(name = "ideal_budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdealBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 予算カテゴリ
     */
    @Column(nullable = false, length = 100, unique = true)
    private String category;

    /**
     * 予算金額
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal budgetAmount;//amountだけでよさそう

    /**
     * 作成日時
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
