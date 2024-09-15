package tetris;

import java.util.ArrayList;
import java.util.Calendar;

public class CommonField {
	public static final int GAME_MARATHON = 0;
	public static final int GAME_VS_COM = 1;
	public static final int GAME_VS_PLAYER = 2;
	
	public static final int TGAME_ENDLESS = 0;
	public static final int TGAME_TIME_ATTACK = 1;
	public static final int TGAME_SCORE_ATTACK = 2;
	public static final int TGAME_HALF_TA = 3;
	public static final int TGAME_HALF_SA = 4;
	
	public static final int Y0 = 41;
	public static final int X0 = 4;
	
	public static final int NAME_SPACE = 5;
	
	public static int CELL_SIZE;
	public static int X_CELLS;
	public static int Y_CELLS;
	public static int FPS;
	public static int LEVEL;
	
	public static int[][] field;
	public static int gameMode;
	public static int gameType;
	public static int window;
	public static ArrayList<Integer> nextBlocks;
	public static Block holdBlock;
	
	public static FrameRate fr;
	
	public static int GAME_LEVEL;
	public static int GAME_SCORE;
	public static int GAME_LINE;
	public static Calendar GAME_TIME = Calendar.getInstance();

	public static boolean isPaused;
	public static int pauseSelection;
	
	public static boolean GAME_OVER;
	
	public static Object[][] ranking;
	
	public static int INPUT_STATUS;
}
