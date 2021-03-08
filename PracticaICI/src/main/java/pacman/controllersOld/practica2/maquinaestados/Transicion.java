package pacman.controllersOld.practica2.maquinaestados;

import pacman.game.Constants.GHOST;
import pacman.game.Game;

public abstract class Transicion
{
	
	protected String ID;
	
	protected Transicion(String id) {
		this.ID = id;
	}
	
	protected String getId() 
	{
		return this.ID;
	}
	
	protected abstract boolean check(Game game);
	protected abstract boolean check(Game game,GHOST ghost);
}
