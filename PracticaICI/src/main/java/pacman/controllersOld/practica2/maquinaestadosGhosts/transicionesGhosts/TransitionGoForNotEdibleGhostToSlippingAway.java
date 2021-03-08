package pacman.controllersOld.practica2.maquinaestadosGhosts.transicionesGhosts;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class TransitionGoForNotEdibleGhostToSlippingAway extends Transicion {

	public TransitionGoForNotEdibleGhostToSlippingAway(String id) {
		super(id);
	}

	@Override
	protected boolean check(Game game) {
		return false;
	}

	@Override
	protected boolean check(Game game, GHOST ghost) {
		return (!UtilsGhosts.amICloseToAProtectiveGhost(game, ghost) &&
				UtilsGhosts.amIClosestGhostToPacman(game, ghost) && 
				UtilsGhosts.allGhostsAreEdible(game))||
				UtilsGhosts.isGhostInDanger(game, ghost);
	}

}
