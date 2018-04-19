package rpgserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
     * map of all the players by internal identfier
     */
    private Map<String,Character> charactersById;
    /**
     * map of all the players by name
     */
    private Map<String,Character> charactersByName;

    @Autowired
    public WorldService(@Qualifier("randomWorldGenerator") IWorldGenerator worldGenerator){
        this.worldGenerator = worldGenerator;
        //manage concurrent hashmap to ensure no duplication when player subscribe
        charactersById = new ConcurrentHashMap<>();
        charactersByName = new ConcurrentHashMap<>();
    }

    /**
     * post-constructor: initialize the map and an empty character map
     */
    @PostConstruct
    public void buildWorld(){
        worldMap = worldGenerator.generateWorldMap(MAP_WIDTH,MAP_HEIGHT);
        worldElements = worldGenerator.generateWorldElements(MAP_WIDTH,MAP_HEIGHT);
        for(WorldElement worldElement : worldElements){
            worldMap[worldElement.getPosition().getx()][worldElement.getPosition().gety()].setSpriteId(worldElement.getSpriteId());
        }
    }

    /**
     * generate an id for the character and add it to the map
     * @param id the character internal-identifier
     * @param characterName the character name (defined when user log in)
     * @param  characterAppearanceId the character's appearance'sidentifier
     * @return the character created
     */
    public Character addCharacter(String id, String characterName, Integer characterAppearanceId){
        Character character = new Character();
        character.setId(id);
        character.setName(characterName);
        character.setCharacterId(characterAppearanceId);
        character.setCurrentState(new CurrentState());
        character.getCurrentState().setPosition(new Position());
        character.getCurrentState().getPosition().setx(0);
        character.getCurrentState().getPosition().sety(0);
        character.getCurrentState().setDirection(0);
        character.getCurrentState().setFrame(0);
        character.getCurrentState().setVelocity(5);
        character.getCurrentState().setMoving(false);
        charactersById.put(id,character);
        charactersByName.put(characterName, character);
        return character;
    }

    /**
     * update the character position
     * @param id the character´ identifier
     * @param currentState the current-state to set
     */
    public void moveCharacter(String id, CurrentState currentState){
        LOGGER.trace("moving player id: {} to currentState: {}",id, currentState);
        Character character = charactersById.get(id);
        character.setCurrentState(currentState);
    }

    public Bloc[][] getWorldMap() {
        return worldMap;
    }

    public Map<String, Character> getCharactersById() {
        return charactersById;
    }

    public Map<String, Character> getCharactersByName() {
        return charactersByName;
    }

    public List<WorldElement> getWorldElements() {
        return worldElements;
    }

    /**
     * remove a character
     * TODO: should these 2 operations be atomics ?
     * @param id the identifier of the character
     * @return the removed character
     */
    public Character removeCharacter(String id) {
        // must be idempotent as message can send the same message multiple times
        Character result=null;
        if(charactersById.containsKey(id)) {
            result = charactersById.get(id);
            charactersByName.remove(result.getName());
            charactersById.remove(id);
        }
        return result;
    }
}
