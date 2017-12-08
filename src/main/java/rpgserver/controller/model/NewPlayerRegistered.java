package rpgserver.controller.model;

import rpgserver.service.models.CurrentState;

/**
 * sent to all other players when a new player is registered
 */
public class NewPlayerRegistered {

    /**
     * the player unique identifier
     */
    private String id;

    /**
     * the character's appearance's identifier
     */
    private Integer characterId;

    /**
     * the player´ name
     */
    private String name;

    /**
     * current-state of the new registered player
     */
    private CurrentState currentState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }
}
