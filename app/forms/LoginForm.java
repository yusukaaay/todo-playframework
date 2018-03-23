package forms;

import java.security.NoSuchAlgorithmException;

import com.avaje.ebean.Model.Finder;

import models.User;
import play.data.validation.Constraints;

/**
 * Formのコンストラクタに渡されると、実際にフォームをPOSTする前に、
 * このvalidate()メソッドを実行し、ユーザ/パスワードの組み合わせが正しいかどうかを判断してくれる
 *
 * @author yu-yama
 *
 */
public class LoginForm {

	@Constraints.Required
	private String username;
	@Constraints.Required
	private String password;

	// クエリを作成する為のfindヘルパー
	public static Finder<Long, User> user = new Finder<>(User.class);

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String validate() throws NoSuchAlgorithmException {
		if (authenticate(username, password) == null) {
			return "Invalid user or password";
		}
		return null;
	}

	public static User authenticate(String username, String password) throws java.security.NoSuchAlgorithmException {
		return user.where().eq("username", username).eq("password", password).findUnique();
	}

}
