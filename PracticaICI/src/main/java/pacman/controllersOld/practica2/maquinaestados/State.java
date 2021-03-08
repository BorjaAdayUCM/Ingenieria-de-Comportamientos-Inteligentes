package pacman.controllersOld.practica2.maquinaestados;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public abstract class State {
	
	protected String ID;
	
	protected State(String id) {
		this.ID = id;
	}
	
	protected String getId() 
	{
		return this.ID;
	}
	
	public abstract MOVE doAction(Game game);
	
	public abstract MOVE doAction(Game game,GHOST ghost);
	
}
