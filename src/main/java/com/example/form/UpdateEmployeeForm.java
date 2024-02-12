package com.example.form;

/**
 * 従業員情報更新時に使用するフォーム
 */
public class UpdateEmployeeForm {
  // 従業員 ID
  private String id;
  // 扶養人数
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "UpdateEmployeeForm [id=" + id + ", name=" + name + "]";
  }
}
