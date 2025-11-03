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
			if(!loggedUser.getPassword().equals(loginData.getPassword()))
				throw new ModelException("Senhas não coincidem");
				
		} catch (ModelException me) {
			throw me;
		}
		return loggedUser;
	}
	
	
}
