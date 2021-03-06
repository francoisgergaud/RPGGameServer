package rpgserver.service.models;

/**
 * the playable characters
 */
public class Character {

    /**
     * the identifier
     */
    private String id;

    /**
     * the name
     */
    private String name;

    /**
     * the current-state of the animated element
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

