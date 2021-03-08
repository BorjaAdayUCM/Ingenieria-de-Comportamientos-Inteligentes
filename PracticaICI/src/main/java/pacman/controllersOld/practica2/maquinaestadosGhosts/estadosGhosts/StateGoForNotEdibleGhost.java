package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class StateGoForNotEdibleGhost extends State{

	public StateGoForNotEdibleGhost(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		if(UtilsGhosts.getIndexFromClosestNotEdibleGhost(game, ghost)!=-1) {
			return game.getNextMoveTowardsTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				UtilsGhosts.getIndexFromClosestNotEdibleGhost(game, ghost), 
				game.getGhostLastMoveMade(ghost), DM.MANHATTAN);
		}
		return MOVE.NEUTRAL;
	}

}
