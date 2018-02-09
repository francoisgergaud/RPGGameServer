package rpgserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rpgserver.controller.model.MovePlayerInput;
import rpgserver.controller.model.NewPlayerRegistered;
import rpgserver.controller.model.RegisterPlayerOutput;
import rpgserver.controller.model.RegisterPlayerInput;
import rpgserver.service.WorldService;
import rpgserver.service.models.Character;

import java.util.Date;
import java.util.Map;

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
     * @param registerPlayer information about the player
     * @return the information about the world
     */
    @RequestMapping(path = "/registerPlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterPlayerOutput> registerPlayer(@RequestBody RegisterPlayerInput registerPlayer) {
        RegisterPlayerOutput result = new RegisterPlayerOutput();
        //assign an id for the user, a character appearance, a position
        Date now = new Date();
        Long timestamp = now.getTime();
        String uniqueId = timestamp.toString();
        Character character = worldService.addCharacter(uniqueId, registerPlayer.getId(), registerPlayer.getCharacterId());
        result.setPlayerId(character.getId());
        result.setMap(worldService.getWorldMap());
        result.setWorldElements(worldService.getWorldElements());
        result.setAnimatedElements(worldService.getCharacters());
        NewPlayerRegistered newPlayerRegistered = new NewPlayerRegistered();
        newPlayerRegistered.setId(character.getId());
        newPlayerRegistered.setCharacterId(character.getCharacterId());
        newPlayerRegistered.setName(registerPlayer.getId());
        newPlayerRegistered.setCurrentState(character.getCurrentState());
        //notify all the other players
        this.template.convertAndSend("/topics/newPlayer", newPlayerRegistered);
        return new ResponseEntity<RegisterPlayerOutput>(result, HttpStatus.OK);
    }

    /**
     * invoked when a player moves
     *
     * @param movePlayerInput information about the player´ movement
     */
    @RequestMapping(path = "/movePlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> movePlayer(@RequestBody MovePlayerInput movePlayerInput) {
        //update the map
        worldService.moveCharacter(movePlayerInput.getId(), movePlayerInput.getCurrentState());
        //notify all the other players
        this.template.convertAndSend("/topics/movePlayer", movePlayerInput);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public void ping() {
        LOGGER.info("ping");
    }
}