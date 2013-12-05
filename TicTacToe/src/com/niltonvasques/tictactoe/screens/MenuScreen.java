package com.niltonvasques.tictactoe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.niltonvasques.tictactoe.TicTacToeGame;
import com.niltonvasques.tictactoe.cpu.Difficult;
import com.niltonvasques.tictactoe.cpu.GameMode;

public class MenuScreen implements Screen{
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	private final TicTacToeGame mGame;
	
	// Managers
	private OrthographicCamera mCamera;
	private SpriteBatch mBatch;
	private Stage mStage;
	
	// Resources	
	private Texture mBoardTexture;
	private Texture mMenuTexture;
	private TextureRegion mBoardTextureRegion;
	private TextureRegion mArcadeModeMenu;
	private TextureRegion m2PlayersModeMenu;
	private TextureRegion mEasyDifficultMenu;
	private TextureRegion mMediumDifficultMenu;
	private TextureRegion mHardDifficultMenu;
	
	private Image mArcadeModeButton;
	private Image m2PlayersModeButton;
	private Image mEasyButton;
	private Image mMediumButton;
	private Image mHardButton;
	
	
	public MenuScreen(TicTacToeGame game) {
		
		mGame = game;
		
		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(true, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		mBatch = new SpriteBatch();
		
		mStage = new Stage(CAMERA_WIDTH, CAMERA_HEIGHT, true);
		
		mBoardTexture = new Texture("data/bg.jpg");
		mBoardTextureRegion = new TextureRegion(mBoardTexture,0,0,512,800);
		mBoardTextureRegion.flip(false, true);
		
		mMenuTexture = new Texture("data/round_menu.png");
		mArcadeModeMenu = new TextureRegion(mMenuTexture,0,380,512,95);
		m2PlayersModeMenu = new TextureRegion(mMenuTexture,0,475,512,95);
		mEasyDifficultMenu = new TextureRegion(mMenuTexture,0,570,512,95);
		mMediumDifficultMenu = new TextureRegion(mMenuTexture,0,665,512,95);
		mHardDifficultMenu = new TextureRegion(mMenuTexture,0,760,512,95);
		
		mArcadeModeButton = new Image(mArcadeModeMenu);
		mArcadeModeButton.setPosition(0, 400);
		mArcadeModeButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				mArcadeModeButton.remove();
				m2PlayersModeButton.remove();
				mStage.addActor(mEasyButton);
				mStage.addActor(mMediumButton);
				mStage.addActor(mHardButton);
			};
			}
		);
		
		m2PlayersModeButton = new Image(m2PlayersModeMenu);
		m2PlayersModeButton.setPosition(0, 300);
		m2PlayersModeButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				mGame.setScreen(new GameScreen(mGame,GameMode.TWO_PLAYERS_MODE));
				dispose();
			};
			}
		);
		
		mEasyButton = new Image(mEasyDifficultMenu);
		mEasyButton.setPosition(0, 450);
		mEasyButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				mGame.setScreen(new GameScreen(mGame,GameMode.ARCADE_MODE,Difficult.EASY));
				dispose();
			};
			}
		);
		
		mMediumButton = new Image(mMediumDifficultMenu);
		mMediumButton.setPosition(0, 350);
		mMediumButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				mGame.setScreen(new GameScreen(mGame,GameMode.ARCADE_MODE,Difficult.MEDIUM));
				dispose();
			};
			}
		);
		
		mHardButton = new Image(mHardDifficultMenu);
		mHardButton.setPosition(0, 250);
		mHardButton.addListener(new ClickListener(){
			public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
				mGame.setScreen(new GameScreen(mGame,GameMode.ARCADE_MODE,Difficult.HARD));
				dispose();
			};
			}
		);
		
		mStage.addActor(mArcadeModeButton);
		mStage.addActor(m2PlayersModeButton);
		
		Gdx.input.setInputProcessor(mStage);
		
	}

	@Override
	public void render(float delta) {
		
		mCamera.update();
		mBatch.setProjectionMatrix(mCamera.combined);
		
		mStage.act(delta);
		
		mBatch.begin();
		
		mBatch.draw(mBoardTextureRegion, 0, 0);
		
		mBatch.end();
		
		mStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
//		mStage.setViewport(width, height, true);
		System.out.println("MenuScreen.resize()");
		mStage.addActor(mArcadeModeButton);
		mStage.addActor(m2PlayersModeButton);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		mBoardTexture.dispose();
		mMenuTexture.dispose();
		mStage.dispose();
		
	}

}
