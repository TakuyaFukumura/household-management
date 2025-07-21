package com.example.controller;

import com.example.entity.HouseholdExpense;
import com.example.service.HouseholdExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/expenses")
public class HouseholdExpenseController {

    private final HouseholdExpenseService householdExpenseService;

    public HouseholdExpenseController(HouseholdExpenseService householdExpenseService) {
        this.householdExpenseService = householdExpenseService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
        });
    }

    // 家計簿一覧表示
    @GetMapping
    public String listExpenses(Model model) {
        List<HouseholdExpense> expenses = householdExpenseService.getAllExpenses();
        model.addAttribute("expenses", expenses);
        return "expenses/list";
    }

    // 新規追加フォーム表示
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new HouseholdExpense());
        return "expenses/add";
    }

    // 新規追加処理
    @PostMapping("/add")
    public String addExpense(@ModelAttribute HouseholdExpense expense) {
        householdExpenseService.saveExpense(expense);
        return "redirect:/expenses";
    }

    // 編集フォーム表示
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        HouseholdExpense expense = householdExpenseService.getExpenseById(id);
        if (expense == null) {
            return "redirect:/expenses";
        }
        model.addAttribute("expense", expense);
        return "expenses/edit";
    }

    // 編集処理
    @PostMapping("/edit/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute HouseholdExpense expense) {
        expense.setId(id);
        householdExpenseService.saveExpense(expense);
        return "redirect:/expenses";
    }

    // 削除処理
    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        householdExpenseService.deleteExpense(id);
        return "redirect:/expenses";
    }
}
