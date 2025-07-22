package com.example.service;

import com.example.entity.HouseholdBudget;
import com.example.repository.HouseholdBudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 家計予算のサービスクラス。
 * データ取得・保存・削除などのビジネスロジックを提供します。
 */
@Service
public class HouseholdBudgetService {

    /**
     * 家計予算リポジトリ。
     */
    private final HouseholdBudgetRepository householdBudgetRepository;

    /**
     * コンストラクタ。
     * @param householdBudgetRepository 家計予算リポジトリ
     */
    public HouseholdBudgetService(HouseholdBudgetRepository householdBudgetRepository) {
        this.householdBudgetRepository = householdBudgetRepository;
    }

    /**
     * 全ての理想予算データをカテゴリ名でソートして取得します。
     * @return 家計予算リスト
     */
    public List<HouseholdBudget> getAllHouseholdBudgets() {
        return householdBudgetRepository.findAllByOrderByCategory();
    }

    /**
     * IDで理想予算データを取得します。
     * @param id 家計予算ID
     * @return 該当する家計予算（Optional）
     */
    public Optional<HouseholdBudget> getHouseholdBudgetById(Long id) {
        return householdBudgetRepository.findById(id);
    }

    /**
     * カテゴリ名で理想予算データを取得します。
     * @param category カテゴリ名
     * @return 該当する家計予算（Optional）
     */
    public Optional<HouseholdBudget> getHouseholdBudgetByCategory(String category) {
        return householdBudgetRepository.findByCategory(category);
    }

    /**
     * 理想予算データを保存または更新します。
     * @param householdBudget 保存・更新する家計予算
     */
    public void saveHouseholdBudget(HouseholdBudget householdBudget) {
        householdBudgetRepository.save(householdBudget);
    }

    /**
     * 理想予算データを削除します。
     * @param id 削除する家計予算ID
     */
    public void deleteHouseholdBudget(Long id) {
        householdBudgetRepository.deleteById(id);
    }
}
