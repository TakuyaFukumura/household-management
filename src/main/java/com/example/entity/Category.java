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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * カテゴリーマスター
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * カテゴリ名
     */
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    /**
     * カテゴリ説明
     */
    @Column(length = 255)
    private String description;

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
     * カテゴリ名のみでのコンストラクタ
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * カテゴリ名と説明でのコンストラクタ
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
