package es.bifacia.bgg.bean;

/**
 * Contains the information of a game.
 * 
 * @author alejandro
 *
 */
public class Game implements Comparable<Game> {
	public long id;
	public String name;
	public Integer year;

	public Game() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "ID: " + this.id + "   -   Game: " + this.name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	 
	  @Override
	  public int compareTo(Game game) {
	    if (name == null || game.getName() == null) {
	      return 0;
	    }
	    return this.name.compareTo(game.getName());
	  }

}
