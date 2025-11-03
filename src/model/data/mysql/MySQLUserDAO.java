package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.data.DAOUtils;
import model.data.UserDAO;
import model.data.mysql.utils.MySQLConnectionFactory;
import model.entities.User;

public class MySQLUserDAO implements UserDAO {

	@Override
	public void update(User data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "UPDATE user set name = ?, email = ?, password = ? WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getName());
			pst.setString(2, data.getEmail());
			pst.setString(3, data.getPassword());
			pst.setInt(4, data.getId());
			
			pst.executeUpdate();
			
			} catch (SQLException sqle) {
			throw new ModelException("Erro ao atualizar Usuário" + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public void insert(User data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "INSERT INTO user (name, email, password) VALUES ( ?, ? , ? )";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getName());
			pst.setString(2, data.getEmail());
			pst.setString(3, data.getPassword());
			
			pst.executeUpdate();
			
			} catch (SQLException sqle) {
			throw new ModelException("Erro ao inserir Usuário" + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public void delete(User data) throws ModelException {
		Connection con  = null;
		PreparedStatement pst = null;
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "DELETE FROM user WHERE id = ?";
			pst = con.prepareStatement(query);
			
			pst.setInt(1, data.getId());
			pst.execute();
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao excluir usuário " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public User findById(User data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM user WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				user = new User(id, name, email, password);

			}
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao obter usuário por id" + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		return user;
	}

	@Override
	public List<User> findAll() throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM user";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				User user = new User(id, name, email, password);
				users.add(user);
			}
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao obter usuário por id" + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		return users;
	}

	@Override
	public User findByEmail(User data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM user WHERE email = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getEmail());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				
				user = new User(id, name, email, password);
				System.out.println(user);
			}
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao obter usuário por id" + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		return user;
	}

}
