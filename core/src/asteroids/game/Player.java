package asteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image implements CircleCollider {

    public final static String name = "Player";   
    private final static float rotationSpeed = 120;
    private final static float accelerationSpeed = 500;    
    private final static float dampingFactor = 0.99f;

    public float rotation = 0;
    private float velocity = 0;
    private LivesCounter livesCounter;

    public Player(TextureRegion textureRegion, final AsteroidsController asteroidsController, final BulletController bulletController,
        LivesCounter livesCounter)
    {
        super(textureRegion);
        this.livesCounter = livesCounter;
        setOrigin(getWidth() / 2, getHeight() / 2);
        setName(name);
        addAction(Actions.forever(
            new Action() {
                @Override
                public boolean act(float delta) {
                    if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
                        BulletController bulletController = getStage().getRoot().findActor(BulletController.name);                        
                        Vector2 position = localToStageCoordinates(new Vector2(26, 8));
                        if (bulletController.spawn(position.x, position.y, velocity + 70, getDirection()))
                            SoundHandler.playSound("shot");
                    }
                    if (Gdx.input.isKeyPressed(Keys.LEFT))
                        rotation += rotationSpeed * delta;                        
                    if (Gdx.input.isKeyPressed(Keys.RIGHT))
                        rotation -= rotationSpeed * delta;
                    if (Gdx.input.isKeyPressed(Keys.UP))
                        velocity += accelerationSpeed * delta;
                    if (Gdx.input.isKeyPressed(Keys.DOWN))
                        velocity -= accelerationSpeed * delta;
                    velocity *= dampingFactor;
                    if (velocity < 1)
                        velocity = 0;
                    setRotation(rotation);                    
                    asteroidsController.handlePlayerMovement(getDirection(), getVelocity() * delta);                    
                    bulletController.handlePlayerMovement(getDirection(), getVelocity() * delta);
                    return true;
                }
            }
        ));
    }

    public Vector2 getDirection() {
        return new Vector2(1, 0).rotate(rotation);
    }
    
    public float getVelocity() {
        return velocity;
    }
 
    public float getColliderRadius() {
        return 11;
    }

    public Vector2 getColliderCenter() {
        return localToStageCoordinates(new Vector2(13, 8));
    }

    public void decreaseLives() {
        livesCounter.decrease();
        if (livesCounter.getLives() < 1) {
            MessageDisplay messageDisplay = getStage().getRoot().findActor(MessageDisplay.name);
            messageDisplay.showMessage("You lost!");
            SoundHandler.playSound("lost");
            Asteroids.gameState = asteroids.game.Asteroids.GameState.LOST;
        }
    }
    
}
