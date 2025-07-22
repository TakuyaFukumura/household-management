package com.example.service;

import com.example.entity.IdealBudget;
import com.example.repository.IdealBudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 理想家計簿のサービスクラス。
 * データ取得・保存・削除などのビジネスロジックを提供します。
 */
@Service
public class IdealBudgetService {

    /**
     * 理想家計簿リポジトリ。
     */
    private final IdealBudgetRepository idealBudgetRepository;

    /**
     * コンストラクタ。
     * @param idealBudgetRepository 理想家計簿リポジトリ
     */
    public IdealBudgetService(IdealBudgetRepository idealBudgetRepository) {
        this.idealBudgetRepository = idealBudgetRepository;
    }

    /**
     * 全ての理想予算データをカテゴリ名でソートして取得します。
     * @return 理想家計簿リスト
     */
    public List<IdealBudget> getAllIdealBudgets() {
        return idealBudgetRepository.findAllByOrderByCategory();
    }

    /**
     * IDで理想予算データを取得します。
     * @param id 理想家計簿ID
     * @return 該当する理想家計簿（Optional）
     */
    public Optional<IdealBudget> getIdealBudgetById(Long id) {
        return idealBudgetRepository.findById(id);
    }

    /**
     * カテゴリ名で理想予算データを取得します。
     * @param category カテゴリ名
     * @return 該当する理想家計簿（Optional）
     */
    public Optional<IdealBudget> getIdealBudgetByCategory(String category) {
        return idealBudgetRepository.findByCategory(category);
    }

    /**
     * 理想予算データを保存または更新します。
     * @param idealBudget 保存・更新する理想家計簿
     */
    public void saveIdealBudget(IdealBudget idealBudget) {
        idealBudgetRepository.save(idealBudget);
    }

    /**
     * 理想予算データを削除します。
     * @param id 削除する理想家計簿ID
     */
    public void deleteIdealBudget(Long id) {
        idealBudgetRepository.deleteById(id);
    }
}
