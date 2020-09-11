package asteroids.game;

import java.util.ArrayList;
import java.util.function.Predicate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BulletController extends Actor {

    public final static String name = "BulletController";
    private final static int maxCount = 10;

    ArrayList<Bullet> bullets;    
    TextureRegion textureRegion;
    Group asteroidsGroup;

    public BulletController(TextureRegion textureRegion, Group asteroidsGroup) {
        bullets = new ArrayList<Bullet>();
        this.textureRegion = textureRegion;
        this.asteroidsGroup = asteroidsGroup;
        setName(name);
    }

    @Override
    public void act(float delta) {
        for (Bullet bullet : bullets) {
            bullet.update(delta);
        }
        bullets.removeIf(new Predicate<Bullet>(){
            public boolean test(Bullet bullet) {
                return !bullet.isActive();
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Bullet bullet : bullets) {
            batch.draw(textureRegion, bullet.x, bullet.y);
        }
    }
    
    public boolean spawn(float x, float y, float velocity, Vector2 direction) {
        if (bullets.size() < maxCount) {
            Bullet bullet = new Bullet(x, y, velocity, direction, asteroidsGroup);
            bullets.add(bullet);
            return true;
        }
        return false;
    }

    public void handlePlayerMovement(Vector2 direction, float velocity) {        
        for (Bullet bullet : bullets) {
            bullet.x -= direction.x * velocity;
            bullet.y -= direction.y * velocity;            
        }
    }

}
