package rpgserver.controller.model;

/**
 * data sent by the client when registering
 */
public class RegisterPlayerInput {

    /**
     * the player´s identifier
     */
    private String id;

    /**
     * @return the player´ s identifier
     */
    public String getId() {
        return id;
    }

    /**
     * set the player´ identifier
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}
