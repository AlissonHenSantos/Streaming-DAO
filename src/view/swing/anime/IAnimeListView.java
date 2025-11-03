package view.swing.anime;

import java.util.List;

import model.entities.Anime;

public interface IAnimeListView {
	void setAnimeList(List<Anime> users);
    void showMessage(String msg);
}
