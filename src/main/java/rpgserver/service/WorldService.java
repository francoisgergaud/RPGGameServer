package rpgserver.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import rpgserver.service.models.*;
import rpgserver.service.models.Character;

import javax.annotation.PostConstruct;

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
     * the world-generator
     */
    @Autowired
    @Qualifier("randomWorldGenerator")
    private IWorldGenerator worldGenerator;

    /**
     * the world-map
     */
    private Bloc[][] worldMap;

    /**
     * list of world-elements (trees, houses etc...)
     */
    private List<WorldElement> worldElements;

    /**
     * map of all the players
     */
    private Map<String,Character> characters;


    /**
     * post-constructor: initialize the map and an empty character map
     */
    @PostConstruct
    private void initialize(){
        worldMap = worldGenerator.generateWorldMap(MAP_WIDTH,MAP_HEIGHT);
        worldElements = worldGenerator.generateWorldElements(MAP_WIDTH,MAP_HEIGHT);
        for(WorldElement worldElement : worldElements){
            worldMap[worldElement.getPosition().getx()][worldElement.getPosition().gety()].setSpriteId(worldElement.getSpriteId());
        }
        characters = new HashMap<>();
    }

    /**
     * generate an id for the character and add it to the map
     * @param characterName the character name
     * @param  characterAppearanceId the character's appearance'sidentifier
     * @return the character created
     */
    public Character addCharacter(String characterName, Integer characterAppearanceId){
        Character character = new Character();
        Date now = new Date();
        Long timestamp = now.getTime();
        String id = timestamp.toString();
        character.setName(characterName);
        character.setId(id);
        character.setCharacterId(characterAppearanceId);
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

    public List<WorldElement> getWorldElements() {
        return worldElements;
    }
}
