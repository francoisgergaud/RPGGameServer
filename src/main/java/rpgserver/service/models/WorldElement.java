package rpgserver.service.models;

/**
 * a world-element, can be a tree, an house, a bridge etc...
 * They have a width and a height (expressed in tile-size) and cannot superpose
 */
public class WorldElement {

    /**
     * the element's sprite identifier.
     */
    private Integer spriteId;

    /**
     * the element's position
     */
    private Position position;

    public Integer getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(Integer spriteId) {
        this.spriteId = spriteId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
