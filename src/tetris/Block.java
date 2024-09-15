package tetris;

import java.awt.Color;

public class Block {

	// 色
	public static final Color[] BLOCK_COLOR = { null, Color.CYAN, Color.ORANGE, Color.BLUE, Color.YELLOW, Color.MAGENTA,
			Color.GREEN, Color.RED};
	public static final Color VOID_COLOR = new Color(240,240,240);
	public static final Color WALL_COLOR = Color.BLACK;
	public static final Color BLOKEN_COLOR = Color.GRAY;
	
	//ブロック
	private int[][] block;
	
	// 種類
	private int sort;
	public static final int BLOKEN_BLOCK = 101;
	public static final int EDGE_WALL = 100;
	public static final int VOID = 0;
	public static final int I_BLOCK = 1;
	public static final int J_BLOCK = 2;
	public static final int L_BLOCK = 3;
	public static final int O_BLOCK = 4;
	public static final int T_BLOCK = 5;
	public static final int S_BLOCK = 6;
	public static final int Z_BLOCK = 7;
	
	public static final int BLOCK_TYPES =7;
	public static final int MAX_BLOCK_TYPES = 50;
	
	// コンストラクタ
	public Block(int sort) {
		this.sort = sort;

		setBlocks(this.sort);
	}
	
	// ブロックの初期化
	public void setBlocks(int n) {
		switch (n) {
		case I_BLOCK:
			setBlock(new int[][] { { n, n, n, n }, { 0, 0, 0, 0 } });
			break;
		case J_BLOCK:
			setBlock(new int[][] { { 0, 0, n }, { n, n, n } });
			break;
		case L_BLOCK:
			setBlock(new int[][] { { n, 0, 0 }, { n, n, n } });
			break;
		case O_BLOCK:
			setBlock(new int[][] { { n, n }, { n, n } });
			break;
		case T_BLOCK:
			setBlock(new int[][] { { 0, n, 0 }, { n, n, n } });
			break;
		case S_BLOCK:
			setBlock(new int[][] { { 0, n, n }, { n, n, 0 } });
			break;
		case Z_BLOCK:
			setBlock(new int[][] { { n, n, 0 }, { 0, n, n } });
			break;
		}
	}
	// 回転
	public void turn() {
		int[][] newBlock = new int[block[0].length][block.length];

		for (int y = 0; y < block.length; y++) {
			for (int x = 0; x < block[0].length; x++) {
				newBlock[x][block.length - y - 1] = block[y][x];
			}
		}

		block = newBlock;
	}

	public int[][] getBlock() {
		return block;
	}

	public void setBlock(int[][] block) {
		this.block = block;
	}
	
	public int getSort(){
		return sort;
	}
}
