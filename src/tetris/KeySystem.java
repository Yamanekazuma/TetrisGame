package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeySystem implements KeyListener {

	private KeyEvent key;

	public boolean isPressed() {
		return (key != null);
	}
	
	public void clear(){
		key = null;
	}

	public String getKeyChar() {
		String str;
		str = String.valueOf(key.getKeyChar());
		key = null;
		return str;
	}

	public boolean isPressed(int keyCode) {
		if (key != null) {
			if (key.getKeyCode() == keyCode) {
				key = null;
				return true;
			}
		}
		return false;
	}

	public boolean isPressed(int keyCode1, int keyCode2) {
		if (key != null) {
			if (key.getKeyCode() >= keyCode1 && key.getKeyCode() <= keyCode2) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		this.key = arg0;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		this.key = null;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
