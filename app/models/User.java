package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

/**
 * ユーザ情報
 *
 * @author yu-yama
 *
 */
@Entity
public class User extends Model {
	@Id
	public Long id;

	@Required
	public String username;

	@Email
	public String mail;

	public String image;

	@Required
	public String password;

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

	@Override
	public String toString() {
		return ("[id:" + id + ", username:" + username + ", mail:" + mail + "]");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUserame(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}
}
