package asteroids.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Asteroids extends ApplicationAdapter {
	
	TextureAtlas textureAtlas;
	Stage stage;
	LivesCounter livesCounter;
	CountDisplay countDisplay;
	MessageDisplay messageDisplay;

	enum GameState {
		STARTING,
		INGAME,
		WON,
		LOST
	}

	public static GameState gameState = GameState.STARTING;
	
	@Override
	public void create () {
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites/textures-packed/pack.atlas"));
		stage = new Stage();
		// add display for asteroids count
		countDisplay = new CountDisplay();
		countDisplay.setPosition(10, 15);
		stage.addActor(countDisplay);
		// add display for remaining lives
		livesCounter = new LivesCounter();
		livesCounter.setPosition(Gdx.graphics.getWidth() - 10, 15);
		stage.addActor(livesCounter);
		// add message display
		messageDisplay = new MessageDisplay();
		stage.addActor(messageDisplay);
		// add group for asteroids
		Group asteroidsGroup = new Group();
		stage.addActor(asteroidsGroup);
		// add asteroids controller
		stage.addActor(new AsteroidsController(textureAtlas, 6, asteroidsGroup, countDisplay));
		// add bullet controller
		stage.addActor(new BulletController(textureAtlas.findRegion("bullet"), asteroidsGroup));
		// add player
		stage.addActor(
			new Player(textureAtlas.findRegion("player"),
			(AsteroidsController)stage.getRoot().findActor(AsteroidsController.name),
			(BulletController)stage.getRoot().findActor(BulletController.name),
			livesCounter
		));
		// center player
		stage.getRoot().findActor(Player.name).setPosition(Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight() / 2.0f);		
		// trigger creation of SoundHandler instance
		SoundHandler.getInstance();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (gameState == GameState.INGAME || gameState == GameState.STARTING)
			stage.act();
		if (gameState == GameState.INGAME && countDisplay.getCount() == 0) {
			SoundHandler.playSound("won");
			gameState = GameState.WON;
			messageDisplay.showMessage("You won!");
		}
		stage.draw();
	}
	
	@Override
	public void dispose () {
		textureAtlas.dispose();
		stage.dispose();
	}
}
