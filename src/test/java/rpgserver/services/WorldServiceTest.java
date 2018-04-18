package rpgserver.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import rpgserver.service.IWorldGenerator;
import rpgserver.service.WorldService;
import rpgserver.service.models.Bloc;
import rpgserver.service.models.CurrentState;
import rpgserver.service.models.Position;
import rpgserver.service.models.WorldElement;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorldServiceTest {

    @Mock
    private IWorldGenerator worldGenerator;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitialize() {
        Integer spriteId = 2;
        Integer spriteXPosition = 1;
        Integer spriteYPosition = 1;
        Integer mapWidth = 2;
        Integer mapHeight = 2;
        Bloc[][] worldMap = new Bloc[mapWidth][mapHeight];
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                worldMap[i][j] = new Bloc();
            }
        }
        List<WorldElement> worldElements = new ArrayList<>();
        WorldElement worldElement = new WorldElement();
        worldElement.setSpriteId(spriteId);
        worldElement.setPosition(new Position());
        worldElement.getPosition().setx(spriteXPosition);
        worldElement.getPosition().sety(spriteYPosition);
        worldElements.add(worldElement);
        when(worldGenerator.generateWorldMap(any(Integer.class), any(Integer.class))).thenReturn(worldMap);
        when(worldGenerator.generateWorldElements(any(Integer.class), any(Integer.class))).thenReturn(worldElements);
        WorldService worldService = new WorldService(worldGenerator);
        worldService.buildWorld();
        assertEquals(worldService.getWorldMap()[spriteXPosition][spriteXPosition].getSpriteId(), spriteId);
    }

    @Test
    public void testAddCharacter() {
        String characterName = "fakeName";
        Integer characterAppearanceId = 1;
        String uniqueId = "fakeId";
        WorldService worldService = new WorldService(worldGenerator);
        worldService.addCharacter(uniqueId, characterName, characterAppearanceId);
        assertEquals(worldService.getCharactersById().get(uniqueId).getName(), characterName);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCharacterId(), characterAppearanceId);
        assertEquals(worldService.getCharactersById().get(uniqueId).getName(), characterName);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getPosition().getx(),0);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getPosition().gety(),0);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getDirection(), new Integer(0));
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getMoving(), false);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getFrame(), new Integer(0));
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState().getVelocity(), new Integer(5));
    }

    @Test
    public void testMoveCharacter() {
        String characterName = "fakeName";
        Integer characterAppearanceId = 1;
        String uniqueId = "fakeId";
        WorldService worldService = new WorldService(worldGenerator);
        worldService.addCharacter(uniqueId, characterName, characterAppearanceId);
        CurrentState currentState = new CurrentState();
        worldService.moveCharacter(uniqueId, currentState);
        assertEquals(worldService.getCharactersById().get(uniqueId).getCurrentState(), currentState);
    }
}
