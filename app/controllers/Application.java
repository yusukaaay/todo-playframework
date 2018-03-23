
package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import forms.LoginForm;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * アクションを実行する
 *
 * @author yu-yama
 *
 */
@Singleton
public class Application extends Controller {
	final static Logger logger = LoggerFactory.getLogger("application");

	int num;

	// private Form<Task> taskForm;

	@Inject
	FormFactory formFactory;

	// @Inject
	// public Application(FormFactory formFactory) {
	// // ラップしてフォーム送信
	// this.taskForm = formFactory.form(Task.class);
	// }
	//
	public Result index() {
		logger.info(String.valueOf(num));
		return redirect("/tasks");
	}

	/**
	 * ユーザ情報入力画面
	 *
	 * @return
	 */
	public Result signup() {
		// Userクラスの各フィールド(id, username, mail...)と同じ属性を持つFormオブジェクトを生成
		Form<User> userForm = formFactory.form(User.class);

		return ok(views.html.signup.render("Sign up", userForm));
	}

	/**
	 * ユーザ情報登録画面
	 *
	 * @return
	 */
	public Result register() {
		// 投稿されたフォームの内容をuserFormオブジェクトとしてインスタンス化
		Form<User> userForm = formFactory.form(User.class).bindFromRequest();
		// フォームの内容にエラーが無いようであれば
		if (!userForm.hasErrors()) {
			// エンティティモデルであるUser オブジェクトにフォームの内容を登録する。
			User userRecord = userForm.get();
			// レコードの登録
			userRecord.save();
			return redirect("/tasks");
		} else {
			return ok(views.html.signup.render("ERROR", userForm));
		}
	}

	/**
	 * ログイン画面
	 *
	 * @return
	 */
	public Result login() {
		// LoginFormクラスの各フィールドと同じ属性を持つFormオブジェクトを生成
		Form<LoginForm> loginForm = formFactory.form(LoginForm.class);
		return ok(views.html.login.render(loginForm));
	}

	/**
	 * 認証処理
	 *
	 * @return
	 */
	public Result authenticate() {
		Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();
		if (loginForm.hasErrors()) {
			Form<LoginForm> newLoginForm = formFactory.form(LoginForm.class);
			return badRequest(views.html.login.render(newLoginForm));
		} else {
			// エラーがない場合、
			// セッション("username")にフォームに入力した値(username)を格納
			session().clear();
			session("username", loginForm.get().getUsername());
			String returnUrl = ctx().session().get("returnUrl");
			if (returnUrl == null || returnUrl.equals("")
					|| returnUrl.equals(routes.Application.login().absoluteURL(request()))) {
				returnUrl = "/tasks";
			}
			return redirect(returnUrl);
		}
	}
}