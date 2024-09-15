package tetris;

import java.awt.event.KeyEvent;

public class TitleSystem extends SubSystem {
	

	public TitleSystem(KeySystem key) {
		super(key);
		CommonField.gameMode = 0;
		CommonField.gameType = 0;
	}

	@Override
	public SubSystem system() {
		if (key.isPressed(KeyEvent.VK_DOWN)) {
			if (CommonField.gameMode < 2)
				CommonField.gameMode++;
		}
		if (key.isPressed(KeyEvent.VK_UP)) {
			if (CommonField.gameMode > 0)
				CommonField.gameMode--;
		}
		if(CommonField.gameMode == 0 && key.isPressed(KeyEvent.VK_RIGHT)){
			if(CommonField.gameType < SGameWindow.GAME_TYPE_NAME.length-1){
				CommonField.gameType++;
			}
		}
		if(CommonField.gameMode == 0 && key.isPressed(KeyEvent.VK_LEFT)){
			if(CommonField.gameType > 0){
				CommonField.gameType--;
			}
		}

		if (key.isPressed(KeyEvent.VK_SPACE) || key.isPressed(KeyEvent.VK_ENTER)) {
			switch(CommonField.gameMode){
			case CommonField.GAME_MARATHON:
				CommonField.window = BaseWindow.SGAME_WINDOW;
				break;
			case CommonField.GAME_VS_COM:
				CommonField.window = BaseWindow.VSCOM_WINDOW;
				break;
			case CommonField.GAME_VS_PLAYER:
				CommonField.window = BaseWindow.VS2PL_WINDOW;
				break;
			}
		}
		
		return nextSystem;
	}

}
