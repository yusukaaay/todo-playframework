package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaje.ebean.Model;

import play.data.validation.Constraints.Required;

/**
 * Taskフォーム
 * <p>
 * HTMLフォームの定義をカプセル化する
 * </p>
 *
 * @author yu-yama
 *
 */
@Entity
public class Task extends Model {
	final static Logger logger = LoggerFactory.getLogger("application");

	@Id
	public Long id;

	@Required
	public String label;

	// クエリを作成する為のfindヘルパー
	public static Finder<Long, Task> find = new Finder<>(Task.class);

	public static List<Task> all() {
		logger.debug("allメソッド");
		return find.all();
	}

	public static void create(Task task) {
		logger.debug("createメソッド");
		task.save();
	}

	public static void delete(Long id) {
		logger.debug("deleteメソッド [" + id + "]");
		find.ref(id).delete();
	}

}