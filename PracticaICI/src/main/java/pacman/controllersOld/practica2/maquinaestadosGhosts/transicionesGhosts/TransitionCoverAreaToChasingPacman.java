package pacman.controllersOld.practica2.maquinaestadosGhosts.transicionesGhosts;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class TransitionCoverAreaToChasingPacman extends Transicion {

	public TransitionCoverAreaToChasingPacman(String id) {
		super(id);
	}

	@Override
	protected boolean check(Game game) {
		return false;
	}

	@Override
	protected boolean check(Game game, GHOST ghost) {
		return UtilsGhosts.isGhostCloseToPacman(game, ghost) && 
				!UtilsGhosts.amIEdible(game, ghost);
	}

}
