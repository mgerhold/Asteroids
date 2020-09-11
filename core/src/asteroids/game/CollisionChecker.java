package asteroids.game;

public class CollisionChecker {

    public static boolean checkCollision(CircleCollider lhs, CircleCollider rhs) {
        return (rhs.getColliderCenter().sub(lhs.getColliderCenter()).len() <= lhs.getColliderRadius() + rhs.getColliderRadius());        
    }

}
