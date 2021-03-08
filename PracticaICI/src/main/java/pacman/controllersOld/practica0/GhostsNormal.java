package pacman.controllersOld.practica0;

import java.util.EnumMap;
import java.util.Random;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllers.GhostController;
import pacman.game.Game;

public final class GhostsNormal extends GhostController {

	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private MOVE[] allMoves;
	private Random rnd = new Random();
	final static int limit = 20;

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		moves.clear();
		int nodoPacman = game.getPacmanCurrentNodeIndex(), i = 0;
		boolean cerca = false;
		int[] powerPillIndices = game.getActivePowerPillsIndices();
		while(i < powerPillIndices.length && !cerca) {
			if(game.getDistance(nodoPacman, powerPillIndices[i], DM.PATH) < limit) cerca = true;
			i++;
		}
		for (GHOST ghostType : GHOST.values()) {
			if (game.doesGhostRequireAction(ghostType)) {
				int nodoFantasma = game.getGhostCurrentNodeIndex(ghostType);
				if(game.isGhostEdible(ghostType) || cerca) {
					moves.put(ghostType, game.getNextMoveAwayFromTarget(nodoFantasma, nodoPacman, DM.PATH));
				}
				else if(rnd.nextDouble() <= 0.1) {
					allMoves = game.getPossibleMoves(nodoFantasma);
					moves.put(ghostType, allMoves[rnd.nextInt(allMoves.length)]);
				}
				else {
					moves.put(ghostType, game.getNextMoveTowardsTarget(nodoFantasma, nodoPacman, DM.PATH));
				}
			}
		}
		return moves;
	}
}
