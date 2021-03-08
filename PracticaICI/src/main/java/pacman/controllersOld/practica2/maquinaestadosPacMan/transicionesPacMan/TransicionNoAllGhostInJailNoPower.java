package pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class TransicionNoAllGhostInJailNoPower extends Transicion{

	public TransicionNoAllGhostInJailNoPower(String id) {
		super(id);
	}

	@Override
	public boolean check(Game game) {
		return !UtilsPacMan.allGhostInJails(game) && !UtilsPacMan.poweredSafe(game);
	}

	@Override
	public boolean check(Game game, GHOST ghost) {
		return false;
	}

}
