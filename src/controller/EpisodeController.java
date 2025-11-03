package controller;

import java.util.List;

import model.ModelException;
import model.data.DAOFactory;
import model.data.EpisodeDAO;
import model.entities.Anime;
import model.entities.Episode;
import view.swing.episode.IEpisodeFormView;
import view.swing.episode.IEpisodeListView;

public class EpisodeController {
 private final EpisodeDAO episodeDAO = DAOFactory.getEpisodeDAO();
    private IEpisodeListView episodeListView;
    private IEpisodeFormView episodeFormView;

    public void loadEpisodes() {
        try {
            List<Episode> episodes = episodeDAO.findAll();
            episodeListView.setEpisodeList(episodes);
        } catch (ModelException e) {
            episodeListView.showMessage("Erro ao carregar episodes: " + e.getMessage());
        }
    }

    public void saveOrUpdate(boolean isNew) {
        Episode episode = episodeFormView.getEpisodeFromForm();

        try {
            episode.validate();
        } catch (IllegalArgumentException e) {
            episodeFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                episodeDAO.insert(episode);
            } else {
                episodeDAO.update(episode);
            }
            episodeFormView.showInfoMessage("Episode salvo com sucesso!");
            episodeFormView.close();
        } catch (ModelException e) {
            episodeFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // Excluir
    public void excluirEpisode(Episode episode) {
        try {
            episodeDAO.delete(episode);
            episodeListView.showMessage("Episode excluído!");
            loadEpisodes();
        } catch (ModelException e) {
            episodeListView.showMessage("Erro ao excluir episode: " + e.getMessage());
        }
    }

    public void setEpisodeFormView(IEpisodeFormView episodeFormView) {
        this.episodeFormView = episodeFormView;
    }

    public void setEpisodeListView(IEpisodeListView episodeListView) {
        this.episodeListView = episodeListView;
    }

    public List<Anime> getAllAnimes() {
        try {
            return DAOFactory.getAnimeDAO().findAll();
        } catch (ModelException e) {
            episodeFormView.showErrorMessage("Erro ao carregar animes: " + e.getMessage());
            return List.of();
        }
    }
}
