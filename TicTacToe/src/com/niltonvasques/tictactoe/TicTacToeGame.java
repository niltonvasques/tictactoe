package com.niltonvasques.tictactoe;

import com.badlogic.gdx.Game;
import com.niltonvasques.tictactoe.screens.GameScreen;
import com.niltonvasques.tictactoe.screens.MenuScreen;

public class TicTacToeGame extends Game {
	
	private IAdRequestHandler mAdRequestHandler = null;
	
	@Override
	public void create() {		
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void registerAdRequestHandler(IAdRequestHandler request){
		mAdRequestHandler = request;
	}
	
	public void showAds(boolean show){
		if(mAdRequestHandler != null){
			mAdRequestHandler.request(show);
		}
	}
}
