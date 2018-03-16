
package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.Task;
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
		num++;
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
		// もし、フォームの内容にエラーが無いようであれば
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

	public Result tasks() {
		Form<Task> taskForm = formFactory.form(Task.class);
		return ok(views.html.index.render(Task.all(), taskForm));
	}

	public Result newTask() {
		// リクエストからデータ取得
		Form<Task> taskForm = formFactory.form(Task.class);
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			logger.error("error");
			System.out.println("error");
			// フォームにエラーが合った場合、エラーを再表示する
			return badRequest(views.html.index.render(Task.all(), filledForm));
		} else {
			logger.info("else");
			System.out.println("else");
			// エラーがない場合、タスクを作成し、タスクリストにリダイレクトする
			Task.create(filledForm.get());
			return redirect(routes.Application.tasks());
		}
	}

	public Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.Application.tasks());
	}
}