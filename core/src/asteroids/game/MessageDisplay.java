package asteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class MessageDisplay extends Label {

    public static final String name = "MessageDisplay";

    public MessageDisplay() {
        super("", new Label.LabelStyle(new BitmapFont(), new Color(1, 1, 1, 1)));
        setAlignment(Align.center);
        setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 3 / 4);
        setName(name);
    }

    public void showMessage(String message) {
        setText(message);        
    }

}
