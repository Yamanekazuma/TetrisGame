package tetris;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GameSystem extends SubSystem {
	private int plevel;
	private int fall_speed;
	private int fall_cells;
	private Block currentBlock;
	private int cby;
	private int cbx;
	private boolean flg;
	private int moveCnt;
	private int timeCnt;
	private int cnt;
	private int preDelLine;
	private boolean canHold;
	private Date startTime;
	private int lastLine;
	private int startLevel;

	public GameSystem(KeySystem key,int startLevel,int lastLine) {
		super(key);
		this.lastLine = lastLine;
		this.startLevel = startLevel;
		
		initialize();
	}
	
	private void initialize(){
		CommonField.nextBlocks = new ArrayList<Integer>();
		CommonField.holdBlock = null;
		currentBlock = null;
		initField();
		chooseBlocks();
		CommonField.GAME_LINE = 0;
		CommonField.GAME_SCORE = 0;
		CommonField.GAME_LEVEL = startLevel;
		CommonField.GAME_TIME.setTimeInMillis(0);
		cnt = 1;
		preDelLine = 0;
		startTime = new Date();
		CommonField.isPaused = false;
	}

	private void initField() {
		for (int y = 0; y < CommonField.field.length; y++) {
			for (int x = 0; x < CommonField.field[0].length; x++) {
				if (x == 0 || x == CommonField.field[0].length - 1 || y == CommonField.field.length - 1) {
					CommonField.field[y][x] = Block.EDGE_WALL;
				} else {
					CommonField.field[y][x] = Block.VOID;
				}
			}
		}
	}

	private void chooseBlocks() {
		ArrayList<Integer> blocks = new ArrayList<Integer>();
		Random rnd = new Random();
		int i;

		for (int n = 1; n <= Block.BLOCK_TYPES; n++) {
			blocks.add(n);
		}
		while (blocks.size() > 0) {
			i = rnd.nextInt(blocks.size());
			CommonField.nextBlocks.add(blocks.get(i));
			blocks.remove(i);
		}
	}

	private void newBlockSet(Block block) {
		currentBlock = block;
		cbx = (CommonField.field[0].length - 2) / 2;
		cby = 0;
		if (block != null) {
			flg = !canPut();
		}
		moveCnt = 40 - (CommonField.GAME_LEVEL / 10);
		timeCnt = 0;
		canHold = true;
	}

	private void calcFallSpeed() {
		fall_speed = (int) (-0.1 * CommonField.FPS * CommonField.GAME_LEVEL + 1.1 * CommonField.FPS);
		fall_cells = 1;
		if (fall_speed <= 0) {
			if (CommonField.GAME_LEVEL > 10 && CommonField.GAME_LEVEL < 21) {
				fall_cells = CommonField.GAME_LEVEL - 10;
			}
			fall_speed = 1;
		}
	}

	private boolean canPut() {
		int[][] block = currentBlock.getBlock();

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[y][x] != Block.VOID && CommonField.field[cby + y][cbx + x] > Block.MAX_BLOCK_TYPES) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean canMove(int vy, int vx) {
		int[][] block = currentBlock.getBlock();

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[y][x] != Block.VOID) {
					if (CommonField.field[cby + y + vy][cbx + x + vx] > Block.MAX_BLOCK_TYPES) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean canTurn() {
		boolean ans = true;
		int[][] pBlock = currentBlock.getBlock();

		currentBlock.turn();

		int[][] block = currentBlock.getBlock();

		for (int y = 0; y < block.length && ans; y++) {
			for (int x = 0; x < block[0].length && ans; x++) {
				if (block[y][x] != Block.VOID) {
					if (CommonField.field[cby + y][cbx + x] > Block.MAX_BLOCK_TYPES) {
						ans = false;
					}
				}
			}
		}
		currentBlock.setBlock(pBlock);
		return ans;
	}

	private boolean blockControl() {
		int x = 0;
		int y = 0;
		boolean flg = false;

		if (key.isPressed(KeyEvent.VK_LEFT)) {
			x = -1;
		}
		if (key.isPressed(KeyEvent.VK_RIGHT)) {
			x = 1;
		}
		if (key.isPressed(KeyEvent.VK_DOWN)) {
			y = 1;
		}

		if (canMove(y, x)) {
			if (y > 0 || x > 0) {
				flg = true;
			}
			cby += y;
			cbx += x;
		}

		if (key.isPressed(KeyEvent.VK_UP)) {
			while (canMove(1, 0)) {
				cby++;
			}
			blockFixing();
		}

		if (currentBlock == null)
			return flg;

		if (key.isPressed(KeyEvent.VK_SPACE)) {
			if (canTurn()) {
				flg = true;
				currentBlock.turn();
			}
		}

		if (currentBlock == null)
			return flg;

		if (key.isPressed(KeyEvent.VK_SHIFT)) {
			if (canHold) {
				Block temp;
				temp = currentBlock;
				newBlockSet(CommonField.holdBlock);
				CommonField.holdBlock = temp;
				canHold = false;
			}
		}
		return flg;
	}

	private void blockFixing() {
		int[][] block = currentBlock.getBlock();

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				if (block[y][x] != Block.VOID) {
					CommonField.field[cby + y][cbx + x] = block[y][x] + Block.MAX_BLOCK_TYPES;
				}
			}
		}

		deleteCheck();

		currentBlock = null;
	}

	private void deleteCheck() {
		int cnt;
		int dLineCntTotal;
		int delHeight;
		int delLineCnt;
		boolean delChain;

		delChain = false;
		dLineCntTotal = 0;
		delHeight = 0;
		delLineCnt = 0;

		for (int y = cby; y < CommonField.field.length - 1; y++) {
			cnt = 0;
			for (int x = 1; x < CommonField.field[0].length - 1; x++) {
				if (CommonField.field[y][x] != Block.VOID) {
					cnt++;
				} else {
					break;
				}
			}

			if (cnt == CommonField.field[0].length - 2) {
				deleteLine(y);
				delHeight = y;
				delLineCnt++;
				dLineCntTotal++;
				delChain = true;
			} else {
				if (delChain == true) {
					fallFieldBlocks(delHeight, delLineCnt);
					delLineCnt = 0;
				}
				delChain = false;
			}
		}

		if (delChain) {
			fallFieldBlocks(delHeight, delLineCnt);
		}

		if (dLineCntTotal > 0) {
			scoreCheck(dLineCntTotal, preDelLine);
			preDelLine = dLineCntTotal;
		}
	}

	private void deleteLine(int y) {
		for (int x = 1; x < CommonField.field[0].length - 1; x++) {
			CommonField.field[y][x] = Block.VOID;
		}
		CommonField.GAME_LINE++;
		if (CommonField.GAME_LINE % 10 == 0) {
			CommonField.GAME_LEVEL++;
		}
	}

	private void scoreCheck(int line, int preLine) {
		int addScore = 0;

		switch (line) {
		case 1:
			addScore = 120;
			break;
		case 2:
			addScore = 550;
			break;
		case 3:
			addScore = 1800;
			break;
		case 4:
			addScore = 5000;
			break;
		}

		addScore = (int) (addScore * (1 + (CommonField.GAME_LEVEL - 1) / 10.0));

		if (line + preLine >= 6) {
			if (line + preLine == 6) {
				addScore = (int) (addScore * 1.2);
			} else if (line + preLine == 8) {
				addScore = (int) (addScore * 2.0);
			} else {
				addScore = (int) (addScore * 1.5);
			}
		}

		CommonField.GAME_SCORE += addScore;
	}

	private void fallFieldBlocks(int height, int dist) {
		for (int y = height - dist; y >= 0; y--) {
			for (int x = 1; x < CommonField.field[0].length - 1; x++) {
				CommonField.field[y + dist][x] = CommonField.field[y][x];
			}
		}

		for (int y = 0; y < dist; y++) {
			for (int x = 1; x < CommonField.field[0].length - 1; x++) {
				CommonField.field[y][x] = Block.VOID;
			}
		}
	}

	private void putBlockOnField() {
		for (int y = 0; y < CommonField.field.length; y++) {
			for (int x = 0; x < CommonField.field[0].length; x++) {
				if (CommonField.field[y][x] > 0 && CommonField.field[y][x] <= Block.MAX_BLOCK_TYPES) {
					CommonField.field[y][x] = Block.VOID;
				}
			}
		}

		if (currentBlock != null) {
			int[][] block = currentBlock.getBlock();

			for (int y = 0; y < block.length; y++) {
				for (int x = 0; x < block[0].length; x++) {
					if (block[y][x] != Block.VOID) {
						CommonField.field[cby + y][cbx + x] = block[y][x];
					}
				}
			}
		}
	}

	protected SubSystem run() {
		if (plevel != CommonField.GAME_LEVEL) {
			plevel = CommonField.GAME_LEVEL;
			calcFallSpeed();
		}

		if (CommonField.nextBlocks.size() < 2) {
			chooseBlocks();
		}
		if (currentBlock == null) {
			newBlockSet(new Block(CommonField.nextBlocks.remove(0)));
		}

		if (blockControl()) {
			if (currentBlock != null && !canMove(1, 0)) {
				if (moveCnt > 0) {
					timeCnt = 0;
				}
				moveCnt--;
			}
		}

		putBlockOnField();

		if (currentBlock != null) {

			if (!canMove(1, 0)) {
				timeCnt++;
			}

			if ((cnt % fall_speed) == 0) {
				cnt = 0;
				for (int i = 0; i < fall_cells; i++) {
					if (canMove(1, 0)) {
						cby++;
					} else if (timeCnt >= CommonField.FPS) {
						blockFixing();
						break;
					}
				}
			}
		}

		cnt++;
		CommonField.GAME_TIME.setTimeInMillis(new Date().getTime() - startTime.getTime());
		
		if(lastLine <= CommonField.GAME_LINE){
			flg = true;
		}

		if (flg) {
			nextSystem = new GameOverSystem(key);
		} else if(key.isPressed(KeyEvent.VK_ESCAPE)) {
			CommonField.isPaused = true;
			CommonField.pauseSelection = 0;
		}
		
		return nextSystem;
	}
	
	protected SubSystem pause(){
		if(key.isPressed(KeyEvent.VK_DOWN)){
			if(CommonField.pauseSelection < 2){
				CommonField.pauseSelection++;
			} else {
				CommonField.pauseSelection = 0;
			}
		}
		if(key.isPressed(KeyEvent.VK_UP)){
			if(CommonField.pauseSelection > 0){
				CommonField.pauseSelection--;
			} else {
				CommonField.pauseSelection = 2;
			}
		}
		if(key.isPressed(KeyEvent.VK_ESCAPE)){
			CommonField.isPaused = false;
		}
		if(CommonField.pauseSelection==0 && (key.isPressed(KeyEvent.VK_ENTER) || key.isPressed(KeyEvent.VK_SPACE))){
			CommonField.isPaused = false;
		}
		
		if(CommonField.pauseSelection==1&&(key.isPressed(KeyEvent.VK_ENTER)||key.isPressed(KeyEvent.VK_SPACE))){
			nextSystem = new GameSystem(key,startLevel,lastLine);
		}
		if(CommonField.pauseSelection==2&&(key.isPressed(KeyEvent.VK_ENTER)||key.isPressed(KeyEvent.VK_SPACE))){
			CommonField.window = BaseWindow.TITLE_WINDOW;
		}
		
		return nextSystem;
	}

	@Override
	public SubSystem system() {
		if(CommonField.isPaused){
			nextSystem = pause();
		} else {
			nextSystem = run();
		}
		return nextSystem;
	}

}
