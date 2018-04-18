package rpgserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rpgserver.controller.model.*;
import rpgserver.service.WorldService;
import rpgserver.service.models.Character;

@Controller
public class RegisterPlayerController {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPlayerController.class);

    private SimpMessagingTemplate template;

    /**
     * the world's content
     */
    private WorldService worldService;

    /**
     * asynchronous message sender (use to send to web-socket)
     * @param worldService the world services
     * @param template the Stomp message-template
     */
    @Autowired
    public RegisterPlayerController(WorldService worldService,SimpMessagingTemplate template) {
        this.template = template;
        this.worldService = worldService;
    }

    /**
     * invoked when a new player joins the game
     *
     * @param registerPlayerInput information about the player
     * @return the information about the world
     */
    @MessageMapping("/registerPlayer")
    public void registerPlayer(@Payload RegisterPlayerInput registerPlayerInput, SimpMessageHeaderAccessor headerAccessor) {
        RegisterPlayerOutput result = new RegisterPlayerOutput();
        Character character = worldService.addCharacter(headerAccessor.getUser().getName(), registerPlayerInput.getId(), registerPlayerInput.getCharacterId());
        result.setPlayerId(character.getId());
        result.setMap(worldService.getWorldMap());
        result.setWorldElements(worldService.getWorldElements());
        result.setAnimatedElements(worldService.getCharactersById());
        NewPlayerRegistered newPlayerRegistered = new NewPlayerRegistered();
        newPlayerRegistered.setId(character.getId());
        newPlayerRegistered.setCharacterId(character.getCharacterId());
        newPlayerRegistered.setName(registerPlayerInput.getId());
        newPlayerRegistered.setCurrentState(character.getCurrentState());
        //notify all the other players
        this.template.convertAndSendToUser(headerAccessor.getUser().getName(), "/queue/registerPlayer", result, headerAccessor.getMessageHeaders());
        this.template.convertAndSend("/topic/newPlayer", newPlayerRegistered);
    }

    /**
     * invoked when a player moves
     *
     * @param movePlayerInput information about the player´ movement
     */
    @MessageMapping("/movePlayer")
    public void movePlayer(@Payload MovePlayerInput movePlayerInput, SimpMessageHeaderAccessor headerAccessor) {
        //update the map
        worldService.moveCharacter(headerAccessor.getUser().getName(), movePlayerInput.getCurrentState());
        //notify all the other players
        this.template.convertAndSend("/topic/movePlayer", movePlayerInput);
        this.template.convertAndSendToUser(headerAccessor.getUser().getName(), "/queue/movePlayer", "message broadcasted to other!", headerAccessor.getMessageHeaders());
    }

    /**
     * invoked when a player moves
     *
     * @param chatInput information about the chat's message
     */
    @MessageMapping("/chat")
    public void chat(@Payload ChatInput chatInput, SimpMessageHeaderAccessor headerAccessor) {
        //get all the destinations:
        for(String destination : chatInput.getTo()) {
            String destinationId = worldService.getCharactersByName().get(destination).getId();
            this.template.convertAndSendToUser(destinationId, "/queue/chat", chatInput.getFrom()
                    + ": "+chatInput.getMessage());
        }
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public void ping() {
        LOGGER.info("ping");
    }
}