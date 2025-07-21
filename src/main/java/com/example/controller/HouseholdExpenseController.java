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
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping({"/", "/expenses"})
public class HouseholdExpenseController {

    private static final String REDIRECT_EXPENSES = "redirect:/expenses";

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

    // 家計簿一覧表示（年月フィルタリング対応）
    @GetMapping
    public String listExpenses(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "month", required = false) Integer month,
            Model model) {
        
        // 年月が指定されていない場合は現在月を使用
        LocalDate targetDate;
        if (year == null || month == null) {
            targetDate = LocalDate.now();
        } else {
            targetDate = LocalDate.of(year, month, 1);
        }
        
        List<HouseholdExpense> expenses = householdExpenseService.getExpensesByYearAndMonth(
                targetDate.getYear(), targetDate.getMonthValue());
        
        model.addAttribute("expenses", expenses);
        model.addAttribute("currentYear", targetDate.getYear());
        model.addAttribute("currentMonth", targetDate.getMonthValue());
        
        // 前月・次月の計算
        LocalDate prevMonth = targetDate.minusMonths(1);
        LocalDate nextMonth = targetDate.plusMonths(1);
        model.addAttribute("prevYear", prevMonth.getYear());
        model.addAttribute("prevMonth", prevMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());
        
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
        return REDIRECT_EXPENSES;
    }

    // 編集フォーム表示
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        HouseholdExpense expense = householdExpenseService.getExpenseById(id).orElse(null);
        if (expense == null) {
            return REDIRECT_EXPENSES;
        }
        model.addAttribute("expense", expense);
        return "expenses/edit";
    }

    // 編集処理
    @PostMapping("/edit/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute HouseholdExpense expense) {
        expense.setId(id);
        householdExpenseService.saveExpense(expense);
        return REDIRECT_EXPENSES;
    }

    // 削除処理
    @PostMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        householdExpenseService.deleteExpense(id);
        return REDIRECT_EXPENSES;
    }
}
