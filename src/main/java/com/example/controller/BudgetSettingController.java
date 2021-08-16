package com.example.controller;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bean.form.BudgetSettingForm;

@Controller
public class BudgetSettingController {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @RequestMapping("/budgetSetting")
  public String index(BudgetSettingForm budgetSettingForm) {
    //login情報から既存の設定を取得
    return "budgetSetting";
  }

  @RequestMapping("/budgetSetting/submit")
  public String submit(BudgetSettingForm budgetSettingForm) {
    //myselfIdとpartnerIdの仮設定
    List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM t_household_account_books");//where myselfId
    if (list.size() < 1) {
      //insert
      System.out.println(budgetSettingForm.getFoodExpenses());
      //jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES ('John','Woo')");
    } else {
      //update
      //jdbcTemplate.update("INSERT INTO customers(first_name, last_name) VALUES ('John','Woo')");
    }
    

    return "budgetSetting";
  }

  @RequestMapping("/budgetSetting/create")
  public String create() {
    jdbcTemplate.execute("CREATE TABLE t_household_account_books(" +
                "id IDENTITY NOT NULL PRIMARY KEY,"+ //long
                "myself_id BIGINT UNIQUE ,"+ //long 
                "partner_id BIGINT UNIQUE ,"+ //long 
                "food_expenses DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "daily_use_items DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "hobbies_and_leisure_expenses DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "entertainment_expenses DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "transport_costs DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "clothing_and_beauty DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "health_and_medical DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "car_cost DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "liberal_arts_expenses DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "special_spending DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "cash_or_card DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "utility_costs DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "phone_and_internet_costs DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "housing_expenses DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "taxes_and_social_security DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "insurance DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "others DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "uncategorized DECIMAL DEFAULT 0 NOT NULL,"+ //BigDecimal 
                "create_at TIMESTAMP DEFAULT CURRENT_DATE(),"+ //Timestamp
                "update_at TIMESTAMP DEFAULT CURRENT_DATE(),"+  //Timestamp
                "is_deleted BOOLEAN DEFAULT FALSE)" //Boolean
                );
    return "budgetSetting";
  }

}
