package controller;

import java.util.List;

import model.ModelException;
import model.data.AnimeDAO;
import model.data.DAOFactory;
import model.entities.Anime;
import view.swing.anime.IAnimeFormView;
import view.swing.anime.IAnimeListView;

public class AnimeController {

		private final AnimeDAO animeDAO = DAOFactory.getAnimeDAO();
		private IAnimeFormView animeFormView;
		private IAnimeListView animeListView;
		
		
		public void loadAnimes() {
			try {
				List<Anime> animes = animeDAO.findAll();
				animeListView.setAnimeList(animes);
			} catch (ModelException e) {
				animeListView.showMessage("Erro ao carregar animes: " + e.getMessage());
			}
		}
		
		public void saveOrUpdate(boolean isNew){
			Anime anime = animeFormView.getAnimeFromForm();
			try {	
				anime.validate();
			} catch (IllegalArgumentException e) {
	            animeFormView.showErrorMessage("Erro de validação: " + e.getMessage());
	            return;
	        }
			
			try {
				if(isNew) 
					animeDAO.insert(anime);
				else
					animeDAO.update(anime);
				
				animeFormView.showInfoMessage("Anime salvo com sucesso!!");
				animeFormView.close();
			} catch (Exception e) {
				 animeFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
			}
			
		}
		
		public void excluirUsuario(Anime anime) {
	        try {
	            animeDAO.delete(anime);
	            animeListView.showMessage("Usuário excluído!");
	            loadAnimes();
	        } catch (ModelException e) {
	            animeListView.showMessage("Erro ao excluir: " + e.getMessage());
	        }
	    }
		
		
		  public void setAnimeFormView(IAnimeFormView animeFormView) {
		        this.animeFormView = animeFormView;
		    }

		    public void setAnimeListView(IAnimeListView animeListView) {
		        this.animeListView = animeListView;
		    }
		
}
