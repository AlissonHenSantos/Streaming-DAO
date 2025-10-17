package model.data;

import model.ModelException;
import model.entities.User;

public interface UserDAO extends GenericDAO<User> {

	User findByEmail(User data) throws ModelException;
}
