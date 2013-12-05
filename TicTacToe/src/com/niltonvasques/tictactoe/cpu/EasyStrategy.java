package com.niltonvasques.tictactoe.cpu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.niltonvasques.tictactoe.players.Player;

public class EasyStrategy extends Strategy{
	
	protected int[] sums;
	
	public EasyStrategy(Board matrix) {
		super(matrix);
	}

	@Override
	public Cell compute() {
		System.out.println("EasyStrategy.compute() in "+this.getClass().getName());
		Cell pos = null;

		computeSums();

		pos = tryWin();
		
		if(pos == null){
			pos = tryBlock();
			
			if(pos == null){
				pos = tryPlayRandom();
			}			
		}

		return pos;
	}

	protected Cell tryPlayRandom() {
		List<Cell> emptyPositions = new ArrayList<Cell>();
		//EASY STRATEGY = RANDOM MARK
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(getMatrix()[i][j] == 0){
					emptyPositions.add(new Cell(i, j));
				}
			}
		}
		
		if(emptyPositions.size() > 0){
			int random = MathUtils.random(emptyPositions.size()-1);
			return emptyPositions.get(random);
		}
		return null;
	}

	protected Cell tryBlock() {
		boolean cpuMoved = false;
		int x = 0;
		int y = 0;
		
		//Blocking Victory Strategy
		if(!cpuMoved){
			for(int k = 0; k < 3; k++){
				if(sums[k] == Player.PLAYER_1_NUMBER*2){
					for(int i = 0; i < 3; i++){
						if(getMatrix()[k][i] == 0 ){
							x = k;
							y = i;
						}
					}
					cpuMoved = true;
					break;
				}

				if(sums[k+3] == Player.PLAYER_1_NUMBER*2){
					for(int i = 0; i < 3; i++){
						if(getMatrix()[i][k] == 0 ){
							x = i;
							y = k;
						}
					}
					cpuMoved = true;
					break;
				}
			}

			if(!cpuMoved && sums[6] == Player.PLAYER_1_NUMBER*2){
				for(int i = 0; i < 3; i++){
					if(getMatrix()[i][i] == 0 ){
						x = i;
						y = i;						
						break;
					}
				}
				cpuMoved = true;
			}

			if(!cpuMoved && sums[7] == Player.PLAYER_1_NUMBER*2){
				for(int i = 0; i < 3; i++){
					if(getMatrix()[i][2-i] == 0 ){
						x = i;
						y = 2-i;
						break;
					}
				}
				cpuMoved = true;
			}
		}
		
		if(cpuMoved)
			return new Cell(x, y);
		else 
			return null;
	}

	protected Cell tryWin() {
		boolean cpuMoved = false;
		int x = 0;
		int y = 0;
		//Victory Strategy
		for(int k = 0; k < 3; k++){
			if(sums[k] == Player.PLAYER_2_NUMBER*2){
				for(int i = 0; i < 3; i++){
					if(getMatrix()[k][i] == 0 ){
						x = k;
						y = i; 
					}
				}
				cpuMoved = true;
				break;
			}

			if(sums[k+3] == Player.PLAYER_2_NUMBER*2){
				for(int i = 0; i < 3; i++){
					if(getMatrix()[i][k] == 0 ){
						x = i;
						y = k;
					}
				}
				cpuMoved = true;
				break;
			}
		}
		
		if(!cpuMoved && sums[6] == Player.PLAYER_2_NUMBER*2){
			for(int i = 0; i < 3; i++){
				if(getMatrix()[i][i] == 0 ){
					x = i;
					y = i;
					break;
				}
			}
			cpuMoved = true;
		}

		if(!cpuMoved && sums[7] == Player.PLAYER_2_NUMBER*2){
			for(int i = 0; i < 3; i++){
				if(getMatrix()[i][2-i] == 0 ){
					x = i;
					y = 2-1;
					break;
				}
			}
			cpuMoved = true;
		}
		if(cpuMoved)
			return new Cell(x, y);
		else 
			return null;
	}

	protected void computeSums() {
		sums = new int[8];
		for(int i = 0; i < 3; i++){
			sums[0]+= getMatrix()[0][i];
			sums[1]+= getMatrix()[1][i];
			sums[2]+= getMatrix()[2][i];

			sums[3]+= getMatrix()[i][0];
			sums[4]+= getMatrix()[i][1];
			sums[5]+= getMatrix()[i][2];

			sums[6] += getMatrix()[i][i];
			sums[7] += getMatrix()[i][2-i];
		}
	}
	
	

}
