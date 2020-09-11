package asteroids.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import asteroids.game.Asteroids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// original resolution: 256 x 231		
		config.width = 256 * 2;
		config.height = 231 * 2;
		config.resizable = false;
		config.pauseWhenBackground = true;
		config.pauseWhenMinimized = true;
		new LwjglApplication(new Asteroids(), config);
	}
}
