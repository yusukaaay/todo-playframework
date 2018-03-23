package models;

import controllers.routes;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {
	@Override
	public String getUsername(Context ctx) {
		// セッションから"username"が取得できたら認証OK
		return ctx.session().get("username");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		// 権限のないユーザーがアクセスしようとしたときに呼ばれるメソッド
		return redirect(routes.Application.login());
	}
}
