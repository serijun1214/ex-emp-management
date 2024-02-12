package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;

/*:
 * 管理者情報を操作するコントローラ.
 */
@Controller
@RequestMapping("")
public class AdministratorController {

  // AdministratorServiceをDIする
  @Autowired
  private AdministratorService administratorService;

  // HttpSessionをDIする
  @Autowired
  private HttpSession session;

  /**
   * 管理者情報を挿入するフォームを表示
   * 
   * @param form
   * @return 管理者情報を挿入するフォーム画面
   */
  @GetMapping("/toInsert")
  public String toInsert(Model model, InsertAdministratorForm form) {
    return "administrator/insert";
  }

  /**
   * 管理者情報を挿入
   * 
   * @param form 挿入する管理者情報
   * @return ログイン画面
   */
  @PostMapping("/insert")
  public String insert(@Validated InsertAdministratorForm form, BindingResult result, Model model) {

    // もしエラーがあった場合は入力画面に遷移
    if (result.hasErrors()) {
      // 管理者情報を挿入するフォーム画面へ戻る
      return toInsert(model,form);
    }
    Administrator administrator = new Administrator();
    BeanUtils.copyProperties(form, administrator);
    administratorService.insert(administrator);
    return "redirect:/";
  }

  /**
   * ログイン画面を表示
   * 
   * @param form
   * @return ログイン画面
   */
  @GetMapping("/")
  public String toLogin(LoginForm form) {
    return "administrator/login";
  }

  /**
   * ログイン処理
   * 
   * @param form  ログイン情報
   * @param model
   * @return 従業員情報一覧画面
   */
  @PostMapping("/login")
  public String login(LoginForm form, Model model) {
    Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
    if (administrator == null) {
      model.addAttribute("errorMessages", "メールアドレスまたはパスワードが不正です。");
      return "administrator/login";
    }
    session.setAttribute("administratorName", administrator.getName());
    return "redirect:/employee/showList";
  }

  /**
   * ログアウト処理
   * 
   * @param form
   * @return ログイン画面
   */
  @GetMapping("/logout")
  public String logout(LoginForm form) {
    session.invalidate();
    return "redirect:/";
  }
}
