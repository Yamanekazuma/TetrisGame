package tetris;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RankingSystem extends SubSystem {
	public static final int RANK = 0;
	public static final int NAME = 1;
	public static final int SCORE = 2;
	public static final int LEVEL = 3;
	public static final int LINE = 4;
	public static final int TIME = 5;

	String name;
	String viewName;
	String filename;
	File file;
	int orderKey;
	int subOrderKey;
	Object[] thisRecord;
	Object[][] rankingRecord;
	int cnt;
	boolean nameChecked;
	SimpleDateFormat sdf;
	int myData;

	public RankingSystem(KeySystem key) {
		super(key);

		nameChecked = false;

		setSorce();
		setData();
		if (cnt < 9 || compare(thisRecord, rankingRecord[cnt])) {
			updateData();
			CommonField.window = BaseWindow.REGISTER_WINDOW;
		} else {
			CommonField.ranking = rankingRecord;
		}
	}

	private void updateData() {
		Object[][] updatedRecord = new Object[cnt > 8 ? 10 : cnt + 2][6];
		int i;
		i = 0;

		while (updatedRecord.length != 1 && compare(rankingRecord[i], thisRecord)) {
			updatedRecord[i] = rankingRecord[i];
			i++;
		}
		if (i == 0) {
			thisRecord[0] = 1;
		} else {
			if (!(compare(rankingRecord[i], thisRecord) || compare(thisRecord, rankingRecord[i]))) {
				thisRecord[0] = (int) rankingRecord[i][0];
			} else {
				thisRecord[0] = (int) updatedRecord[i - 1][0] + 1;
			}
		}

		updatedRecord[i] = thisRecord;
		myData = i;
		i++;
		while (i < updatedRecord.length) {
			updatedRecord[i] = rankingRecord[i - 1];
			if ((compare(thisRecord, updatedRecord[i]) || compare(updatedRecord[i], thisRecord))) {
				updatedRecord[i][0] = (int) updatedRecord[i][0] + 1;
			}
			i++;
		}

		CommonField.ranking = updatedRecord;
		
		writeToFile(updatedRecord);
	}

	// a‚Æb‚ð”ä‚×‚Äa‚Ì•û‚ª‡ˆÊ‚ª—Ç‚¯‚ê‚ÎTRUE
	// Š®‘S‚É“¯‚¶ê‡‚ÍFALSE
	private boolean compare(Object[] a, Object[] b) {
		if (a[orderKey] == null || a[subOrderKey] == null) {
			return false;
		}
		if (b[orderKey] == null || b[subOrderKey] == null) {
			return true;
		}
		if (orderKey == TIME) {
			if (((String) a[orderKey]).compareTo((String) b[orderKey]) < 0) {
				return true;
			} else if (((String) a[orderKey]).compareTo(((String) b[orderKey])) == 0) {
				if ((int) a[subOrderKey] > (int) b[subOrderKey]) {
					return true;
				}
			}
		} else {
			if ((int) a[orderKey] > (int) b[orderKey]) {
				return true;
			} else if ((int) a[orderKey] == (int) b[orderKey]) {
				if (subOrderKey == TIME) {
					if (((String) a[orderKey]).compareTo((String) b[orderKey]) < 0) {
						return true;
					}
				} else {
					if ((int) a[subOrderKey] > (int) b[subOrderKey]) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void setData() {
		sdf = new SimpleDateFormat("mm:ss.SSS");
		name = "";
		rankingRecord = new Object[10][6];
		thisRecord = new Object[6];
		thisRecord[0] = 0;
		thisRecord[1] = name;
		thisRecord[2] = CommonField.GAME_SCORE;
		thisRecord[3] = CommonField.GAME_LEVEL;
		thisRecord[4] = CommonField.GAME_LINE;
		thisRecord[5] = sdf.format(CommonField.GAME_TIME.getTime());

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			String[] record;
			cnt = -1;
			while ((line = br.readLine()) != null) {
				record = line.split(",", 6);
				for (int i = 0; i < record.length; i++) {
					if (i == 1 || i == 5) {
						rankingRecord[cnt + 1][i] = record[i];
					} else {
						rankingRecord[cnt + 1][i] = Integer.parseInt(record[i]);
					}
				}
				cnt++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setSorce() {
		filename = "src/tetris/data/";
		switch (CommonField.gameType) {
		case CommonField.TGAME_ENDLESS:
			filename = filename + "endless.csv";
			orderKey = SCORE;
			subOrderKey = LEVEL;
			break;
		case CommonField.TGAME_TIME_ATTACK:
			filename = filename + "ta.csv";
			orderKey = TIME;
			subOrderKey = SCORE;
			break;
		case CommonField.TGAME_SCORE_ATTACK:
			filename = filename + "sa.csv";
			orderKey = SCORE;
			subOrderKey = TIME;
			break;
		case CommonField.TGAME_HALF_TA:
			filename = filename + "h-ta.csv";
			orderKey = TIME;
			subOrderKey = SCORE;
			break;
		case CommonField.TGAME_HALF_SA:
			filename = filename + "h-sa.csv";
			orderKey = SCORE;
			subOrderKey = TIME;
			break;
		}
		file = new File(filename);
	}
	
	private void writeToFile(Object[][] record){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String str;
			
			for(int y=0;y < record.length;y++){
				str = "";
				for(int x=0;x < record[0].length;x++){
					if(x!=0){
						str = str+",";
					}
					str = str + record[y][x].toString();
				}
				bw.write(str);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public SubSystem system() {
		if (CommonField.window == BaseWindow.RANKING_WINDOW) {
			CommonField.INPUT_STATUS = RankingWindow.RANKING_VIEW;
			if (key.isPressed(KeyEvent.VK_ENTER) || key.isPressed(KeyEvent.VK_SPACE)) {
				CommonField.window = BaseWindow.TITLE_WINDOW;
			}
		} else {
			if (!nameChecked) {
				CommonField.INPUT_STATUS = RankingWindow.INPUT_NAME;
				if (key.isPressed()) {
					if (key.isPressed(KeyEvent.VK_0, KeyEvent.VK_9) || key.isPressed(KeyEvent.VK_A, KeyEvent.VK_Z)) {
						name = name + key.getKeyChar();
					} else if (name.length() > 0 && key.isPressed(KeyEvent.VK_BACK_SPACE)) {
						name = name.substring(0, name.length() - 1);
					} else if (key.isPressed(KeyEvent.VK_SPACE)) {
						name = name + " ";
					} else if (key.isPressed(KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD9)) {
						name = name + key.getKeyChar();
					} else if (key.isPressed(KeyEvent.VK_ENTER)) {
						nameChecked = true;
					}
				}
				if (name.length() < CommonField.NAME_SPACE) {
					CommonField.ranking[myData][1] = name + "_";
				} else {
					CommonField.ranking[myData][1] = (name=name.substring(0, CommonField.NAME_SPACE));
				}
			} else {
				CommonField.INPUT_STATUS = RankingWindow.CHECK_NAME;
				if(name.length() != 5){
					CommonField.ranking[myData][1] = name;
				}
				if (key.isPressed(KeyEvent.VK_ENTER)) {
					writeToFile(CommonField.ranking);
					CommonField.window = BaseWindow.RANKING_WINDOW;
				} else if(key.isPressed(KeyEvent.VK_BACK_SPACE)){
					nameChecked = false;
				}
			}
		}
		return null;
	}

}
