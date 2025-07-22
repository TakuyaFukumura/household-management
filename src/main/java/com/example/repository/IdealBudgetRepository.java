package com.example.repository;

import com.example.entity.IdealBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdealBudgetRepository extends JpaRepository<IdealBudget, Long> {

    // カテゴリ名でソートして全て取得
    List<IdealBudget> findAllByOrderByCategory();

    // カテゴリ名で検索
    Optional<IdealBudget> findByCategory(String category);
}
