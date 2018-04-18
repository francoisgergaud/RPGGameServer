package rpgserver.controller.model;

import java.util.List;

/**
 * a chat-input message
 */
public class ChatInput {

    /**
     * the origin
     */
    private String from;

    /**
     * the list of destinations
     */
    private List<String> to;

    /**
     * the message
     */
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
