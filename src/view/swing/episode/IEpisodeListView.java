package view.swing.episode;

import java.util.List;

import model.entities.Episode;

public interface IEpisodeListView {
    void setEpisodeList(List<Episode> episodes);
    void showMessage(String msg);
}
