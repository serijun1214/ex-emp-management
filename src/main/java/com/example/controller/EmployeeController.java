package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラ
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
  
  // EmployeeServiceをDI
  @Autowired
  private EmployeeService employeeService;

  /**
   * 従業員情報一覧画面を表示
   * @param model
   * @return 従業員情報一覧画面
   */
  @GetMapping("/showList")
  public String showList(Model model) {
    List<Employee> employeeList = employeeService.showList();
    model.addAttribute("employeeList", employeeList);
    return "employee/list";
  }

  /**
   * 従業員情報詳細画面を表示
   * @param id 従業員ID
   * @param model 
   * @param form
   * @return 従業員情報詳細画面
   */
  @GetMapping("/showDetail")
  public String showDetail(String id, Model model, UpdateEmployeeForm form) {
    Employee employee = employeeService.showDetail(Integer.parseInt(id));
    model.addAttribute("employee", employee);
    return "employee/detail";
  }

  /**
   * 従業員情報を更新
   * @param form 更新する従業員情報
   * @return 従業員情報一覧画面
   */
  @PostMapping("/update")
  public String update(UpdateEmployeeForm form) {
    Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
    employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
    employeeService.update(employee);
    return "redirect:/employee/showList";
  }
}
