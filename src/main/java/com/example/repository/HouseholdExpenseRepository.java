package com.example.repository;

import com.example.entity.HouseholdExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdExpenseRepository extends JpaRepository<HouseholdExpense, Long> {
    
    // 支出日の降順でソートして全て取得
    List<HouseholdExpense> findAllByOrderByExpenseDateDescIdDesc();
}
