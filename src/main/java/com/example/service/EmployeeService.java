package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス
 */
@Service
@Transactional
public class EmployeeService {

  // EmployeeRepositoryをDIする
  @Autowired
  private EmployeeRepository employeeRepository;

  /**
   * 従業員情報を全件取得する
   * @return 従業員情報リスト
   */
  public List<Employee> showList() {
    return employeeRepository.findAll();
  }
}
