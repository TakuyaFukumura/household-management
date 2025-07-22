package com.example.controller;

import com.example.entity.HouseholdBudget;
import com.example.service.HouseholdBudgetService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 家計予算の一覧表示、新規追加、編集、削除の各機能を提供します。
 */
@Controller
@RequestMapping("/budget")
public class HouseholdBudgetController {

    private static final Logger logger = LoggerFactory.getLogger(HouseholdBudgetController.class);

    /**
     * リダイレクト用パス定数
     */
    private static final String REDIRECT_HOUSEHOLD_BUDGET = "redirect:/budget";

    /**
     * 家計予算サービス
     */
    private final HouseholdBudgetService householdBudgetService;

    /**
     * コンストラクタ
     *
     * @param householdBudgetService 家計予算サービス
     */
    public HouseholdBudgetController(HouseholdBudgetService householdBudgetService) {
        this.householdBudgetService = householdBudgetService;
    }

    /**
     * 家計予算一覧表示
     *
     * @param model モデル
     * @return 一覧画面テンプレート名
     */
    @GetMapping
    public String listHouseholdBudgets(Model model) {
        List<HouseholdBudget> householdBudgets = householdBudgetService.getAllHouseholdBudgets();
        model.addAttribute("householdBudgets", householdBudgets);
        return "budget/list";
    }

    /**
     * 新規追加フォーム表示
     *
     * @param model モデル
     * @return 新規追加画面テンプレート名
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("householdBudget", new HouseholdBudget());
        return "budget/add";
    }

    /**
     * 新規追加処理
     *
     * @param householdBudget 追加する家計予算
     * @return リダイレクト先
     */
    @PostMapping("/add")
    public String addHouseholdBudget(@ModelAttribute HouseholdBudget householdBudget) {
        householdBudgetService.saveHouseholdBudget(householdBudget);
        return REDIRECT_HOUSEHOLD_BUDGET;
    }

    /**
     * 編集フォーム表示
     *
     * @param id    編集対象の家計予算ID
     * @param model モデル
     * @return 編集画面テンプレート名またはリダイレクト
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            HouseholdBudget householdBudget = householdBudgetService.getHouseholdBudgetById(id)
                    .orElseThrow(() -> new EntityNotFoundException("家計予算が見つかりません。id: " + id));
            model.addAttribute("householdBudget", householdBudget);
        } catch (Exception e) {
            logger.error("家計予算の取得に失敗しました。id: {}", id, e);
            return REDIRECT_HOUSEHOLD_BUDGET;
        }
        return "budget/edit";
    }

    /**
     * 編集処理
     *
     * @param id              編集対象の家計予算ID
     * @param householdBudget 編集内容
     * @return リダイレクト先
     */
    @PostMapping("/edit/{id}")
    public String updateHouseholdBudget(@PathVariable Long id, @ModelAttribute HouseholdBudget householdBudget) {
        householdBudget.setId(id);
        householdBudgetService.saveHouseholdBudget(householdBudget);
        return REDIRECT_HOUSEHOLD_BUDGET;
    }

    /**
     * 削除処理
     *
     * @param id 削除対象の家計予算ID
     * @return リダイレクト先
     */
    @PostMapping("/delete/{id}")
    public String deleteHouseholdBudget(@PathVariable Long id) {
        householdBudgetService.deleteHouseholdBudget(id);
        return REDIRECT_HOUSEHOLD_BUDGET;
    }
}
