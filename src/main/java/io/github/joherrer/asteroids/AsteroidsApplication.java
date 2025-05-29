package io.github.joherrer.asteroids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AsteroidsApplication extends Application {
    
    public static int WIDTH = 300;
    public static int HEIGHT = 200;
    
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        pane.setPrefSize(WIDTH, HEIGHT);
        
        Text text = new Text(10, 20, "Points: 0");
        AtomicInteger points = new AtomicInteger();
        
        Ship ship = new Ship(WIDTH /2 , HEIGHT / 2);
        List<Asteroid> asteroids = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            Asteroid asteroid = new Asteroid(random.nextInt(WIDTH / 3), random.nextInt(HEIGHT));
            asteroids.add(asteroid);
        }
        List<Projectile> projectiles = new ArrayList<>();
        
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
        pane.getChildren().add(text);
                
        Scene scene = new Scene(pane);
        
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        
        scene.setOnKeyPressed((event) -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });
        
        scene.setOnKeyReleased((event) -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        new AnimationTimer() {
            
            @Override
            public void handle(long now) {
                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                    ship.turnLeft();
                }
                
                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                    ship.turnRight();
                }
                
                if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
                    ship.accelerate();
                }
                
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3) {
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);
                    
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
                    
                    pane.getChildren().add(projectile.getCharacter());
                }
                
                ship.move();
                asteroids.forEach(asteroid -> asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                
                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        stop();
                    }
                });
                
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if (projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    
                    if (!projectile.isAlive()) {
                        text.setText("Points: " + points.addAndGet(1000));
                    }
                });
                
                removeDeadCharacters(projectiles, pane);
                removeDeadCharacters(asteroids, pane);
                
                if (Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
                    if (!asteroid.collide(ship)) {
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            }                
        }.start();
        
        stage.setTitle("Asteroids!");
        stage.setScene(scene);
        stage.show();
    }
    
    private void removeDeadCharacters(List<? extends Character> list, Pane pane) {
        List<Character> deadCharacters = list.stream()
                                            .filter(character -> !character.isAlive())
                                            .collect(Collectors.toList());
        
        deadCharacters.forEach(character -> pane.getChildren().remove(character.getCharacter()));
        list.removeAll(deadCharacters);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
