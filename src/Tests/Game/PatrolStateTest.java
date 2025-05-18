package Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import Figures.Point;
import Game.Entities.Commons.StateMachine;
import Game.Entities.Enemies.EnemyStates.PatrolState;
import GameEngine.InputEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class PatrolStateTest {

    private PatrolState patrolState;
    private StateMachine stateMachine;
    private TestGameObject owner;
    private TestGameObject player;
    private List<Point> patrolPoints;

    @BeforeEach
    void setUp() {
        patrolPoints = new ArrayList<>();
        patrolPoints.add(new Point(0, 0));
        patrolPoints.add(new Point(5, 5));
        patrolPoints.add(new Point(10, 10));

        player = new TestGameObject("player");
        owner = new TestGameObject("enemy");
        stateMachine = new StateMachine();

        patrolState = new PatrolState(patrolPoints, player);
        stateMachine.addState("Patrol", patrolState);
        stateMachine.addState("Chase", new TestState("Chase")); // Add a dummy ChaseState for testing
        stateMachine.setOwner(owner);
        patrolState.onInit(stateMachine, owner);
    }

    @Test
    void CONSTRUCTOR_ValidPatrolPoints() {
        assertDoesNotThrow(() -> new PatrolState(patrolPoints, player));
    }

    @Test
    void CONSTRUCTOR_InvalidPatrolPoints() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new PatrolState(null, player));
        assertEquals("Patrol points cannot be null or empty.", exception.getMessage());
    }

    @Test
    void ON_ENTER_ValidState() {
        assertDoesNotThrow(() -> patrolState.onEnter());
    }

    @Test
    void ON_ENTER_UninitializedState() {
        PatrolState uninitializedState = new PatrolState(patrolPoints, player);
        Exception exception = assertThrows(IllegalStateException.class, uninitializedState::onEnter);
        assertEquals("StateMachine or owner is not initialized.", exception.getMessage());
    }

    @Test
    void ON_COLLISION_NullObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> patrolState.onCollision(null));
        assertEquals("Colliding object cannot be null.", exception.getMessage());
    }

    @Test
    void ON_UPDATE_PlayerWithinDetectionRadius() {
        // Move player within detection radius
        player.transform().move(new Point(1, 1), 0);

        InputEvent inputEvent = new InputEvent(1);
        patrolState.onUpdate(0.016, inputEvent);

        // Verify that the state machine transitions to "Chase"
        assertEquals("Chase", stateMachine.getCurrentStateName());
    }
}