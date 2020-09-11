package asteroids.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class LivesCounter extends Label {

    int lives = 3;

    public LivesCounter() {
        super("", new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
        setAlignment(Align.right);
        refresh();
    }

    private void refresh() {
        setText("Lives: " + lives);
    }
    
    public void increase() {
        lives++;
        refresh();
    }

    public void decrease() {
        if (lives > 0) {
            lives--;
            refresh();
        }
    }

    public int getLives() {
        return lives;
    }

}
