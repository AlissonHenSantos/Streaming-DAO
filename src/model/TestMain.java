package model;

import java.util.ArrayList;
import java.util.List;

import model.data.DAOFactory;
import model.data.UserDAO;
import model.entities.User;

public class TestMain {

	
	public static void main(String[] args) throws ModelException {
		
//		GenericDAO<Category> dao =  DAOFactory.getCategoryDAO();
//		
//		Category newCategory = new Category(2, "Ação");
//		dao.delete(newCategory);
//		
//		List<Category> categories = dao.findAll();
//		
//		
//		for (Category category : categories) {
//			System.out.println(category);
//		}
//		
//		System.out.println();
//		
//		Category c = new Category();
//		c.setId(1);
//		
//		Category categoryFinded = dao.findById(c);
//		System.out.println(categoryFinded);
//		

		
		UserDAO userDAO = DAOFactory.getUserDAO();
			User u =  new User(0, "Alisson henrique", "alissonhenriqued@gmail.com", "123");
			//userDAO.insert(u);
	
		List<User> users = userDAO.findAll();
	for (User user : users) {
		System.out.println(user);
		}
	}
	
}
