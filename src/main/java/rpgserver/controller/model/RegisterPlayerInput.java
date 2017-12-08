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
     * the character's appearance'sidentifier
     */
    private Integer characterId;

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

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }
}
