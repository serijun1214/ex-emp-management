package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ(Dao)
 */
@Repository
public class EmployeeRepository {

  // NamedParameterJdbcTemplateをDI
  @Autowired
  NamedParameterJdbcTemplate template;

  // RowMapper
  private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
    Employee employee = new Employee();
    employee.setId(rs.getInt("id"));
    employee.setName(rs.getString("name"));
    employee.setImage(rs.getString("image"));
    employee.setGender(rs.getString("gender"));
    employee.setHireDate(rs.getDate("hire_date"));
    employee.setMailAddress(rs.getString("mail_address"));
    employee.setZipCode(rs.getString("zip_code"));
    employee.setAddress(rs.getString("address"));
    employee.setTelephone(rs.getString("telephone"));
    employee.setSalary(rs.getInt("salary"));
    employee.setCharacteristics(rs.getString("characteristics"));
    employee.setDependentsCount(rs.getInt("dependents_count"));
    return employee;
  };

  /**
   * 従業員一覧情報を入社日順(降順)で取得
   * @return 従業員情報のリスト
   */
  public List<Employee> findAll() {
    // SQL文を作成
    String sql = """
        SELECT
        id,
        name,
        image,
        gender,
        hire_date,
        mail_address,
        zip_code,address,
        telephone,salary,
        characteristics,
        dependents_count
        FROM
        employees
        ORDER BY
        hire_date DESC;
          """;

    // SQLを実行
    List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);
    // 結果返却
    return employees;
  }

  /**
   * 主キーから従業員情報を取得
   * 
   * @param id ID
   * @return 従業員情報
   */
  public Employee load(Integer id) {
    try {
      // SQL文を作成
      String sql = """
          SELECT
          id,
          name,
          image,
          gender,
          hire_date,
          mail_address,
          zip_code,
          address,
          telephone,
          salary,
          characteristics,
          dependents_count
          FROM
          employees
          WHERE id = :id;
            """;
      ;
      // パラメータを作成
      SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
      // SQLを実行
      Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
      // 結果返却
      return employee;
    } catch (EmptyResultDataAccessException e) {
      // データが見つからなかった場合の処理
      return null;
    }
  }

  /**
   * 従業員情報を変更
   * @param employee 変更したい従業員情報
   */
  public void update(Employee employee) {
    String sql = """
        UPDATE employees
        SET
        name =:name,
        image=:image,
        gender=:gender,
        hire_date=:hireDate,
        mail_address=:mailAddress,
        zip_code=:zipCode,
        address=:address,
        telephone=:telephone,
        salary=:salary,
        characteristics=:characteristics,
        dependents_count = :dependentsCount
        WHERE id = :id;
            """;
    SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
    template.update(sql, param);
  }
}
