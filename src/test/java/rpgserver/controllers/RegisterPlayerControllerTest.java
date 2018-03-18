package rpgserver.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rpgserver.controller.RegisterPlayerController;
import rpgserver.controller.model.MovePlayerInput;
import rpgserver.controller.model.NewPlayerRegistered;
import rpgserver.controller.model.RegisterPlayerInput;
import rpgserver.service.WorldService;
import rpgserver.service.models.Character;
import rpgserver.service.models.CurrentState;
import rpgserver.service.models.Position;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RegisterPlayerControllerTest {

    @Mock
    private SimpMessagingTemplate messageTemplate;

    @Mock
    private WorldService worldService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterPlayer() {
        Integer characterId = 0;
        String id = "fakeId";
        String name = "fakeName";
        Integer direction = 10;
        Integer frame = 9;
        boolean moving = true;
        Integer velocity = 8;
        Position position = new Position();
        //mock the word-service addCharacter method
        Character characterCreated = new Character();
        characterCreated.setCharacterId(characterId);
        characterCreated.setId(id);
        characterCreated.setName(name);
        characterCreated.setCurrentState(new CurrentState());
        characterCreated.getCurrentState().setDirection(direction);
        characterCreated.getCurrentState().setFrame(frame);
        characterCreated.getCurrentState().setMoving(moving);
        characterCreated.getCurrentState().setVelocity(velocity);
        characterCreated.getCurrentState().setPosition(position);
        when(worldService.addCharacter(any(String.class), eq(id), eq(characterId))).thenReturn(characterCreated);
        //test the controller
        RegisterPlayerInput input = new RegisterPlayerInput();
        input.setCharacterId(characterId);
        input.setId(id);
        RegisterPlayerController registerPlayerController = new RegisterPlayerController(worldService, messageTemplate);
        registerPlayerController.registerPlayer(input);
        //test the output
        ArgumentCaptor<NewPlayerRegistered> newPlayerRegisteredArgument = ArgumentCaptor.forClass(NewPlayerRegistered.class);
        verify(messageTemplate, new Times(1)).convertAndSend(eq("/topics/newPlayer"), newPlayerRegisteredArgument.capture());
        assertEquals(id, newPlayerRegisteredArgument.getValue().getName());
        assertEquals(characterId, newPlayerRegisteredArgument.getValue().getCharacterId());
        assertEquals(new Integer(direction), newPlayerRegisteredArgument.getValue().getCurrentState().getDirection());
        assertEquals(new Integer(frame), newPlayerRegisteredArgument.getValue().getCurrentState().getFrame());
        assertEquals(moving, newPlayerRegisteredArgument.getValue().getCurrentState().getMoving());
        assertEquals(position, newPlayerRegisteredArgument.getValue().getCurrentState().getPosition());
    }

    @Test
    public void testMovePlayer() {
        String id = "0";
        CurrentState currentState = new CurrentState();
        //test the controller
        MovePlayerInput input = new MovePlayerInput();
        input.setId(id);
        input.setCurrentState(currentState);
        RegisterPlayerController registerPlayerController = new RegisterPlayerController(worldService, messageTemplate);
        registerPlayerController.movePlayer(null, input);
        //test the service call
        ArgumentCaptor<CurrentState> currentStateArgument = ArgumentCaptor.forClass(CurrentState.class);
        verify(worldService, new Times(1)).moveCharacter(eq(id), currentStateArgument.capture());
        assertEquals(currentState, currentStateArgument.getValue());
        // test the async message sent
        ArgumentCaptor<MovePlayerInput> movePlayerServiceArgument = ArgumentCaptor.forClass(MovePlayerInput.class);
        verify(messageTemplate, new Times(1)).convertAndSend(
                eq("/topics/movePlayer"),
                movePlayerServiceArgument.capture());
        assertEquals(input, movePlayerServiceArgument.getValue());
    }
}
