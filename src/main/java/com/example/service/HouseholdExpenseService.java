package com.example.service;

import com.example.entity.HouseholdExpense;
import com.example.repository.HouseholdExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdExpenseService {

    private final HouseholdExpenseRepository householdExpenseRepository;

    public HouseholdExpenseService(HouseholdExpenseRepository householdExpenseRepository) {
        this.householdExpenseRepository = householdExpenseRepository;
    }

    // 全ての家計簿データを取得
    public List<HouseholdExpense> getAllExpenses() {
        return householdExpenseRepository.findAllByOrderByExpenseDateDescIdDesc();
    }

    // IDで家計簿データを取得
    public HouseholdExpense getExpenseById(Long id) {
        return householdExpenseRepository.findById(id).orElse(null);
    }

    // 家計簿データを保存
    public void saveExpense(HouseholdExpense expense) {
        householdExpenseRepository.save(expense);
    }

    // 家計簿データを更新
    public void updateExpense(HouseholdExpense expense) {
        householdExpenseRepository.save(expense);
    }

    // 家計簿データを削除
    public void deleteExpense(Long id) {
        householdExpenseRepository.deleteById(id);
    }
}
