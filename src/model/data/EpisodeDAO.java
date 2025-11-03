package model.data;

import java.util.List;

import model.ModelException;
import model.entities.Anime;
import model.entities.Episode;

public interface EpisodeDAO extends GenericDAO<Episode> {

	List<Episode> getEpisodesByAnime(Anime anime) throws ModelException;
}
