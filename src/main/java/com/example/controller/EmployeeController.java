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

@Controller
@RequestMapping("/employee")
public class EmployeeController {
  
  @Autowired
  private EmployeeService employeeService;

  @GetMapping("/showList")
  public String showList(Model model) {
    List<Employee> employeeList = employeeService.showList();
    model.addAttribute("employeeList", employeeList);
    return "employee/list";
  }

  @GetMapping("/showDetail")
  public String showDetail(String id, Model model, UpdateEmployeeForm form) {
    Employee employee = employeeService.showDetail(Integer.parseInt(id));
    model.addAttribute("employee", employee);
    return "employee/detail";
  }

  @PostMapping("/update")
  public String update(UpdateEmployeeForm form) {
    Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
    employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
    employeeService.update(employee);
    return "redirect:/employee/showList";
  }
}
