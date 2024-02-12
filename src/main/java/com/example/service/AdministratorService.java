package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス
 */
@Service
@Transactional
public class AdministratorService {
  
  // AdministratorRepositoryをDIする
  @Autowired
  private AdministratorRepository administratorRepository;
}
