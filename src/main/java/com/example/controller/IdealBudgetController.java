package com.example.controller;

import com.example.entity.IdealBudget;
import com.example.service.IdealBudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ideal-budget")
public class IdealBudgetController {

    private static final String REDIRECT_IDEAL_BUDGET = "redirect:/ideal-budget";

    private final IdealBudgetService idealBudgetService;

    public IdealBudgetController(IdealBudgetService idealBudgetService) {
        this.idealBudgetService = idealBudgetService;
    }

    // 理想家計簿一覧表示
    @GetMapping
    public String listIdealBudgets(Model model) {
        List<IdealBudget> idealBudgets = idealBudgetService.getAllIdealBudgets();
        model.addAttribute("idealBudgets", idealBudgets);
        return "ideal-budget/list";
    }

    // 新規追加フォーム表示
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("idealBudget", new IdealBudget());
        return "ideal-budget/add";
    }

    // 新規追加処理
    @PostMapping("/add")
    public String addIdealBudget(@ModelAttribute IdealBudget idealBudget) {
        idealBudgetService.saveIdealBudget(idealBudget);
        return REDIRECT_IDEAL_BUDGET;
    }

    // 編集フォーム表示
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        IdealBudget idealBudget = idealBudgetService.getIdealBudgetById(id).orElse(null);
        if (idealBudget == null) {
            return REDIRECT_IDEAL_BUDGET;
        }
        model.addAttribute("idealBudget", idealBudget);
        return "ideal-budget/edit";
    }

    // 編集処理
    @PostMapping("/edit/{id}")
    public String updateIdealBudget(@PathVariable Long id, @ModelAttribute IdealBudget idealBudget) {
        idealBudget.setId(id);
        idealBudgetService.saveIdealBudget(idealBudget);
        return REDIRECT_IDEAL_BUDGET;
    }

    // 削除処理
    @PostMapping("/delete/{id}")
    public String deleteIdealBudget(@PathVariable Long id) {
        idealBudgetService.deleteIdealBudget(id);
        return REDIRECT_IDEAL_BUDGET;
    }
}