package rpgserver.config;

import java.security.Principal;

/**
 * custom class to manage the principal enity on web-socket
 */
public class StompPrincipal implements Principal {
    String name;

    StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}