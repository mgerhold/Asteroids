package asteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class Bullet implements CircleCollider {

    public float x;
    public float y;
    public float velocity;
    public Vector2 direction;
    boolean active = true;

    Group asteroidsGroup;

    public Bullet(float x, float y, float velocity, Vector2 direction, Group asteroidsGroup) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.direction = direction;
        this.asteroidsGroup = asteroidsGroup;
    }

    public void update(float delta) {
        x += velocity * direction.x * delta;
        y += velocity * direction.y * delta;
        // check for collisions with asteroids
        SnapshotArray<Actor> asteroids = asteroidsGroup.getChildren();
        for (Actor asteroid : asteroids) {
            if (CollisionChecker.checkCollision((CircleCollider)asteroid, (CircleCollider)Bullet.this)) {
                // collision
                ((Asteroid)asteroid).explode();
                active = false;
            }
        }
    }

    public boolean isActive() {
        return (active && x >= 0 && x < Gdx.graphics.getWidth() && y >= 0 && y < Gdx.graphics.getHeight());
    }
    
    public float getColliderRadius() {
        return 1;
    }

    public Vector2 getColliderCenter() {
        return new Vector2(x, y);
    }

}
