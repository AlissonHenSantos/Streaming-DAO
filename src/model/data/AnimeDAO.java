package model.data;

import model.ModelException;
import model.entities.Anime;
import model.entities.Category;

public interface AnimeDAO extends GenericDAO<Anime>{

	public void addCategory(Category categoryData, Anime animeData) throws ModelException;
	
}
