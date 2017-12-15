package rpgserver.controller.model;

import rpgserver.service.models.Bloc;
import rpgserver.service.models.Character;
import rpgserver.service.models.WorldElement;

import java.util.List;
import java.util.Map;

/**
 * data returned to the client when registering a player
 */
public class RegisterPlayerOutput {

    /**
     * the newly-registered-player's identifier
     */
    private String playerId;

    /**
     * the map section to be seen by the player
     */
    private Bloc[][] map;

    /**
     * list of world-elements (trees, houses etc...)
     */
    private List<WorldElement> worldElements;

    /**
     * animated-elements on the world (other players for now)
     */
    private Map<String, Character> animatedElements;

    public Bloc[][] getMap() {
        return map;
    }

    public void setMap(Bloc[][] map) {
        this.map = map;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Map<String, Character> getAnimatedElements() {
        return animatedElements;
    }

    public void setAnimatedElements(Map<String, Character> animatedElements) {
        this.animatedElements = animatedElements;
    }

    public List<WorldElement> getWorldElements() {
        return worldElements;
    }

    public void setWorldElements(List<WorldElement> worldElements) {
        this.worldElements = worldElements;
    }
}
