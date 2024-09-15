package tetris;

public class GameOverSystem extends SubSystem {
	private int direction;
	private int x;
	private int y;
	

	public GameOverSystem(KeySystem key) {
		super(key);
		
		CommonField.GAME_OVER = false;
		x = 1;
		y = CommonField.field.length - 2;
		direction = 1;
	}

	@Override
	public SubSystem system() {
		if(!CommonField.GAME_OVER){
			boolean flg;
			flg = true;
			do{
				if(existBlock(y,x)){
					CommonField.field[y][x] = Block.BLOKEN_BLOCK;
					flg = false;
				}
			}while(flg && next());
		}else{
			if(key.isPressed()){
				key.clear();
				CommonField.window = BaseWindow.RANKING_WINDOW;
				nextSystem = new RankingSystem(key);
			}
		}
		return nextSystem;
	}
	
	private boolean existBlock(int y,int x){
		if(CommonField.field[y][x]>Block.VOID && CommonField.field[y][x] < Block.EDGE_WALL){
			return true;
		}
		return false;
	}

	private boolean next(){
		x += direction;
		if(CommonField.field[y][x] == Block.EDGE_WALL){
			if(y>0){
				x-=direction;
				y--;
				direction *= -1;
			} else {
				CommonField.GAME_OVER = true;
				return false;
			}
		}
		
		return true;
	}
}
