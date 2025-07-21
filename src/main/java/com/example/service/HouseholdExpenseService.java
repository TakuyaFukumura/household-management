package com.example.service;

import com.example.entity.HouseholdExpense;
import com.example.repository.HouseholdExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    // 指定年月の家計簿データを取得
    public List<HouseholdExpense> getExpensesByYearAndMonth(int year, int month) {
        return householdExpenseRepository.findByYearAndMonthOrderByExpenseDateDescIdDesc(year, month);
    }

    // 年月が指定されていない場合は現在月、指定されている場合はその月のデータを取得
    public List<HouseholdExpense> getExpensesByYearAndMonth(Integer year, Integer month) {
        if (year == null || month == null) {
            LocalDate now = LocalDate.now();
            return getExpensesByYearAndMonth(now.getYear(), now.getMonthValue());
        }
        return getExpensesByYearAndMonth(year.intValue(), month.intValue());
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
