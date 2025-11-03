package view.swing.episode;

import model.entities.Episode;

public interface IEpisodeFormView {
    Episode getEpisodeFromForm();
    void setEpisodeInForm(Episode post);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}
