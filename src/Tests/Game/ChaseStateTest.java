package Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import Figures.Point;
import Game.Entities.Commons.StateMachine;
import Game.Entities.Enemies.EnemyStates.ChaseState;
import GameEngine.InputEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChaseStateTest {

    private ChaseState chaseState;
    private StateMachine stateMachine;
    private TestGameObject owner;
    private TestGameObject player;

    @BeforeEach
    void setUp() {
        player = new TestGameObject("player");
        owner = new TestGameObject("enemy");

        player.transform().move(new Point(10, 10), 0);
        owner.transform().move(new Point(0, 0), 0);

        stateMachine = new StateMachine();
        chaseState = new ChaseState(player);

        stateMachine.setOwner(owner);
        chaseState.onInit(stateMachine, owner);
    }

    @Test
    void CONSTRUCTOR_ValidPlayer() {
        assertDoesNotThrow(() -> new ChaseState(player));
    }

    @Test
    void CONSTRUCTOR_NullPlayer() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new ChaseState(null));
        assertEquals("Player cannot be null.", exception.getMessage());
    }

    @Test
    void ON_UPDATE_PlayerWithinAttackRadius() {
        // Move player within attack radius
        player.transform().move(new Point(0.5, 0.5), 0);

        InputEvent inputEvent = new InputEvent(1);
        chaseState.onUpdate(0.016, inputEvent);

        // Verify that the state machine transitions to "Attack"
        assertThrows(IllegalArgumentException.class, () -> stateMachine.setState("Attack"));
    }

    @Test
    void ON_ENTER_ValidState() {
        assertDoesNotThrow(() -> chaseState.onEnter());
    }

    @Test
    void ON_ENTER_UninitializedState() {
        ChaseState uninitializedState = new ChaseState(player);
        Exception exception = assertThrows(IllegalStateException.class, uninitializedState::onEnter);
        assertEquals("StateMachine or owner is not initialized.", exception.getMessage());
    }

    @Test
    void ON_COLLISION_NullObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> chaseState.onCollision(null));
        assertEquals("Colliding object cannot be null.", exception.getMessage());
    }
}