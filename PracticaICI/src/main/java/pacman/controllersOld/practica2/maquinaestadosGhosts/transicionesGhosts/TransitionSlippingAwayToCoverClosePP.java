package pacman.controllersOld.practica2.maquinaestadosGhosts.transicionesGhosts;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class TransitionSlippingAwayToCoverClosePP extends Transicion {

	public TransitionSlippingAwayToCoverClosePP(String id) {
		super(id);
	}

	@Override
	protected boolean check(Game game) {
		return false;
	}

	@Override
	protected boolean check(Game game, GHOST ghost) {
		return !UtilsGhosts.isGhostCloseToPacman(game, ghost) &&
				UtilsGhosts.isTherePPills(game) && 
				UtilsGhosts.isGhostCloseToPPill(game, ghost) && 
				!UtilsGhosts.isPPillCoverYet(game, ghost);
	}

}
