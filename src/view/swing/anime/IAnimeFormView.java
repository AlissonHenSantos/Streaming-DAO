package view.swing.anime;

import model.entities.Anime;

public interface IAnimeFormView {
		Anime getAnimeFromForm();
	    void setAnimeInForm(Anime user);
	    void showInfoMessage(String msg);
	    void showErrorMessage(String msg);
	    void close();
}
