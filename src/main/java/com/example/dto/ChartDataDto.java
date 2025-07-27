package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * チャート表示用のデータ転送オブジェクト
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartDataDto {
    
    /**
     * カテゴリ名
     */
    private String category;
    
    /**
     * 金額
     */
    private BigDecimal amount;
}
