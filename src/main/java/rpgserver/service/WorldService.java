package rpgserver.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rpgserver.service.models.Bloc;
import rpgserver.service.models.Character;
import rpgserver.service.models.CurrentState;
import rpgserver.service.models.Position;

/**
 * contains information about the world and manage concurrent access to it
 */
@Service
public class WorldService {

    /**
     * the logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldService.class);

    /**
     * the world-map width
     */
    public static final Integer MAP_WIDTH = 50;

    /**
     * the world-map height
     */
    public static final Integer MAP_HEIGHT = 50;

    /**
     * the world-map
     */
    private Bloc[][] worldMap;

    /**
     * map of all the players
     */
    private Map<String,Character> characters;

    /**
     * constructor: initialize the map and an empty character map
     */
    public WorldService(){
        worldMap = new Bloc[MAP_WIDTH][MAP_HEIGHT];
        for(int i=0; i < MAP_WIDTH; i++){
            for(int j=0; j < MAP_HEIGHT; j++){
                Bloc bloc = new Bloc();
                bloc.setSpriteId(ThreadLocalRandom.current().nextInt(0,  10) < 7 ? 0 : 1 );
                worldMap[i][j] = bloc;
            }
        }
        characters = new HashMap<>();
    }

    /**
     * generate an id for the character and add it to the map
     * @param characterName the character name
     * @return the character created
     */
    public Character addCharacter(String characterName){
        Character character = new Character();
        Date now = new Date();
        Long timestamp = now.getTime();
        String id = timestamp.toString();
        character.setName(characterName);
        character.setId(id);
        character.setCurrentState(new CurrentState());
        character.getCurrentState().setPosition(new Position());
        character.getCurrentState().getPosition().setx(0);
        character.getCurrentState().getPosition().sety(0);
        character.getCurrentState().setDirection(0);
        character.getCurrentState().setFrame(0);
        character.getCurrentState().setVelocity(5);
        character.getCurrentState().setMoving(false);
        characters.put(id,character);
        return character;
    }

    /**
     * update the character position
     * @param id the character´ identifier
     * @param currentState the current-state to set
     */
    public void moveCharacter(String id, CurrentState currentState){
        LOGGER.trace("moving player id: {} to currentState: {}",id, currentState);
        Character character = characters.get(id);
        character.setCurrentState(currentState);
    }

    public Bloc[][] getWorldMap() {
        return worldMap;
    }

    public Map<String, Character> getCharacters() {
        return characters;
    }
}
