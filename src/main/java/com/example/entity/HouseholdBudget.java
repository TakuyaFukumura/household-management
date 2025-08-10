package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 家計予算
 */
@Entity
@Table(name = "household_budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseholdBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * カテゴリ名
     */
    @Column(nullable = false, length = 100, unique = true)
    private String category;

    /**
     * カテゴリーマスター参照（今後はこちらを使用予定）
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryEntity;

    /**
     * 予算金額
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * 作成日時
     */
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * カスタムコンストラクタ（カテゴリエンティティ使用）
     */
    public HouseholdBudget(Category categoryEntity, BigDecimal amount) {
        this.categoryEntity = categoryEntity;
        this.category = categoryEntity.getName(); // 後方互換性のため
        this.amount = amount;
    }

    /**
     * カスタムコンストラクタ（文字列カテゴリ使用）
     */
    public HouseholdBudget(String category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
    }
}
