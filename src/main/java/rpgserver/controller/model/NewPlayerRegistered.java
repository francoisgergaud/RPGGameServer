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
