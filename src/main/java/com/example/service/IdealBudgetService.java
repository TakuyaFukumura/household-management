package com.example.service;

import com.example.entity.IdealBudget;
import com.example.repository.IdealBudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdealBudgetService {

    private final IdealBudgetRepository idealBudgetRepository;

    public IdealBudgetService(IdealBudgetRepository idealBudgetRepository) {
        this.idealBudgetRepository = idealBudgetRepository;
    }

    // 全ての理想予算データを取得
    public List<IdealBudget> getAllIdealBudgets() {
        return idealBudgetRepository.findAllByOrderByCategory();
    }

    // IDで理想予算データを取得
    public Optional<IdealBudget> getIdealBudgetById(Long id) {
        return idealBudgetRepository.findById(id);
    }

    // カテゴリ名で理想予算データを取得
    public Optional<IdealBudget> getIdealBudgetByCategory(String category) {
        return idealBudgetRepository.findByCategory(category);
    }

    // 理想予算データを保存・更新
    public void saveIdealBudget(IdealBudget idealBudget) {
        idealBudgetRepository.save(idealBudget);
    }

    // 理想予算データを削除
    public void deleteIdealBudget(Long id) {
        idealBudgetRepository.deleteById(id);
    }
}
