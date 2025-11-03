package model.data;

import model.data.mysql.MySQLAnimeDAO;
import model.data.mysql.MySQLCategoryDAO;
import model.data.mysql.MySQLEpisodeDAO;
import model.data.mysql.MySQLUserDAO;

public class DAOFactory {

//	static Map<Class<?>, GenericDAO<?>> daos = new HashMap<>();
//	
//	static {
//		daos.put(Category.class, new MySQLCategoryDAO());
//	}
//	
//	public static Object getDAO(Class<?> c) throws ModelException{
//		System.out.println(c);
//		Object dao = daos.get(c);
//		
//		if(dao == null) throw new ModelException("class inválido");
//		
//		
//		return dao;
//	}
		
	public static MySQLCategoryDAO getCategoryDAO() {
		return new MySQLCategoryDAO();
	}
	public static MySQLUserDAO getUserDAO() {
		return new MySQLUserDAO();
	}
	public static MySQLAnimeDAO getAnimeDAO() {
		return new MySQLAnimeDAO();
	}
	
	public static MySQLEpisodeDAO getEpisodeDAO() {
		return new MySQLEpisodeDAO();
	}
	

	
}
