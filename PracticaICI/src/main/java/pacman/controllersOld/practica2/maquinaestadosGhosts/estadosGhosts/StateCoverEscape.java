package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class StateCoverEscape extends State {

	public StateCoverEscape(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {

		UtilsGhosts.addGhostCoveringEscape(ghost);

		int junctionToAvoid = UtilsGhosts.closestJunctionToChasedPacman(game);
		int junctionToTake = UtilsGhosts.closestJunctionToChasedPacman(game, junctionToAvoid);

		return game.getApproximateNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), junctionToTake,
				game.getGhostLastMoveMade(ghost), DM.PATH);

	}

}
