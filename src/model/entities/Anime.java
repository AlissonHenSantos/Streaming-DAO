package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Anime {

	private Integer id;
	private String name;
	private String description;
	private List<Category> categories;
	
	public Anime() {
		this.categories = new ArrayList<Category>();
	}

	public Anime(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.categories = new ArrayList<Category>();
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anime other = (Anime) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Anime [id=" + id + ", name=" + name + ", description=" + description + ", categories=" + categories
				+ "]";
	}
	
	public void addCategory(Category category) {
		if(category == null)
			throw new IllegalArgumentException("Passe uma categoria válida");
		
		categories.add(category);
	}
	
	
}
