package Tests.Game;

import org.junit.jupiter.api.Test;

import Game.Entities.Commons.Health;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {

    @Test
    void CONSTRUCTOR_ValidMaxHealth() {
        Health health = new Health(100);
        assertEquals(100, health.getMaxHealth());
        assertEquals(100, health.getCurrentHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void CONSTRUCTOR_InvalidMaxHealth() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Health(0));
        assertEquals("Max health must be greater than 0.", exception.getMessage());
    }

    @Test
    void TAKE_DAMAGE_ValidDamage() {
        Health health = new Health(100);
        health.takeDamage(30);
        assertEquals(70, health.getCurrentHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void TAKE_DAMAGE_ExceedingHealth() {
        Health health = new Health(100);
        health.takeDamage(150);
        assertEquals(0, health.getCurrentHealth());
        assertFalse(health.isAlive());
    }

    @Test
    void TAKE_DAMAGE_NegativeDamage() {
        Health health = new Health(100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> health.takeDamage(-10));
        assertEquals("Damage must be non-negative.", exception.getMessage());
    }

    @Test
    void HEAL_ValidHeal() {
        Health health = new Health(100);
        health.takeDamage(50);
        health.heal(30);
        assertEquals(80, health.getCurrentHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void HEAL_ExceedingMaxHealth() {
        Health health = new Health(100);
        health.takeDamage(20);
        health.heal(50);
        assertEquals(100, health.getCurrentHealth());
        assertTrue(health.isAlive());
    }

    @Test
    void HEAL_NegativeHeal() {
        Health health = new Health(100);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> health.heal(-10));
        assertEquals("Heal amount must be non-negative.", exception.getMessage());
    }

    @Test
    void TAKE_DAMAGE_WhenDead() {
        Health health = new Health(100);
        health.takeDamage(150); // Health reaches 0 and is dead
        health.takeDamage(10); // Should have no effect
        assertEquals(0, health.getCurrentHealth());
        assertFalse(health.isAlive());
    }

    @Test
    void HEAL_WhenDead() {
        Health health = new Health(100);
        health.takeDamage(150); // Health reaches 0 and is dead
        health.heal(50); // Should have no effect
        assertEquals(0, health.getCurrentHealth());
        assertFalse(health.isAlive());
    }
}