package tetris;

import java.awt.event.KeyEvent;

public abstract class SubSystem {
	protected KeySystem key;
	protected SubSystem nextSystem;
	
	public SubSystem(KeySystem key){
		this.key = key;
	}
	
	public abstract SubSystem system();

}
