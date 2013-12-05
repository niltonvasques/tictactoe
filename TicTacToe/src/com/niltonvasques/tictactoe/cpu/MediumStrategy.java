package com.niltonvasques.tictactoe.cpu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.niltonvasques.tictactoe.players.Player;

public class MediumStrategy extends EasyStrategy{

	public MediumStrategy(Board matrix) {
		super(matrix);
		System.out.println("MediumStrategy.MediumStrategy()");
	}
	
	@Override
	public Cell compute() {
		System.out.println("MediumStrategy.compute()");
		Cell pos = null;
		
		computeSums();
		
		pos = tryWin();
		
		if( pos == null){
			pos = tryBlock();
			System.out.println("Block");
			if( pos == null){
				pos = tryCompleteFork();
				System.out.println("CompleteFork");
				if( pos == null){
					pos = tryFork();
					System.out.println("Fork");
					if( pos == null){
						pos = tryFirstPlay();
						System.out.println("FirstPlay");
					}
				}
			}
		}
		
		return pos;
		
	}
	
	public Cell tryCompleteFork(){
		int[][] m = getMatrix().clone();
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(m[i][j] == 0){
					m[i][j] = Player.PLAYER_2_NUMBER;
					
					int[] s = new int[8];
					for(int k = 0; k < 3; k++){
						s[0]+= m[0][k];
						s[1]+= m[1][k];
						s[2]+= m[2][k];

						s[3]+= m[k][0];
						s[4]+= m[k][1];
						s[5]+= m[k][2];	

						s[6] += m[k][k];		
						s[7] += m[k][2-k];
					}
					int count = 0;
					for(int k = 0; k < 8; k++){
						if(s[k] == Player.PLAYER_2_NUMBER*2) count++;
					}
					
					if(count >= 2) return new Cell(i, j);
					m[i][j] = 0;					
				}
			}
		}
		
		
		return null;
	}
	
	public Cell tryFork(){
		Cell pos = null;
		
		List<Cell> list = new ArrayList<Cell>();
		if(getMatrix()[0][0] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(1, 2));
			list.add(new Cell(2, 1));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();
		if(getMatrix()[0][1] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(1, 0));
			list.add(new Cell(1, 2));
			list.add(new Cell(2, 0));
			list.add(new Cell(2, 2));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();		
		if(getMatrix()[0][2] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(1, 0));
			list.add(new Cell(2, 1));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();
		if(getMatrix()[1][0] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(0, 1));
			list.add(new Cell(0, 2));
			list.add(new Cell(2, 1));
			list.add(new Cell(2, 2));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();
		if(getMatrix()[1][2] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(0, 0));
			list.add(new Cell(0, 1));
			list.add(new Cell(2, 0));
			list.add(new Cell(2, 1));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();		
		if(getMatrix()[2][0] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(0, 1));
			list.add(new Cell(1, 2));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();
		if(getMatrix()[2][1] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(0, 0));
			list.add(new Cell(0, 2));
			list.add(new Cell(1, 0));
			list.add(new Cell(1, 2));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		list.clear();		
		if(getMatrix()[2][2] == Player.PLAYER_2_NUMBER){
			list.add(new Cell(0, 1));
			list.add(new Cell(1, 0));
			
			boolean found = false;
			while(!found && list.size() > 0){
				pos = list.get(MathUtils.random(list.size()-1));
				if(getMatrix()[pos.x][pos.y] != 0){
					list.remove(pos);
				}else{
					return pos;
				}				
			}
		}
		
		return pos;
	}
	
	public Cell tryFirstPlay(){
		List<Cell> list = new ArrayList<Cell>();
		for(int i = 0; i < 3; i++){
			list.add(new Cell(0, i));
			list.add(new Cell(2, i));
			if(i != 1) list.add(new Cell(1, i));			
		}
		
		Cell pos = null;
		boolean found = false;
		while(!found && list.size() > 0){
			pos = list.get(MathUtils.random(list.size()-1));
			if(getMatrix()[pos.x][pos.y] != 0){
				list.remove(pos);
			}else{
				found = true;
			}				
		}
		
		return pos;
	}

}
