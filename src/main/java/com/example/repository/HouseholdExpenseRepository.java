package com.example.repository;

import com.example.entity.HouseholdExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdExpenseRepository extends JpaRepository<HouseholdExpense, Long> {
    
    // 支出日の降順でソートして全て取得
    List<HouseholdExpense> findAllByOrderByExpenseDateDescIdDesc();
    
    // 指定年月の支出データを取得（支出日の降順でソート）
    @Query("SELECT h FROM HouseholdExpense h WHERE YEAR(h.expenseDate) = :year AND MONTH(h.expenseDate) = :month ORDER BY h.expenseDate DESC, h.id DESC")
    List<HouseholdExpense> findByYearAndMonthOrderByExpenseDateDescIdDesc(@Param("year") int year, @Param("month") int month);
}
