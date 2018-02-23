
package controllers;

import models.Task;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * アクションを実行する
 *
 * @author yu-yama
 *
 */
public class Application extends Controller {

	public Result index() {
		return redirect("/tasks");
		// return ok(index.render("Your new application is ready."));
	}

	public Result tasks() {
		Form<Task> taskForm = Form.form(Task.class);
		return ok(views.html.index.render(Task.all(), taskForm));
//		return TODO;
	}

	public Result newTask() {
		return TODO;
	}

	public Result deleteTask(Long id) {
		return TODO;
	}
}