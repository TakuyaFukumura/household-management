package com.example.controller;

import com.example.entity.HouseholdBudget;
import com.example.service.HouseholdBudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 理想家計簿のコントローラークラス。
 * 理想家計簿の一覧表示、新規追加、編集、削除の各機能を提供します。
 */
@Controller
@RequestMapping("/household-budget")
public class HouseholdBudgetController {

    /**
     * リダイレクト用パス定数。
     */
    private static final String REDIRECT_HOUSEHOLD_BUDGET = "redirect:/household-budget";

    /**
     * 理想家計簿サービス。
     */
    private final HouseholdBudgetService householdBudgetService;

    /**
     * コンストラクタ。
     * @param householdBudgetService 理想家計簿サービス
     */
    public HouseholdBudgetController(HouseholdBudgetService householdBudgetService) {
        this.householdBudgetService = householdBudgetService;
    }

    /**
     * 理想家計簿一覧表示。
     * @param model モデル
     * @return 一覧画面テンプレート名
     */
    @GetMapping
    public String listHouseholdBudgets(Model model) {
        List<HouseholdBudget> householdBudgets = householdBudgetService.getAllHouseholdBudgets();
        model.addAttribute("HouseholdBudgets", householdBudgets);
        return "household-budget/list";
    }

    /**
     * 新規追加フォーム表示。
     * @param model モデル
     * @return 新規追加画面テンプレート名
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("HouseholdBudget", new HouseholdBudget());
        return "household-budget/add";
    }

    /**
     * 新規追加処理。
     * @param householdBudget 追加する理想家計簿
     * @return リダイレクト先
     */
    @PostMapping("/add")
    public String addHouseholdBudget(@ModelAttribute HouseholdBudget householdBudget) {
        householdBudgetService.saveHouseholdBudget(householdBudget);
        return REDIRECT_HOUSEHOLD_BUDGET;
    }

    /**
     * 編集フォーム表示。
     * @param id 編集対象ID
     * @param model モデル
     * @return 編集画面テンプレート名またはリダイレクト
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        HouseholdBudget householdBudget = householdBudgetService.getHouseholdBudgetById(id).orElse(null);
        if (householdBudget == null) {
            return REDIRECT_HOUSEHOLD_BUDGET;
        }
        model.addAttribute("HouseholdBudget", householdBudget);
        return "household-budget/edit";
    }

    /**
     * 編集処理。
     * @param id 編集対象ID
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
     * 削除処理。
     * @param id 削除対象ID
     * @return リダイレクト先
     */
    @PostMapping("/delete/{id}")
    public String deleteHouseholdBudget(@PathVariable Long id) {
        householdBudgetService.deleteHouseholdBudget(id);
        return REDIRECT_HOUSEHOLD_BUDGET;
    }
}
