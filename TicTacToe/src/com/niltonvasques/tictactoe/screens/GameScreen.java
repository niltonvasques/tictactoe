package com.niltonvasques.tictactoe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.niltonvasques.tictactoe.TicTacToeGame;
import com.niltonvasques.tictactoe.cpu.AIPlayerMinimax;
import com.niltonvasques.tictactoe.cpu.AIPlayerMinimaxPruning;
import com.niltonvasques.tictactoe.cpu.AiPlayerMinimaxAdapter;
import com.niltonvasques.tictactoe.cpu.AIPlayer2StrategyAdapter;
import com.niltonvasques.tictactoe.cpu.Board;
import com.niltonvasques.tictactoe.cpu.Cell;
import com.niltonvasques.tictactoe.cpu.Difficult;
import com.niltonvasques.tictactoe.cpu.EasyStrategy;
import com.niltonvasques.tictactoe.cpu.GameMode;
import com.niltonvasques.tictactoe.cpu.MediumStrategy;
import com.niltonvasques.tictactoe.cpu.Seed;
import com.niltonvasques.tictactoe.cpu.Strategy;

public class GameScreen implements Screen{
	
	private static final long INTERVAL_RESTART_ROUND = 1000;
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	
	private static final int PLAYER_1_NUMBER = 1;
	private static final int PLAYER_2_NUMBER = 10;
	private static final int NO_WINNERS = -1;
	
	private final TicTacToeGame mGame;
	
	// Managers
	private OrthographicCamera mCamera;
	private SpriteBatch mBatch;
	
	// Resources
	
	private Texture mBoardTexture;
	private Texture mCrossTexture;
	private Texture mCircleTexture;
	private Texture mRoundMenuTexture;
	private TextureRegion mBoardTextureRegion;
	private TextureRegion mNewRoundTextureRegion;
	private TextureRegion mResetScoresTextureRegion;
	private TextureRegion mPlayer1WinTextureRegion;
	private TextureRegion mPlayer2WinTextureRegion;
	private BitmapFont mFont;
	
	private Sound mScratchingSound;
	
	// Logic
	
	private GameMode mGameMode;
	private Strategy strategy = null;
	
	private Rectangle[][] mAreasMatrix;
	private Array<Rectangle> mCrossArray;
	private Array<Rectangle> mCircleArray;
	private Rectangle mNewRoundMenuRect;
	private Rectangle mResetScoresMenuRect;
	private int mPlayerMove = PLAYER_1_NUMBER;
	private Board board;
	private boolean mRoundFinished = false;
	private int mMovesCount = 0;
	private int mPlayer1Score = 0;
	private int mPlayer2Score = 0;
	private int mLastResult = -1;
	
	private long mTimeMilis = 0;
	
	private float mDeltaScreenY;
	private float mDeltaScreenX;
	
	public GameScreen(TicTacToeGame game, GameMode mode ) {
		mGame = game;
		mGameMode = mode;
		
		// create camera
		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(true, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		// instantiating a sprite batch 
		mBatch = new SpriteBatch();
		
		// Load Resources
		mBoardTexture = new Texture("data/bg.jpg");
		mBoardTextureRegion = new TextureRegion(mBoardTexture,0,0,512,800);
		mBoardTextureRegion.flip(false, true);
		
		mRoundMenuTexture = new Texture("data/round_menu.png");
		mNewRoundTextureRegion = new TextureRegion(mRoundMenuTexture, 0, 0, 512, 95);
		mNewRoundTextureRegion.flip(false, true);
		mResetScoresTextureRegion = new TextureRegion(mRoundMenuTexture, 0, 95, 512, 95);
		mResetScoresTextureRegion.flip(false, true);
		mPlayer1WinTextureRegion = new TextureRegion(mRoundMenuTexture, 0, 190, 512, 95);
		mPlayer1WinTextureRegion.flip(false, true);
		mPlayer2WinTextureRegion = new TextureRegion(mRoundMenuTexture, 0, 285, 512, 95);
		mPlayer2WinTextureRegion.flip(false, true);
		
		
		
		mCrossTexture = new Texture("data/cross.png");
		mCircleTexture = new Texture("data/circle.png");
		
		mScratchingSound = Gdx.audio.newSound(Gdx.files.internal("data/scratching.mp3"));
		
//		mFont = new BitmapFont(true);
		mFont = new BitmapFont(Gdx.files.internal("data/ayear-font.fnt"),true);
		mFont.scale(0.5f);
		mFont.setColor(Color.BLACK);
		
		// fill areas
		mAreasMatrix = new Rectangle[3][3];
		
		mAreasMatrix[0][0] = new Rectangle(0, 0, 165, 153);
		mAreasMatrix[0][1] = new Rectangle(167, 0, 165, 153);
		mAreasMatrix[0][2] = new Rectangle(333, 0, 165, 153);
		
		mAreasMatrix[1][0] = new Rectangle(0, 157, 163, 186);
		mAreasMatrix[1][1] = new Rectangle(167, 157, 163, 186);
		mAreasMatrix[1][2] = new Rectangle(333, 157, 163, 186);
		
		mAreasMatrix[2][0] = new Rectangle(0, 344, 165, 186);
		mAreasMatrix[2][1] = new Rectangle(167, 344, 165, 186);
		mAreasMatrix[2][2] = new Rectangle(333, 344, 165, 186);
				
		mCrossArray = new Array<Rectangle>();
		mCircleArray = new Array<Rectangle>();
		
		board = new Board();
		
		// initialize rects of menu
		mNewRoundMenuRect = new Rectangle(0,342,CAMERA_WIDTH, 95);
		mResetScoresMenuRect = new Rectangle(0, 454, CAMERA_WIDTH, 95);
		
		mTimeMilis = TimeUtils.millis();
		
		mDeltaScreenX =  (float) CAMERA_WIDTH / Gdx.graphics.getWidth();
		mDeltaScreenY = (float) CAMERA_HEIGHT / Gdx.graphics.getHeight();
		
		System.out.println("Started Graphics "+Gdx.graphics.getWidth()+" x "+Gdx.graphics.getHeight());
		
	}
	
	public GameScreen(TicTacToeGame game, GameMode mode, Difficult difficult) {
		this(game,mode);
		System.out.println("Dificuldade"+difficult);
		if(mode == GameMode.ARCADE_MODE){
			if(difficult == Difficult.EASY)
				strategy = new EasyStrategy(board);
			if(difficult == Difficult.MEDIUM)
				strategy = new MediumStrategy(board);
			if(difficult == Difficult.HARD){
				strategy = new AIPlayer2StrategyAdapter(new AIPlayerMinimaxPruning(board));
			}
		}
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		mCamera.update();
		
		mBatch.setProjectionMatrix(mCamera.combined);
		
		mBatch.begin();
		
		mBatch.draw(mBoardTextureRegion, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		
		for (Rectangle cross : mCrossArray) {
			mBatch.draw(mCrossTexture, cross.x, cross.y, cross.width, cross.height);
		}
		
		for (Rectangle circle : mCircleArray) {
			mBatch.draw(mCircleTexture, circle.x, circle.y, circle.width, circle.height);
		}
		
		//Draw Scores
		mFont.draw(mBatch, ""+mPlayer1Score, 100, 730);
		mFont.draw(mBatch, ""+mPlayer2Score, 313, 730);
		
		if(mRoundFinished){
			mBatch.draw(mNewRoundTextureRegion, 0, 342, CAMERA_WIDTH, 95);
			
			mBatch.draw(mResetScoresTextureRegion, 0, 454, CAMERA_WIDTH, 95);
			
			if(mLastResult == PLAYER_1_NUMBER){
				mBatch.draw(mPlayer1WinTextureRegion, 0, 50, CAMERA_WIDTH, 95);
			}else if(mLastResult == PLAYER_2_NUMBER){
				mBatch.draw(mPlayer2WinTextureRegion, 0, 50, CAMERA_WIDTH, 95);
			}
			
			mGame.showAds(true);
		}else{
			mGame.showAds(false);
		}
		
		mBatch.end();		
		
		if(mRoundFinished){
			if((TimeUtils.millis() - mTimeMilis) > INTERVAL_RESTART_ROUND){
				if(Gdx.input.isKeyPressed(Keys.ENTER)){
					newRound();
					mRoundFinished = false;
				}
				if(Gdx.input.isTouched()){
					Rectangle touch = getTouchRect();
					if(mNewRoundMenuRect.overlaps(touch)){

						mTimeMilis = TimeUtils.millis();

						newRound();
						mRoundFinished = false;
					}

					if(mResetScoresMenuRect.overlaps(touch)){

						mTimeMilis = TimeUtils.millis();

						newRound();
						mRoundFinished = false;
						mPlayer1Score = 0;
						mPlayer2Score = 0;
					}
				}
			}
			return;
		}
		
		switch (checkingWinners()) {
		case PLAYER_1_NUMBER:
			System.out.println("PLAYER 1 WINNER");
			mTimeMilis = TimeUtils.millis();
			mPlayer1Score++;
			mRoundFinished = true;
			mPlayerMove = PLAYER_1_NUMBER;
			mLastResult = PLAYER_1_NUMBER;
			break;

		case PLAYER_2_NUMBER:
			System.out.println("PLAYER 2 WINNER");
			mTimeMilis = TimeUtils.millis();
			mPlayer2Score++;
			mRoundFinished = true;
			mPlayerMove = PLAYER_2_NUMBER;
			mLastResult = PLAYER_2_NUMBER;
			break;
			
		case NO_WINNERS:
			System.out.println("NO WINNERS");
			mTimeMilis = TimeUtils.millis();
			mRoundFinished = true;
			mLastResult = NO_WINNERS;
			break;
			
		default:
			break;
		};
		
		if((TimeUtils.millis() - mTimeMilis) > INTERVAL_RESTART_ROUND){
			if(mPlayerMove == PLAYER_2_NUMBER && mGameMode == GameMode.ARCADE_MODE){
				computerMove();
			}else{		
				checkingNewMoves();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		mCircleTexture.dispose();
		mCrossTexture.dispose();
		mRoundMenuTexture.dispose();
		mBoardTexture.dispose();
		mFont.dispose();
		mScratchingSound.dispose();
		mBatch.dispose();		
	}
	
	private Vector3 mTouchPos = new Vector3();
	private Rectangle mRectTouch = new Rectangle();
	private Rectangle getTouchRect() {
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		mTouchPos.x = x;
		mTouchPos.y = y;
		mTouchPos.z = 0;
		mCamera.unproject(mTouchPos);
		mRectTouch.x = mTouchPos.x;
		mRectTouch.y = mTouchPos.y;
		mRectTouch.width = 1;
		mRectTouch.height = 1;
		
		return mRectTouch;
	}

	private void checkingNewMoves() {
			if(Gdx.input.isTouched()){
				
				Rectangle touch = getTouchRect();
				
				for(int i = 0; i < 3; i++){
					for(int j = 0; j < 3; j++){
						if(mAreasMatrix[i][j] != null){
							if(board.getMatrix()[i][j] == 0 && mAreasMatrix[i][j].overlaps(touch)){
								if(mPlayerMove == PLAYER_1_NUMBER){
									mCrossArray.add(mAreasMatrix[i][j]);
									mPlayerMove = PLAYER_2_NUMBER;
									board.getMatrix()[i][j] = PLAYER_1_NUMBER;
									board.cells[j][i].content = Seed.CROSS;
								}else if(mPlayerMove == PLAYER_2_NUMBER){
									mCircleArray.add(mAreasMatrix[i][j]);
									mPlayerMove = PLAYER_1_NUMBER;
									board.getMatrix()[i][j] = PLAYER_2_NUMBER;
									board.cells[j][i].content = Seed.NOUGHT;
								}
								mScratchingSound.play();
								mMovesCount++;
								return;
							}
						}
					}
				}
			}
		}

	private int checkingWinners() {
		//		[ 10,    0,    0 ] = 10 = sum[0]
		//		[ 1,     0,    0  ] = 1 = sum[1]
		//		[ 1,     1,    10 ] = 2 = sum[2]
		//		  12,    1,    10     20
		//  sum[7]		 sum[3], sum[4], sum[5]     sum[6]		
		int[] sums = new int[8];
		for(int i = 0; i < 3; i++){
			sums[0]+= board.getMatrix()[0][i];
			sums[1]+= board.getMatrix()[1][i];
			sums[2]+= board.getMatrix()[2][i];
	
			sums[3]+= board.getMatrix()[i][0];
			sums[4]+= board.getMatrix()[i][1];
			sums[5]+= board.getMatrix()[i][2];	
	
			sums[6] += board.getMatrix()[i][i];		
			sums[7] += board.getMatrix()[i][2-i];
		}
	
		for(int i = 0; i < 8; i++){
			if(sums[i] == PLAYER_1_NUMBER*3){
				return PLAYER_1_NUMBER;
			}
	
			if(sums[i] == PLAYER_2_NUMBER*3){
				return PLAYER_2_NUMBER;
			}			
		}
		
		return (mMovesCount == 9 ? NO_WINNERS : 0);
	}
	
	private void computerMove(){
		
		System.out.println("ComputerMove in "+strategy.getClass().getName());
		
		Cell pos = strategy.compute();

		if(pos != null){
			mCircleArray.add(mAreasMatrix[pos.x][pos.y]);
			board.getMatrix()[pos.x][pos.y] = PLAYER_2_NUMBER;
			board.cells[pos.y][pos.x].content = Seed.NOUGHT;
			mPlayerMove = PLAYER_1_NUMBER;
			mScratchingSound.play();
			mMovesCount++;
		}
	}
	
	

	private void newRound() {
		mMovesCount = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				board.getMatrix()[i][j] = 0;
				board.cells[j][i].content = Seed.EMPTY;
			}
		}
		
		mCrossArray.clear();
		mCircleArray.clear();		
	}

}
