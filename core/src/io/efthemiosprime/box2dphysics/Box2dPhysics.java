package io.efthemiosprime.box2dphysics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.efthemiosprime.box2dphysics.screens.Basic;

public class Box2dPhysics extends Game {

	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Basic());
	}

	@Override
	public void render () {

		super.render();
	}
}
