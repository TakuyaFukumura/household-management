package com.example.repository;

import com.example.entity.HouseholdExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class HouseholdExpenseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<HouseholdExpense> rowMapper = new RowMapper<HouseholdExpense>() {
        @Override
        public HouseholdExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
            HouseholdExpense expense = new HouseholdExpense();
            expense.setId(rs.getLong("id"));
            expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
            expense.setCategory(rs.getString("category"));
            expense.setAmount(rs.getBigDecimal("amount"));
            expense.setDescription(rs.getString("description"));
            if (rs.getTimestamp("created_at") != null) {
                expense.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            if (rs.getTimestamp("updated_at") != null) {
                expense.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
            return expense;
        }
    };

    // 全ての家計簿データを取得
    public List<HouseholdExpense> findAll() {
        String sql = "SELECT * FROM household_expenses ORDER BY expense_date DESC, id DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // IDで家計簿データを取得
    public HouseholdExpense findById(Long id) {
        String sql = "SELECT * FROM household_expenses WHERE id = ?";
        List<HouseholdExpense> expenses = jdbcTemplate.query(sql, rowMapper, id);
        return expenses.isEmpty() ? null : expenses.get(0);
    }

    // 家計簿データを保存
    public int save(HouseholdExpense expense) {
        String sql = "INSERT INTO household_expenses (expense_date, category, amount, description) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, expense.getExpenseDate(), expense.getCategory(), 
                                  expense.getAmount(), expense.getDescription());
    }

    // 家計簿データを更新
    public int update(HouseholdExpense expense) {
        String sql = "UPDATE household_expenses SET expense_date = ?, category = ?, amount = ?, description = ? WHERE id = ?";
        return jdbcTemplate.update(sql, expense.getExpenseDate(), expense.getCategory(), 
                                  expense.getAmount(), expense.getDescription(), expense.getId());
    }

    // 家計簿データを削除
    public int deleteById(Long id) {
        String sql = "DELETE FROM household_expenses WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}