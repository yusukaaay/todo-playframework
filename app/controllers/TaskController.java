package controllers;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * アクションを実行する
 *
 * @author yu-yama
 *
 */
@Singleton
@Security.Authenticated(models.Secured.class)
public class TaskController extends Controller {
	final static Logger logger = LoggerFactory.getLogger("application");

	@Inject
	FormFactory formFactory;

	/**
	 * タスク一覧画面
	 *
	 * @return
	 *
	 */
	public Result tasks() {
		Form<Task> taskForm = formFactory.form(Task.class);
		return ok(views.html.index.render(Task.all(), taskForm));
	}

	/**
	 * タスク追加
	 *
	 * @return
	 */
	public Result newTask() {
		// リクエストからデータ取得
		Form<Task> taskForm = formFactory.form(Task.class);
		Form<Task> filledForm = taskForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			logger.error("error");
			// フォームにエラーが合った場合、エラーを再表示する
			return badRequest(views.html.index.render(Task.all(), filledForm));
		} else {
			logger.info("else");
			// エラーがない場合、タスクを作成し、タスクリストにリダイレクトする
			Task.create(filledForm.get());
			return redirect(routes.TaskController.tasks());
		}
	}

	/**
	 * タスク削除
	 *
	 * @param id
	 * @return
	 */
	public Result deleteTask(Long id) {
		Task.delete(id);
		return redirect(routes.TaskController.tasks());
	}
}