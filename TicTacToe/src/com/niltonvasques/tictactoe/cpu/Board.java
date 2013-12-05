package com.niltonvasques.tictactoe.cpu;

import com.niltonvasques.tictactoe.players.Player;

public class Board {
	public static final int COLUMNS = 3;
	public static final int ROWS = 3;
	public Cell[][] cells = new Cell[COLUMNS][ROWS];
	private int[][] table = new int[Board.COLUMNS][Board.ROWS];
	
	public Board(){
		resetBoard();
	}

	private void resetBoard() {
		for(int i = 0; i< 3; i++){
			for(int j = 0; j < 3; j++){
				cells[j][i] = new Cell(j, i);
				cells[j][i].content = Seed.EMPTY;
			}
		}
	}
	
	public int[][] getMatrix(){
		for(int i = 0; i<3;i++){
			for(int j = 0; j < 3; j++){
				table[i][j] = seed2int(cells[j][i].content); 
			}
		}
		return table;
	}
	
	protected int seed2int(Seed s){
		switch (s) {
		case CROSS:
			return Player.PLAYER_1_NUMBER;
		case NOUGHT:
			return Player.PLAYER_2_NUMBER;
		default:
			return 0;
		}
	}
}
