package model;

import java.util.List;

import model.data.DAOFactory;
import model.data.GenericDAO;
import model.entities.Category;

public class TestMain {

	
	public static void main(String[] args) throws ModelException {
		
		GenericDAO<Category> dao =  DAOFactory.getDAOCategory();
		
		Category newCategory = new Category(2, "Ação");
		dao.delete(newCategory);
		
		List<Category> categories = dao.findAll();
		
		
		for (Category category : categories) {
			System.out.println(category);
		}
		
		System.out.println();
		
		Category c = new Category();
		c.setId(1);
		
		Category categoryFinded = dao.findById(c);
		System.out.println(categoryFinded);
		
		
		
		
	}
	
}
