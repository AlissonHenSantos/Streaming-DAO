package controller;

import model.ModelException;
import model.data.DAOFactory;
import model.data.UserDAO;
import model.entities.User;

public class AuthController {
	private UserDAO userDAO = DAOFactory.getUserDAO();
	
	
	
	public User login(User loginData) throws ModelException {
		User loggedUser = null;
		try {
			loggedUser = userDAO.findByEmail(loginData);
			if(loggedUser == null) {
				throw new ModelException("Usuário não encontrado");
			}
			System.out.println(loggedUser);
			if(!PasswordEncoder.verifyPassword(loginData.getPassword(), loggedUser.getPassword()))
				throw new ModelException("Senhas não coincidem");
				
		} catch (ModelException me) {
			throw me;
		}
		return loggedUser;
	}

	public void register(User newUser) throws ModelException {
		try {
			User existingUser = userDAO.findByEmail(newUser);
			if(existingUser != null) {
				throw new ModelException("Usuário já existe com esse email");
			}
			newUser.setPassword(PasswordEncoder.hashPassword(newUser.getPassword()));
			userDAO.insert(newUser);
		} catch (ModelException me) {
			throw me;
		}
	}

	public User findByEmail(String email) throws ModelException {
        return userDAO.findByEmail(new User(0, "", email, ""));
    }

	public boolean recoverPassword(String email, String newPassword) throws ModelException {
		 User user = userDAO.findByEmail(new User(0, "", email, ""));
        if (user == null) return false;
        user.setPassword(PasswordEncoder.hashPassword(newPassword));
        userDAO.update(user);
        return true;
	}
	
	
}
