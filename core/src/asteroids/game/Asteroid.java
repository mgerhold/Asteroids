package asteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Asteroid extends Image implements CircleCollider {

    public final static float margin = 30;

    TextureAtlas textureAtlas;
    int level;
    CountDisplay countDisplay;
    Vector2 velocity;

    public Asteroid(float x, float y, float velocityX, float velocityY, TextureAtlas textureAtlas, CountDisplay countDisplay) {
        this(x, y, velocityX, velocityY, textureAtlas, countDisplay, 0);
    }

    public Asteroid(float x, float y, final float velocityX, final float velocityY, TextureAtlas textureAtlas, final CountDisplay countDisplay, int level) {
        super(textureAtlas.findRegion("asteroid" + Integer.toString(level)));
        this.level = level;
        setOrigin(getWidth() / 2, getHeight() / 2);
        this.textureAtlas = textureAtlas;
        this.countDisplay = countDisplay;
        this.velocity = new Vector2(velocityX, velocityY);
        setPosition(x, y);
        final float angularVelocity = (float)Math.random() * 140;

        addAction(Actions.forever(
            new Action() {
                @Override
                public boolean act(float delta) {
                    setPosition(getX() + velocityX * delta, getY() + velocityY * delta);
                    setRotation(getRotation() + angularVelocity * delta);
                    if (getX() >= Gdx.graphics.getWidth() + margin)
                        setX(-margin);
                    if (getX() < -margin)
                        setX(Gdx.graphics.getWidth() + margin - 1);
                    if (getY() >= Gdx.graphics.getHeight() + margin)
                        setY(-margin);
                    if (getY() < -margin)
                        setY(Gdx.graphics.getHeight() + margin - 1);
                    Player player = getStage().getRoot().findActor(Player.name); // bad for performance
                    if (CollisionChecker.checkCollision((CircleCollider)player, (CircleCollider)Asteroid.this)) {
                        // collision with player happened
                        SoundHandler.playSound("ship_damage");                        
                        player.addAction(Actions.repeat(7, Actions.sequence(
                            Actions.hide(),
                            Actions.delay(0.05f),
                            Actions.show(),
                            Actions.delay(0.05f)
                        )));
                        player.decreaseLives();
                        countDisplay.decrease();
                        remove();
                    }
                    return true;
                }   
            }
        ));
    }

    public float getColliderRadius() {
        switch (level) {
            case 0:
                return 25;                
            case 1:
                return 14;
            case 2:
                return 7;
            default:
                return 25;
        }
    }

    public Vector2 getColliderCenter() {
        return localToStageCoordinates(new Vector2(getColliderRadius(), getColliderRadius()));
    }

    public void explode() {
        countDisplay.decrease();
        SoundHandler.playSound("explosion", 0.6f);
        if (level < 2) {
            // spawn 2 new asteroids
            for (int i = 0; i < 2; i++) {
                Vector2 newVelocity = velocity.rotate((float)Math.random() * 360);
                Asteroid asteroid = new Asteroid(
                    getX(),
                    getY(),
                    newVelocity.x * 1.2f,
                    newVelocity.y * 1.2f,
                    textureAtlas, countDisplay,
                    level + 1
                );
                getParent().addActor(asteroid);
                countDisplay.increase();
            }
        }
        remove();
    }
    
}
