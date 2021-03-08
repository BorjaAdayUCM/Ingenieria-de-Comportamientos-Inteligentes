package pacman.controllersOld.practica0;

import java.util.EnumMap;
import pacman.controllers.GhostController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostsAggresive extends GhostController {
	
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		int nodeTo = game.getPacmanCurrentNodeIndex();
		for (GHOST ghostType : GHOST.values()) {
			int nodeFrom = game.getGhostCurrentNodeIndex(ghostType);
			moves.put(ghostType, game.getApproximateNextMoveTowardsTarget(nodeFrom, nodeTo, game.getGhostLastMoveMade(ghostType), DM.PATH));
		}
		return moves;
	}

	
}
