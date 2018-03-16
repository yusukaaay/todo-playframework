package forms;

import java.security.NoSuchAlgorithmException;

import models.User;
import play.data.validation.Constraints;

public class LoginForm {

	@Constraints.Required
	private String username;
	@Constraints.Required
	private String password;

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
		// Model.Finder<Long, User> find = new Model.Finder<Long,
		// User>(Long.class, User.class);
		// return find.where().eq("username", username).eq("password",
		// password).findUnique();
		return null;
	}

}
