package pacman.controllersOld.practica1;

import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import java.util.Random;

public final class MsPacMan extends PacmanController {
	
	final static int limitToPowerPill = 19;
	final static int limitToGhost = 8;
	final static int ghostToEat = 2;
	final static int limitPills = 20;
	final static int limitTime = 3000;

	@Override
	public MOVE getMove(Game game, long timeDue) {
		if(game.getCurrentLevelTime() == 0) return MOVE.RIGHT;
		int nodePacman = game.getPacmanCurrentNodeIndex();

		// Calculos de fantasmas dentro del l�mite.
		int nearGhost = 0;
		double distanceToNearestGhost = Integer.MAX_VALUE;
		double distanceToNearestGhostEdible = Integer.MAX_VALUE;
		GHOST ghostEdible = null;
		GHOST ghost = null;

		for (GHOST ghostType : GHOST.values()) {
			double distanceToGhost = game.getDistance(nodePacman, game.getGhostCurrentNodeIndex(ghostType), DM.PATH);
				if (distanceToGhost != -1 && distanceToGhost < distanceToNearestGhostEdible && game.isGhostEdible(ghostType)) {
					distanceToNearestGhostEdible = distanceToGhost;
					ghostEdible = ghostType;
				}
				if (distanceToGhost != -1 && distanceToGhost < limitToGhost && distanceToGhost < distanceToNearestGhost && !game.isGhostEdible(ghostType)) {
					nearGhost++;
					distanceToNearestGhost = distanceToGhost;
					ghost = ghostType;
				}
		}
		MOVE mGhostNoEdible = ghost != null ? game.getNextMoveTowardsTarget(nodePacman, game.getGhostCurrentNodeIndex(ghost), DM.PATH) : null;
		@SuppressWarnings("unused")
		MOVE mGhostEdible = ghostEdible != null ? game.getNextMoveTowardsTarget(nodePacman, game.getGhostCurrentNodeIndex(ghostEdible), DM.PATH) : null;

		// Calculo de pill m�s cercana.
		int nearestPill = -1;
		int[] pillIndices = game.getActivePillsIndices();
		double distanceToNearestPill = Integer.MAX_VALUE;
		for (int i = 0; i < pillIndices.length; i++) {
			double distanceToPill = game.getDistance(nodePacman, pillIndices[i], DM.PATH);
			if (distanceToPill != -1 && distanceToPill < distanceToNearestPill) {
				nearestPill = i;
				distanceToNearestPill = distanceToPill;
			}
		}
		@SuppressWarnings("unused")
		MOVE mPill = (nearestPill >= 0 && nearestPill < pillIndices.length) ? game.getNextMoveTowardsTarget(nodePacman, pillIndices[nearestPill], DM.PATH) : null;

		// Calculo de powerPill m�s cercana dentro del l�mite.
		int nearestPowerPill = -1;
		int[] powerPillIndices = game.getActivePowerPillsIndices();
		double distanceToNearestPowerPill = Integer.MAX_VALUE;
		for (int i = 0; i < powerPillIndices.length; i++) {
			double distanceToPowerPill = game.getDistance(nodePacman, powerPillIndices[i], DM.PATH);
			if (distanceToPowerPill != -1 && distanceToPowerPill < limitToPowerPill && distanceToPowerPill < distanceToNearestPowerPill) {
				nearestPowerPill = i;
				distanceToNearestPowerPill = distanceToPowerPill;
			}
		}
		MOVE mPowerPill = (nearestPowerPill >= 0 && nearestPowerPill < powerPillIndices.length) ? game.getNextMoveTowardsTarget(nodePacman, powerPillIndices[nearestPowerPill], DM.PATH) : null;

		// Si no tengo ninguna amenaza y puedo comer.
		if (nearGhost == 0 && ghostEdible != null) {
			return game.getNextMoveTowardsTarget(nodePacman, game.getGhostCurrentNodeIndex(ghostEdible), DM.PATH);
		}

		// Si tengo cerca menos de los que quiero: pill m�s cercana huyendo del fantasma
		// y sin ir a por una powerpill.
		if(game.getCurrentLevelTime() < limitTime)
		{
			if (nearGhost < ghostToEat) {
				// Si no me persigue nadie
				if (nearGhost == 0) {
					// Si no estoy cerca de una powerPill ir a la pill m�s cercana.
					if (mPowerPill == null) {
						if(nearestPill == -1) return mPowerPill;
						else return game.getApproximateNextMoveTowardsTarget(nodePacman, pillIndices[nearestPill], game.getPacmanLastMoveMade(), DM.PATH);
					}
					// Si tengo alguna cerca, ir a la pill m�s cercana que no me lleve a la
					// powerPill, a menos que no exista.
					else {
						nearestPill = -1;
						distanceToNearestPill = Integer.MAX_VALUE;
						for (int i = 0; i < pillIndices.length; i++) {
							double distanceToPill = game.getDistance(nodePacman, pillIndices[i], DM.PATH);
							if (distanceToPill != -1 && distanceToPill < distanceToNearestPill && ((game.getNextMoveTowardsTarget(nodePacman, pillIndices[i], DM.PATH) != mPowerPill)||(game.getActivePillsIndices().length < limitPills))) {
								nearestPill = i;
								distanceToNearestPill = distanceToPill;
							}
						}
						if (nearestPill != -1) {
							return game.getNextMoveTowardsTarget(nodePacman, pillIndices[nearestPill], DM.PATH);
						} else {
							return mPowerPill;
						}
					}
				}
				// Si me persigue alguno, ir a la pill mas cercana evitando fantasma y
				// powerPill, sino existe, ir a por la powerPill.
				else {
					nearestPill = -1;
					distanceToNearestPill = Integer.MAX_VALUE;
					for (int i = 0; i < pillIndices.length; i++) {
						double distanceToPill = game.getDistance(nodePacman, pillIndices[i], DM.PATH);
						if (distanceToPill != -1 && distanceToPill < distanceToNearestPill &&  ((game.getNextMoveTowardsTarget(nodePacman, pillIndices[i], DM.PATH) != mPowerPill)||(game.getActivePillsIndices().length < limitPills)) && game.getNextMoveTowardsTarget(nodePacman, pillIndices[i], DM.PATH) != mGhostNoEdible) {
							nearestPill = i;
							distanceToNearestPill = distanceToPill;
						}
					}
					if (nearestPill != -1) return game.getNextMoveTowardsTarget(nodePacman, pillIndices[nearestPill], DM.PATH);
					else return mPowerPill;
				}
			}

			// Si tengo m�s de los que quiero detr�s, a por la powerPill.
			else {
				nearestPowerPill = -1;
				distanceToNearestPowerPill = Integer.MAX_VALUE;
				for (int i = 0; i < powerPillIndices.length; i++) {
					double distanceToPowerPill = game.getDistance(nodePacman, powerPillIndices[i], DM.PATH);
					if (distanceToPowerPill != -1 && distanceToPowerPill < distanceToNearestPowerPill && game.getNextMoveTowardsTarget(nodePacman, powerPillIndices[i], DM.PATH) != mGhostNoEdible) {
						nearestPowerPill = i;
						distanceToNearestPowerPill = distanceToPowerPill;
					}
				}
				if (nearestPowerPill != -1) return game.getNextMoveTowardsTarget(nodePacman, powerPillIndices[nearestPowerPill], DM.PATH);
				else return game.getNextMoveAwayFromTarget(nodePacman, game.getGhostCurrentNodeIndex(ghost), DM.PATH);
			}
		}
		
		else
		{
			nearestPowerPill = -1;
			distanceToNearestPowerPill = Integer.MAX_VALUE;
			for (int i = 0; i < powerPillIndices.length; i++) 
			{
				double distanceToPowerPill = game.getDistance(nodePacman, powerPillIndices[i], DM.PATH);
				if (distanceToPowerPill != -1 && distanceToPowerPill < distanceToNearestPowerPill && game.getNextMoveTowardsTarget(nodePacman, powerPillIndices[i], DM.PATH) != mGhostNoEdible)
				{
					nearestPowerPill = i;
					distanceToNearestPowerPill = distanceToPowerPill;
				}
			}
			if (nearestPowerPill != -1) return game.getNextMoveTowardsTarget(nodePacman, powerPillIndices[nearestPowerPill], DM.PATH);
			else
			{
				nearestPill = -1;
				distanceToNearestPill = Integer.MAX_VALUE;
				for (int i = 0; i < pillIndices.length; i++) {
					double distanceToPill = game.getDistance(nodePacman, pillIndices[i], DM.PATH);
					if (distanceToPill != -1 && distanceToPill < distanceToNearestPill && game.getNextMoveTowardsTarget(nodePacman, pillIndices[i], DM.PATH) != mGhostNoEdible) {
						nearestPill = i;
						distanceToNearestPill = distanceToPill;
					}
				}
				if(nearestPill != -1)	return game.getNextMoveTowardsTarget(nodePacman, pillIndices[nearestPill], DM.PATH);
				else if(ghost == null) {
					return game.getPossibleMoves(nodePacman)[new Random().nextInt(game.getPossibleMoves(nodePacman).length)];
				}
				else return game.getNextMoveAwayFromTarget(nodePacman, game.getGhostCurrentNodeIndex(ghost),  DM.PATH);
			}
		}
	}

}