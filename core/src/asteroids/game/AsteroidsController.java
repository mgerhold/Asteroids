package asteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;

public class AsteroidsController extends Actor {

    public final static String name = "AsteroidsController";
    private final static float initialSpeed = 150;

    Group asteroidsGroup = null;
    TextureAtlas textureAtlas;
    CountDisplay countDisplay;

    public AsteroidsController(TextureAtlas textureAtlas, int count, Group asteroidsGroup, CountDisplay countDisplay) {
        this.textureAtlas = textureAtlas;
        this.asteroidsGroup = asteroidsGroup;
        this.countDisplay = countDisplay;
        setName(name);
        addAction(Actions.sequence(
            Actions.delay(0.5f),
            Actions.repeat(count,
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        spawnAsteroid();
                        return true;
                    }
                }
            ),
            new Action() {
                @Override
                public boolean act(float delta) {
                    Asteroids.gameState = asteroids.game.Asteroids.GameState.INGAME;
                    return true;
                }
            }
        ));
    }

    public void handlePlayerMovement(Vector2 direction, float velocity) {
        SnapshotArray<Actor> children = asteroidsGroup.getChildren();
        for (Actor actor : children) {
            actor.moveBy(-direction.x * velocity, -direction.y * velocity);
        }
    }

    public void spawnAsteroid() {        
        countDisplay.increase();
        Asteroid asteroid;
        if (Math.random() < 0.5f)
            asteroid = new Asteroid(
                -Asteroid.margin,
                (float)Math.random() * Gdx.graphics.getHeight(),
                initialSpeed * ((float)Math.random() - 0.5f),
                initialSpeed * ((float)Math.random() - 0.5f),
                textureAtlas,
                countDisplay
            );
        else
            asteroid = new Asteroid(
                (float)Math.random() * Gdx.graphics.getWidth(),
                -Asteroid.margin,
                initialSpeed * ((float)Math.random() - 0.5f),
                initialSpeed * ((float)Math.random() - 0.5f),
                textureAtlas,
                countDisplay
        );
        asteroidsGroup.addActor(asteroid);
    }
    
}
