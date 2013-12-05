package com.niltonvasques.tictactoe.cpu;



public abstract class Strategy{
	private Board board;
	public Strategy(Board matrix) {
		board = matrix;
	}
	
	public abstract Cell compute();

	public Board getBoard() {
		return board;
	}	
	
	public int[][] getMatrix(){
		return board.getMatrix();
	}
}
