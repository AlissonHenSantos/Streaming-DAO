package model.data.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.data.DAOUtils;
import model.data.EpisodeDAO;
import model.data.mysql.utils.MySQLConnectionFactory;
import model.entities.Anime;
import model.entities.Episode;
import model.utils.DateConverter;

public class MySQLEpisodeDAO implements EpisodeDAO{

	@Override
	public void update(Episode data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "UPDATE episode title = ?, releaseDate = ?, number = ?, Anime = ? WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setString(1, data.getTitle());
			pst.setDate(2, DateConverter.convertDateToDB(data.getReleaseDate()));
			pst.setInt(3, data.getNumber());
			pst.setInt(4, data.getAnime().getId());
			pst.setInt(5, data.getId());
			pst.execute();
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao atualizar episódio " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
	}

	@Override
	public void insert(Episode data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "INSERT INTO episode (title, releaseDate, number, Anime) VALUES ( ?, now(), ?, ? )";
			pst = con.prepareStatement(query);
			System.out.println(data.getNumber());
			pst.setString(1, data.getTitle());
			pst.setInt(2, data.getNumber());
			pst.setInt(3, data.getAnime().getId());
			pst.execute();
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao inserir episódio " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
	}

	@Override
	public void delete(Episode data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "DELETE FROM episode WHERE id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			pst.execute();
			
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao excluir episódio " + sqle);
		}finally {
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
	}

	@Override
	public Episode findById(Episode data) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Episode episode = null;
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT e.id, e.title, e.releaseDate, e.number, a.id as idAnime, a.name, a.description FROM episode e INNER JOIN anime a on a.id = e.Anime WHERE e.id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, data.getId());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				int episodeId = rs.getInt("id");
				String title = rs.getString("title");
				LocalDate ReleaseDate = DateConverter.convertDateFromDB(rs.getDate("releaseDate"));
				int number = rs.getInt("number");
				int idAnime = rs.getInt("idAnime");
				
				String name = rs.getString("name");
				String description = rs.getString("description");
				
				Anime anime = new Anime();
				anime.setId(idAnime);
				anime.setDescription(description);
				anime.setName(name);
				
				episode = new Episode();
				episode.setId(episodeId);
				episode.setNumber(number);
				episode.setTitle(title);
				episode.setReleaseDate(ReleaseDate);
				episode.setAnime(anime);
				
				
			}
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar Episódios " + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		return episode;
	}

	@Override
	public List<Episode> findAll() throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Episode> episodes = new ArrayList<>();
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT e.id, e.title, e.releaseDate, e.number, a.id as idAnime, a.name, a.description FROM episode e INNER JOIN anime a on a.id = e.Anime";
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();
			
			buildListEpisode(rs, episodes);
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar Episódios " + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		return episodes;
	}

	@Override
	public List<Episode> getEpisodesByAnime(Anime animeData) throws ModelException {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Episode> episodes = new ArrayList<>();
		try {
			con = MySQLConnectionFactory.getConnection();
			String query = "SELECT e.id, e.title, e.releaseDate, e.number, a.id as idAnime, a.name, a.description FROM episode e INNER JOIN anime a on a.id = e.Anime WHERE a.id = ?";
			pst = con.prepareStatement(query);
			pst.setInt(1, animeData.getId());
			rs = pst.executeQuery();
			
			buildListEpisode(rs, episodes);
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao buscar Episódios " + sqle);
		} finally {
			DAOUtils.close(rs);
			DAOUtils.close(con);
			DAOUtils.close(pst);
		}
		return episodes;
	}
	
	private List<Episode> buildListEpisode (ResultSet rs, List<Episode> episodes) throws ModelException{
		
		try {
			while(rs.next()) {
				int episodeId = rs.getInt("id");
				String title = rs.getString("title");
				LocalDate ReleaseDate = DateConverter.convertDateFromDB(rs.getDate("releaseDate"));
				int number = rs.getInt("number");
				int idAnime = rs.getInt("idAnime");
				
				String name = rs.getString("name");
				String description = rs.getString("description");
				
				Anime anime = new Anime();
				anime.setId(idAnime);
				anime.setDescription(description);
				anime.setName(name);
				
				Episode episode = new Episode();
				episode.setId(episodeId);
				episode.setNumber(number);
				episode.setTitle(title);
				episode.setReleaseDate(ReleaseDate);
				episode.setAnime(anime);
				
				episodes.add(episode);
			}
		} catch (SQLException sqle) {
			throw new ModelException("Erro ao construir lista de episódios " + sqle);
		}
		return episodes;
	}

}
