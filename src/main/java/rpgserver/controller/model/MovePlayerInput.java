package rpgserver.controller.model;

import rpgserver.service.models.Character;
import rpgserver.service.models.CurrentState;

/**
 * sent by a player when moving
 */
public class MovePlayerInput {

    /**
     * the player´ identifier
     */
    private String id;

    /**
     * the player´ current-state
     */
    private CurrentState currentState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrentState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CurrentState currentState) {
        this.currentState = currentState;
    }
}
