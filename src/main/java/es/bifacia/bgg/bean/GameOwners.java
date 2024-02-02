package es.bifacia.bgg.bean;

import java.util.List;

public class GameOwners {
    private Game game;
    private List<String> owners;

    public GameOwners() {
        super();
    }

    public GameOwners(final Game game, final List<String> owners) {
        this.game = game;
        this.owners = owners;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }
}
