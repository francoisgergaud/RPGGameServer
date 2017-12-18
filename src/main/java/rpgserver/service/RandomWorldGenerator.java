package rpgserver.service;

import org.springframework.stereotype.Component;
import rpgserver.service.models.Bloc;
import rpgserver.service.models.Position;
import rpgserver.service.models.WorldElement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * world generator based on random
 */
@Component("randomWorldGenerator")
public class RandomWorldGenerator implements rpgserver.service.IWorldGenerator {

    /**
     * the generation of the world. BAsed on simple random
     * @param mapWidth the world's width
     * @param mapHeight the world's height
     * @return the world created
     */
    @Override
    public Bloc[][] generateWorldMap(int mapWidth, int mapHeight){
        Bloc[][] worldMap = new Bloc[mapWidth][mapHeight];
        for(int i=0; i < mapWidth; i++){
            for(int j=0; j < mapHeight; j++){
                Bloc bloc = new Bloc();
                int randomNumber = ThreadLocalRandom.current().nextInt(0,  100);
                if(randomNumber < 90){
                    bloc.setTileId(0);
                }else{
                    bloc.setTileId(1);
                }
                bloc.setSpriteId(null);
                worldMap[i][j] = bloc;
            }
        }
        return worldMap;
    }

    /**
     * generate sprite for world in a random way
     * @param mapWidth the map width
     * @param mapHeight the map height
     * @return the list of created world-elements
     */
    public List<WorldElement> generateWorldElements(int mapWidth, int mapHeight) {
        List<WorldElement> result = new ArrayList<>();
        int randomNumber = ThreadLocalRandom.current().nextInt(3,  20);
        for(int i = 0; i < randomNumber; i++){
            WorldElement worldElement = new WorldElement();
            worldElement.setPosition(new Position());
            worldElement.getPosition().setx(ThreadLocalRandom.current().nextInt(0,  mapWidth-1));
            worldElement.getPosition().sety(ThreadLocalRandom.current().nextInt(0,  mapHeight-1));
            worldElement.setSpriteId(0);
            result.add(worldElement);
        }
        //only one house:
        WorldElement worldElement = new WorldElement();
        worldElement.setPosition(new Position());
        worldElement.getPosition().setx(ThreadLocalRandom.current().nextInt(0,  mapWidth-1));
        worldElement.getPosition().sety(ThreadLocalRandom.current().nextInt(0,  mapHeight-1));
        worldElement.setSpriteId(1);
        result.add(worldElement);
        return result;
    }
}
