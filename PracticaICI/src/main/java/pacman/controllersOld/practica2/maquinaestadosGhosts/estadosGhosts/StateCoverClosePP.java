package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class StateCoverClosePP extends State{

	public StateCoverClosePP(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		int PPIndex = UtilsGhosts.getNearestPPillIndex(game, ghost);
		
		if(UtilsGhosts.isAnyGhostCoveringPP(game, PPIndex))
			return MOVE.NEUTRAL;
		
		return game.getApproximateNextMoveTowardsTarget(
				game.getGhostCurrentNodeIndex(ghost), 
				PPIndex, game.getGhostLastMoveMade(ghost), DM.MANHATTAN);

	}

}
