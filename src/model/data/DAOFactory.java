package model.data;

import java.util.HashMap;
import java.util.Locale.Category;
import java.util.Map;

import model.ModelException;
import model.data.mysql.MySQLCategoryDAO;

public class DAOFactory {

	static Map<Class<?>, GenericDAO<?>> daos = new HashMap<>();
	
	static {
		daos.put(Category.class, new MySQLCategoryDAO());
	}
	
	public static Object getDAO(Class<?> c) throws ModelException{
		System.out.println(c);
		Object dao = daos.get(c);
		
		if(dao == null) throw new ModelException("class inválido");
		
		
		return dao;
	}
	
	public static MySQLCategoryDAO getDAOCategory() {
		return new MySQLCategoryDAO();
	}
	
}
