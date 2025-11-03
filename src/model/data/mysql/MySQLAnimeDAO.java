package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.data.AnimeDAO;
import model.data.DAOUtils;
import model.data.mysql.utils.MySQLConnectionFactory;
import model.entities.Anime;
import model.entities.Category;

public class MySQLAnimeDAO implements AnimeDAO {

	@Override
	public void update(Anime data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		 try {
			con =  MySQLConnectionFactory.getConnection();
			String query = "UPDATE anime SET name = ?, description = ? WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getName());
			pst.setString(2, data.getDescription());
			pst.setInt(3, data.getId());
			System.out.println(pst);
			pst.executeUpdate();
		 } catch (SQLException sqle) {
			throw new ModelException("Erro ao atualizar anime " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public void insert(Anime data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		 try {
			con =  MySQLConnectionFactory.getConnection();
			String query = "INSERT INTO anime (name, description) VALUES (?, ?)";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getName());
			pst.setString(2, data.getDescription());
			
			pst.executeUpdate();
		 } catch (SQLException sqle) {
			throw new ModelException("Erro ao inserir anime " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
	}

	@Override
	public void delete(Anime data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		 try {
			con =  MySQLConnectionFactory.getConnection();
			String query = "DELETE FROM anime WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			
			pst.executeUpdate();
		 } catch (SQLException sqle) {
			throw new ModelException("Erro ao excluir anime " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
	}

	@Override
	public Anime findById(Anime data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Anime anime = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM anime WHERE id = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				anime = new Anime(id, name, description);
				findAnimeCategories(anime);
			}
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar categorias do Anime " + sqle);
		}
		finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		return anime;
	}

	@Override
	public List<Anime> findAll() throws ModelException {
		
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Anime> animes = new ArrayList<Anime>();
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT * FROM anime ";
			pst = con.prepareStatement(query);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				Anime anime = new Anime(id, name, description);
				findAnimeCategories(anime);
				animes.add(anime);
			}
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar animes" + sqle);
		}
		finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		
		return animes;
	}
	
	private void findAnimeCategories (Anime anime) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT c.id, c.category FROM anime_category INNER JOIN category c on c.id = Category_id WHERE Anime_id = ? ";
			pst = con.prepareStatement(query);
			pst.setInt(1, anime.getId());
			rs = pst.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				Category animeCategory = new Category(id, category);
				anime.addCategory(animeCategory);
			}
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar categorias do Anime " + sqle);
		}
		finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
	}

	@Override
	public void addCategory(Category categoryData, Anime animeData) throws ModelException {
		// TODO Auto-generated method stub
	}

}
