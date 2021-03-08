package pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class TransicionAllGhostInJail extends Transicion {

	public TransicionAllGhostInJail(String id) {
		super(id);
	}

	@Override
	public boolean check(Game game) {
		return UtilsPacMan.allGhostInJails(game);
	}

	@Override
	public boolean check(Game game, GHOST ghost) {
		return false;
	}

}
