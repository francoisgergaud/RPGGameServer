package rpgserver.service;

import rpgserver.service.models.Bloc;
import rpgserver.service.models.WorldElement;

import java.util.List;

public interface IWorldGenerator {

    /**
     * generate a world
     * @param mapWidth the world's width
     * @param mapHeight the world's height
     * @return the world generator
     */
    Bloc[][] generateWorldMap(int mapWidth, int mapHeight);

    /**
     * generate the elements on the world
     * @return the list of elements on the world map
     */
    List<WorldElement> generateWorldElements(int mapWidth, int mapHeight);
}
