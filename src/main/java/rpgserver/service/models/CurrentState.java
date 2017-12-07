package rpgserver.service.models;

public class CurrentState {
    /**
     * the curent position
     */
    private Position position;

    /**
     * the direction the animated element is facing
     */
    private Integer direction;

    /**
     * the animation frame index
     */
    private Integer frame;

    /**
     * the velocity of the animated element
     */
    private Integer velocity;

    /**
     * is the animated-element currently moving
     */
    private Boolean moving;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getFrame() {
        return frame;
    }

    public void setFrame(Integer frame) {
        this.frame = frame;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Boolean getMoving() {
        return moving;
    }

    public void setMoving(Boolean moving) {
        this.moving = moving;
    }
}
