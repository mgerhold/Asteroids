package asteroids.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class SoundHandler {

    private static SoundHandler instance = null;
    private HashMap<String, Sound> sounds;

    private SoundHandler() {
        sounds = new HashMap<>();
        FileHandle directoryHandle = Gdx.files.internal("sounds");
        for (FileHandle file : directoryHandle.list()) {
            if (file.extension().equals("wav")) {
                sounds.put(file.nameWithoutExtension(), Gdx.audio.newSound(file));
            }            
        }
    }

    public static SoundHandler getInstance() {
        if (instance == null)
            instance = new SoundHandler();
        return instance;
    }

    public static void playSound(String name) {
        playSound(name, 1.0f);
    }

    public static void playSound(String name, float volume) {
        Sound sound = getInstance().sounds.get(name);
        if (sound == null) {
            Gdx.app.error("SoundHandler", "Could not find sound file named " + name);
            return;
        }
        sound.play(volume);
    }
    
}
