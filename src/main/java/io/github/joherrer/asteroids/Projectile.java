package io.github.joherrer.asteroids;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {
    
    public Projectile(int x, int y) {
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
    }
}
