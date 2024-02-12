package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

/**
 * administeratorsテーブルを操作するリポジトリ(Dao)
 */
@Repository
public class AdministratorRepository {

  // NamedParameterJdbcTemplateをDI
  @Autowired
  NamedParameterJdbcTemplate template;

  // RowMapper
  private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
    Administrator administrator = new Administrator();
    administrator.setId(rs.getInt("id"));
    administrator.setName(rs.getString("name"));
    administrator.setMailAddress(rs.getString("mail_address"));
    administrator.setPassword(rs.getString("password"));
    return administrator;
  };

  /**
   * 管理者情報を挿入
   * @param administrator 管理者情報
   */

  public void insert(Administrator administrator) {
    // SQL文を作成
    String sql = "INSERT INTO administrators (name, mail_address, password) VALUES (:name, :mailAddress, :password)";

    // パラメータを作成
    SqlParameterSource param = new MapSqlParameterSource()
    .addValue("name", administrator.getName())
    .addValue("mailAddress", administrator.getMailAddress())
    .addValue("password", administrator.getPassword());

    // SQLを実行
    template.update(sql, param);
  }

  /**
   * メールアドレスとパスワードから管理者情報を取得
   * @param maillAddress メールアドレス
   * @param password     パスワード
   * @return Administrator 管理者情報
   */
  public Administrator findByMailAddressAndPassword(String maillAddress, String password) {
    // SQL文を作成
    String sql = "SELECT id, name, mail_address, password FROM administrators WHERE mail_address = :mailAddress AND password = :password";

    // パラメータを作成
    SqlParameterSource param = new MapSqlParameterSource()
    .addValue("mailAddress", maillAddress)
    .addValue("password",password);

    // SQLを実行
    List<Administrator> administratorList = template.query(sql, param, ADMINISTRATOR_ROW_MAPPER);
    // 結果がなければnullを返す
    if (administratorList.size() == 0) {
      return null;
    }
    // 結果があれば最初の要素を返す
    return administratorList.get(0);
  }
}
