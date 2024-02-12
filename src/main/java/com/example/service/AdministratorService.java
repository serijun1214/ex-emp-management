package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス
 */
@Service
@Transactional
public class AdministratorService {
  
  // AdministratorRepositoryをDI
  @Autowired
  private AdministratorRepository administratorRepository;

  /**
   * 管理者情報を挿入
   * @param administrator 管理者情報
   */
  public void insert(Administrator administrator) {
    administratorRepository.insert(administrator);
  }

  /**
   * ログイン処理
   * @param mailAddress
   * @param password
   * @return 管理者情報
   */
  public Administrator login(String mailAddress, String password) {
    return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
  }
}
