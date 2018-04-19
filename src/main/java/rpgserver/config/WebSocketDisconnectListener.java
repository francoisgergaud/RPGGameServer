package rpgserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import rpgserver.service.WorldService;

import java.security.Principal;

@Component
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketDisconnectListener.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private WorldService worldService;

    @Override
    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        Principal principal = event.getUser();
        worldService.removeCharacter(principal.getName());
        logger.info("Disconnect event [sessionId: " + sha.getSessionId() + " : close status" + event.getCloseStatus() + "]");
    }
}
