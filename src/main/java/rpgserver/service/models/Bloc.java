package rpgserver.service.models;

/**
 * define a bloc in the map
 */
public class Bloc {

    /**
     * the sprite's identifier
     */
    private Integer spriteId;

    /**
     * the tile´ identifier
     */
    private Integer tileId;

    public Bloc(){
        this.tileId = -1;
        this.spriteId = -1;
    }

    public Integer getSpriteId() {
        return spriteId;
    }

    public void setSpriteId(Integer spriteId) {
        this.spriteId = spriteId;
    }

    public Integer getTileId() {
        return tileId;
    }

    public void setTileId(Integer tileId) {
        this.tileId = tileId;
    }
}
