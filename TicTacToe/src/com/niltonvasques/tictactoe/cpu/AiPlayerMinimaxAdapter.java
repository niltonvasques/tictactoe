package com.niltonvasques.tictactoe.cpu;

public class AiPlayerMinimaxAdapter extends Strategy{
	
	private AIPlayerMinimax minimax;
	

	public AiPlayerMinimaxAdapter(Board board) {
		super(board);
		minimax = new AIPlayerMinimax(board);
		minimax.setSeed(Seed.NOUGHT);
	}

	@Override
	public Cell compute() {
		int[] m = minimax.move();
		if(m == null) return null;
		
		Cell c = new Cell(m[1], m[0]);
		
		return c;
	}

}
