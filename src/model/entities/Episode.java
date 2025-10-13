package model.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Episode {
	private Integer id;
	private String title;
	private LocalDate releaseDate;
	private Integer number;
	private Anime anime;
	
	public Episode() {
		
	}

	public Episode(Integer id, String title, LocalDate releaseDate, Integer number, Anime anime) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.number = number;
		this.anime = anime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Anime getAnime() {
		return anime;
	}

	public void setAnime(Anime anime) {
		this.anime = anime;
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
		Episode other = (Episode) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Episode [id=" + id + ", title=" + title + ", releaseDate=" + releaseDate + ", number=" + number
				+ ", anime=" + anime + "]";
	}
	
	
}
