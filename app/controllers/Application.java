
package controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.Task;
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
public class Application extends Controller {
	final static Logger logger = LoggerFactory.getLogger("application");

	private Form<Task> taskForm;

	@Inject
	public Application(FormFactory formFactory) {
		// ラップしてフォーム送信
		this.taskForm = formFactory.form(Task.class);
	}

	public Result index() {
		return redirect("/tasks");
	}

	public Result tasks() {
		return ok(views.html.index.render(Task.all(), taskForm));
	}

	public Result newTask() {
		// リクエストからデータ取得
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