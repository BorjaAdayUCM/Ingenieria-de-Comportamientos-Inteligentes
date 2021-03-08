package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class StateSlippingAway extends State{

	public StateSlippingAway(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		
		if(!UtilsGhosts.allGhostsAreEdible(game)) {
			UtilsGhosts.resetNumSuicideGhost();
		}
		
		return game.getApproximateNextMoveAwayFromTarget(
				game.getGhostCurrentNodeIndex(ghost),
				game.getPacmanCurrentNodeIndex(), 
				game.getGhostLastMoveMade(ghost), DM.MANHATTAN);
	}

}
