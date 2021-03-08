package pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class TransicionComerPPComerG extends Transicion {

	public TransicionComerPPComerG(String id) {
		super(id);
	}

	@Override
	public boolean check(Game game) {
		return UtilsPacMan.isPossibleEat(game)!= null;
	}

	@Override
	public boolean check(Game game, GHOST ghost) {
		return false;
	}

}
