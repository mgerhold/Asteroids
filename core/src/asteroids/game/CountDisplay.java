package asteroids.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CountDisplay extends Label {

    public final static String name = "CountDisplay";

    int count = 0;

    public CountDisplay() {
        super("", new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
        setName(name);
        refresh();
    }

    public void increase() {
        count++;
        refresh();
    }

    public void decrease() {
        count--;
        refresh();
    }

    private void refresh() {
        setText("Asteroids: " + Integer.toString(count));
    }

    public int getCount() {
        return count;
    }
    
}
