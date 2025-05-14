package Tests.Game;

import static org.junit.jupiter.api.Assertions.*;

import GameEngine.InputEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Game.Entities.StateMachine;

class StateMachineTest {

    private StateMachine stateMachine;
    private TestState state1;
    private TestState state2;
    private TestGameObject owner;

    @BeforeEach
    void setUp() {
        // Initialize the StateMachine and its components
        stateMachine = new StateMachine();
        owner = new TestGameObject("Owner");
        state1 = new TestState("State1");
        state2 = new TestState("State2");

        // Add states to the StateMachine
        stateMachine.addState("State1", state1);
        stateMachine.addState("State2", state2);

        // Set the owner of the StateMachine
        stateMachine.setOwner(owner);
    }

    @Test
    void ADD_STATE_ValidState() {
        TestState newState = new TestState("NewState");
        stateMachine.addState("NewState", newState);
        assertDoesNotThrow(() -> stateMachine.setState("NewState"));
    }

    @Test
    void ADD_STATE_DuplicateState() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> stateMachine.addState("State1", state1));
        assertEquals("State State1 already exists.", exception.getMessage());
    }

    @Test
    void REMOVE_STATE_ValidState() {
        stateMachine.removeState("State1");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> stateMachine.setState("State1"));
        assertEquals("State State1 not found.", exception.getMessage());
    }

    @Test
    void REMOVE_STATE_InvalidState() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> stateMachine.removeState("InvalidState"));
        assertEquals("State InvalidState not found.", exception.getMessage());
    }

    @Test
    void SET_STATE_ValidState() {
        stateMachine.setState("State1");
        assertTrue(state1.isEntered());
    }

    @Test
    void SET_STATE_InvalidState() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> stateMachine.setState("InvalidState"));
        assertEquals("State InvalidState not found.", exception.getMessage());
    }

    @Test
    void SET_STATE_TransitionBetweenStates() {
        stateMachine.setState("State1");
        assertTrue(state1.isEntered());

        stateMachine.setState("State2");
        assertTrue(state1.isExited());
        assertTrue(state2.isEntered());
    }

    @Test
    void ON_UPDATE_ValidState() {
        stateMachine.setState("State1");
        stateMachine.onUpdate(0.016, new InputEvent(1));
        assertTrue(state1.isUpdated());
    }

    @Test
    void ON_UPDATE_NoCurrentState() {
        Exception exception = assertThrows(IllegalStateException.class, () -> stateMachine.onUpdate(0.016, new InputEvent(1)));
        assertEquals("No current state is set.", exception.getMessage());
    }

    @Test
    void RESET_TO_DEFAULT_ValidDefaultState() {
        stateMachine.setDefaultState("State1");
        stateMachine.resetToDefault();
        assertTrue(state1.isEntered());
    }

    @Test
    void RESET_TO_DEFAULT_NoDefaultState() {
        Exception exception = assertThrows(IllegalStateException.class, () -> stateMachine.resetToDefault());
        assertEquals("Default state is not set.", exception.getMessage());
    }

    @Test
    void SET_DEFAULT_STATE_ValidState() {
        stateMachine.setDefaultState("State1");
        assertDoesNotThrow(() -> stateMachine.resetToDefault());
    }

    @Test
    void SET_DEFAULT_STATE_InvalidState() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> stateMachine.setDefaultState("InvalidState"));
        assertEquals("Default state InvalidState not found.", exception.getMessage());
    }

    @Test
    void ON_COLLISION_ValidState() {
        stateMachine.setState("State1");
        TestGameObject other = new TestGameObject("Other");
        stateMachine.onCollision(other);
        assertTrue(state1.isCollided());
    }

    @Test
    void ON_COLLISION_NoCurrentState() {
        TestGameObject other = new TestGameObject("Other");
        Exception exception = assertThrows(IllegalStateException.class, () -> stateMachine.onCollision(other));
        assertEquals("No current state is set.", exception.getMessage());
    }
}