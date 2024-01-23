package com.mygdx.gotas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Gotas extends Game {
	public AssetManager assetManager;
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont font2;
	public BitmapFont font3;




	public void create() {

		// Crea una instancia de AssetManager
		assetManager = new AssetManager();

		// Carga tus assets en el AssetManager
		assetManager.load("droplet.png", Texture.class);
		assetManager.load("bucket.png", Texture.class);
		assetManager.load("piedra.png", Texture.class);
		assetManager.load("drop.wav", Sound.class);
		assetManager.load("hit.wav", Sound.class);
		assetManager.load("rain.mp3", Music.class);
		assetManager.load("Peace sells.mp3", Music.class);
		assetManager.load("Points.mp3", Music.class);
		assetManager.load("lluvia.jpg", Texture.class);
		assetManager.load("tormenta.jpg", Texture.class);
		assetManager.load("calavera.png", Texture.class);


		// Espera a que se carguen todos los assets antes de continuar
		assetManager.finishLoading();

		batch = new SpriteBatch();
		font = new BitmapFont(false);
		font.setColor(242 / 255f, 171 / 255f, 0, 1);//Cambiar el color de la letra
		font.getData().setScale(2f);  // Ajusta el tamaño de la fuente según tus necesidades

		font2 = new BitmapFont(false);
		font2.setColor(242 / 255f, 171 / 255f, 0, 1);
		font2.getData().setScale(1.8f);  // Ajusta el tamaño de la fuente según tus necesidades

		font3 = new BitmapFont(false);
		font3.setColor(242 / 255f, 171 / 255f, 0, 1);
		font3.getData().setScale(6f);

		this.setScreen(new MainMenuScreen(this));

	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		font2.dispose();
	}
}
