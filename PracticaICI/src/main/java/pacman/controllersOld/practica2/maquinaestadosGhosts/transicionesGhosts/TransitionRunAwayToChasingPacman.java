package pacman.controllersOld.practica2.maquinaestadosGhosts.transicionesGhosts;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class TransitionRunAwayToChasingPacman extends Transicion {

	public TransitionRunAwayToChasingPacman(String id) {
		super(id);
	}

	@Override
	protected boolean check(Game game) {
		return false;
	}

	@Override
	protected boolean check(Game game, GHOST ghost) {
		return UtilsGhosts.amITheSuicideGhost(game, ghost)||  
				(!UtilsGhosts.amIEdible(game, ghost) && 
				UtilsGhosts.isGhostCloseToPacman(game, ghost));
	}

}
