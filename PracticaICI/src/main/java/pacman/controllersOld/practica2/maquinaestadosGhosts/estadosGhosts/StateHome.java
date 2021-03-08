package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;

import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.game.Game;

public class StateHome extends State{

	public StateHome(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		
		return MOVE.NEUTRAL;
	}

}
