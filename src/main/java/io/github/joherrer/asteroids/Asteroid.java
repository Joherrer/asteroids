package io.github.joherrer.asteroids;

import java.util.Random;

public class Asteroid extends Character {
    
    private double rotationalMovement;
    
    public Asteroid(int x, int y) {
        super(new PolygonFactory().createPolygon(), x, y);
        
        Random rdm = new Random();
        
        super.getCharacter().setRotate(rdm.nextInt(360));
        
        int accelerationAmount = 1 + rdm.nextInt(10);
        for (int i = 0; i < accelerationAmount; i++) {
            super.accelerate();
        }
        
        this.rotationalMovement = 0.5 - rdm.nextDouble();    
    }
    
    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotationalMovement);
    }
}
