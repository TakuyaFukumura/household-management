package com.example.service;

import com.example.entity.HouseholdExpense;
import com.example.repository.HouseholdExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class HouseholdExpenseService {

    private final HouseholdExpenseRepository householdExpenseRepository;

    public HouseholdExpenseService(HouseholdExpenseRepository householdExpenseRepository) {
        this.householdExpenseRepository = householdExpenseRepository;
    }

    /**
     * 指定した年月の家計簿データを取得します。
     *
     * @param targetYm 取得対象の年月（YearMonth形式）
     * @return 指定年月内の家計簿データのリスト（降順で並び替え済み）
     */
    public List<HouseholdExpense> getExpensesByYearAndMonth(YearMonth targetYm) {
        return householdExpenseRepository.findByExpenseDateBetweenOrderByExpenseDateDescIdDesc(
                targetYm.atDay(1), targetYm.atEndOfMonth()
        );
    }

    // IDで家計簿データを取得
    public Optional<HouseholdExpense> getExpenseById(Long id) {
        return householdExpenseRepository.findById(id);
    }

    // 家計簿データを保存・更新
    public void saveExpense(HouseholdExpense expense) {
        householdExpenseRepository.save(expense);
    }

    // 家計簿データを削除
    public void deleteExpense(Long id) {
        householdExpenseRepository.deleteById(id);
    }
}
