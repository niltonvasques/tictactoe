package com.niltonvasques.tictactoe.cpu;



public class HardStrategy extends EasyStrategy{

	public HardStrategy(Board matrix) {
		super(matrix);
	}

//	@Override
//	public Cell compute() {
//		computeSums();
//		Cell pos = possibleWin(Player.PLAYER_2_NUMBER);
//		if(pos == null){
//			pos = possibleWin(Player.PLAYER_1_NUMBER);
//			if(pos == null){
//				
//			}
//		}
//		return pos;
//	}
//	
//	public Cell possibleWin(int player){
//		
//			for(int i = 0; i<3;i++){
//				if(sums[i] == player*2){ // CHECK HORIZONTAL LINES
//					int j = (getMatrix()[i][0] == 0 ? 0: getMatrix()[i][1] == 0 ? 1 : 2);
//					return new Cell(i, j);
//				}
//				
//				if(sums[i+3] == player*2){ // CHECK VERTICAL
//					int j = (getMatrix()[0][i] == 0 ? 0: getMatrix()[1][i] == 0 ? 1 : 2);
//					return new Cell(j, i);
//				}
//			}
//		
//			
//		
//		if(sums[6] == player*2){ // CHECK DIAGONAL 1
//			return (getMatrix()[0][0] == 0 ? new Cell(0, 0): getMatrix()[1][1] == 0 ? new Cell(1, 1) : new Cell(2, 2));
//		}
//		
//		if(sums[7] == player*2){ // CHECK DIAGONAL 2
//			return (getMatrix()[0][2] == 0 ? new Cell(0, 2): getMatrix()[1][1] == 0 ? new Cell(1, 1) : new Cell(2, 0));
//		}
//		
//		return null;
//	}
//	
//	public Cell possibleFork(int player){
//		if(sums[6] == player){
//			if(sums[7] == player){
//				if(getMatrix()[1][1] == 0) return new Cell(1, 1);
//			}
//			if(sums[3] == player){
//				if(getMatrix()[1][1] == 0) return new Cell(1, 1);
//			}
//			if(sums[1] == player){
//				if(getMatrix()[1][1] == 0) return new Cell(1, 1);
//			}
//		}
//		return null;
//	}
//	
	

}
