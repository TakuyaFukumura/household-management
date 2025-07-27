package com.example.service;

import com.example.dto.ChartDataDto;
import com.example.entity.HouseholdBudget;
import com.example.repository.HouseholdBudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 家計予算のデータ取得・保存・削除などのビジネスロジックを提供します。
 */
@Service
public class HouseholdBudgetService {

    /**
     * 家計予算リポジトリ
     */
    private final HouseholdBudgetRepository householdBudgetRepository;

    /**
     * コンストラクタ
     *
     * @param householdBudgetRepository 家計予算リポジトリ
     */
    public HouseholdBudgetService(HouseholdBudgetRepository householdBudgetRepository) {
        this.householdBudgetRepository = householdBudgetRepository;
    }

    /**
     * 全ての家計予算データをカテゴリ名でソートして取得します。
     *
     * @return 家計予算リスト
     */
    public List<HouseholdBudget> getAllHouseholdBudgets() {
        return householdBudgetRepository.findAllByOrderByCategory();
    }

    /**
     * 全ての家計予算データをチャート用データとして取得します。
     *
     * @return カテゴリ別予算データのリスト
     */
    public List<ChartDataDto> getBudgetChartData() {
        return getAllHouseholdBudgets().stream()
                .map(budget -> new ChartDataDto(budget.getCategory(), budget.getAmount()))
                .collect(Collectors.toList());
    }

    /**
     * IDで家計予算データを取得します。
     *
     * @param id 家計予算ID
     * @return 該当する家計予算（Optional）
     */
    public Optional<HouseholdBudget> getHouseholdBudgetById(Long id) {
        return householdBudgetRepository.findById(id);
    }

    /**
     * 家計予算データを保存または更新します。
     *
     * @param householdBudget 保存・更新する家計予算
     */
    public void saveHouseholdBudget(HouseholdBudget householdBudget) {
        householdBudgetRepository.save(householdBudget);
    }

    /**
     * 家計予算データを削除します。
     *
     * @param id 削除対象の家計予算ID
     */
    public void deleteHouseholdBudget(Long id) {
        householdBudgetRepository.deleteById(id);
    }
}
