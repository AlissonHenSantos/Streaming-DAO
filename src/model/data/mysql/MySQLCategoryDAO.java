package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.data.DAOUtils;
import model.data.GenericDAO;
import model.data.mysql.utils.MySQLConnectionFactory;
import model.entities.Category;

public class MySQLCategoryDAO implements GenericDAO<Category> {

	@Override
	public void update(Category data) throws ModelException{
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "UPDATE category SET category = ? WHERE id = ?";
			
			pst = con.prepareStatement(query);
			pst.setString(1, data.getCategory());
			pst.setInt(2, data.getId());
			pst.execute();
			
		}catch (SQLException sqle) {
			throw new ModelException("Erro ao inserir categoria " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public void insert(Category data) throws ModelException{
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "INSERT INTO category (category) VALUES (?)";
			
			pst = con.prepareStatement(query);
			pst.setString(1, data.getCategory());
			pst.execute();
			
		}catch (SQLException sqle) {
			throw new ModelException("Erro ao inserir categoria " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public void delete(Category data) throws ModelException{
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "DELETE FROM category WHERE id = ?;";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			pst.executeUpdate();
			
			
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao excluir categoria " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public Category findById(Category data) throws ModelException{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Category category = null;
		try {
			con =  MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM category WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String categoryText = rs.getString("category");
				category = new Category(id, categoryText);
			}
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar categoria por id " + sqle);
		}finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		
		return category;
	}

	@Override
	public List<Category> findAll() throws ModelException{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Category> categories = new ArrayList<Category>();
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM category";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery(query);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				
				Category newCategory = new Category(id, category);
				
				categories.add(newCategory);
			}
			
		}catch (SQLException sqle) {
			DAOUtils.sqlExceptionTratement("Erro ao listar categorias", sqle);
		}
		finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		return categories;
	}

}
