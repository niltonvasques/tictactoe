package com.niltonvasques.tictactoe.cpu;

public class AIPlayer2StrategyAdapter extends Strategy{
	
	private AIPlayer ai;
	

	public AIPlayer2StrategyAdapter(AIPlayer ai) {
		super(ai.getBoard());
		this.ai = ai;
		ai.setSeed(Seed.NOUGHT);
	}

	@Override
	public Cell compute() {
		int[] m = ai.move();
		if(m == null) return null;
		
		Cell c = new Cell(m[1], m[0]);
		
		return c;
	}

}
