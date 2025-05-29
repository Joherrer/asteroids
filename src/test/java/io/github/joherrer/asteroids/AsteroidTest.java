package io.github.joherrer.asteroids;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AsteroidTest {

    @Test
    public void testAsteroidInitialization() {
        Asteroid asteroid = new Asteroid(100, 50);
        
        assertNotNull(asteroid.getCharacter(), "Asteroid character should not be null");
        assertEquals(100, asteroid.getCharacter().getTranslateX(), "Initial X should match");
        assertEquals(50, asteroid.getCharacter().getTranslateY(), "Initial Y should match");
        assertTrue(asteroid.isAlive(), "Asteroid should be alive on creation");
    }

    @Test
    public void testAsteroidMovementChangesRotation() {
        Asteroid asteroid = new Asteroid(0, 0);
        double initialRotation = asteroid.getCharacter().getRotate();
        
        asteroid.move();
        double newRotation = asteroid.getCharacter().getRotate();
        
        assertNotEquals(initialRotation, newRotation, "Rotation should change after move");
    }
}
