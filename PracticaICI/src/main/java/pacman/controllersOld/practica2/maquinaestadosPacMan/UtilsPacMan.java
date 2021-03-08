package pacman.controllersOld.practica2.maquinaestadosPacMan;

import pacman.game.Game;

import java.util.Random;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class UtilsPacMan {
	public static int limitToGhost = 8;
	public static int distancePrudential = 12;
	private static Random rnd = new Random();

	//Devuelve la salida de un tunel.
	public static int exit(MOVE move, Game game) 
	{
		int nodePacman= game.getPacmanCurrentNodeIndex();
		int nextNode = game.getNeighbour(nodePacman, move);
		MOVE lastMove = move;
		while(!game.isJunction(nextNode))
		{
			MOVE nextMove = game.getPossibleMoves(nextNode, lastMove)[0];
			nextNode = game.getNeighbour(nextNode, nextMove);
			lastMove = nextMove;
		}
		return nextNode;
	}
	
	//Devuelve si es seguro entrar en el tunel con una distancia prudencial de distanceToGhost
	public static boolean safeWayForPacMan(MOVE move, Game game) {
		boolean caminoSeguro = true;
		int nodeSalida = exit(move, game);
		int nodePacMan = game.getPacmanCurrentNodeIndex();
		double distancePacManToExit = game.getDistance(nodePacMan, nodeSalida, DM.PATH);
		for (GHOST ghostType : GHOST.values()) {
			double distanceGhostToExit = game.getDistance(game.getGhostCurrentNodeIndex(ghostType), nodeSalida, DM.PATH);
			//Comprobar tiempo que le queda edible
			if (distanceGhostToExit != -1 && 
			(!game.isGhostEdible(ghostType) || game.getGhostEdibleTime(ghostType) < game.getDistance(nodePacMan, game.getGhostCurrentNodeIndex(ghostType), DM.PATH))
			&& distanceGhostToExit <= distancePacManToExit + UtilsPacMan.distancePrudential) caminoSeguro = false;
		}
		return caminoSeguro;
	}

	
	//Comprueba si todos los ghost estan en la carcel
	public static Boolean allGhostInJails (Game game)
	{
		boolean allInJails = true;
		for (GHOST ghostType : GHOST.values())
		{
			if(game.getGhostLairTime(ghostType) == 0) allInJails = false;
		}
		return allInJails;
	}
	
	public static MOVE safeMoveForPacMan(MOVE move, Game game) {
		MOVE[] possibleMoves;
		if(safeWayForPacMan(move, game)) return move;
		else {
			int nodePacMan = game.getPacmanCurrentNodeIndex();
			MOVE lastMoveMade = game.getPacmanLastMoveMade();
			possibleMoves = game.getPossibleMoves(nodePacMan, lastMoveMade);
			for(MOVE moveCheck : possibleMoves) {
				if(safeWayForPacMan(moveCheck, game)) return moveCheck;
			}
		}
		return possibleMoves[rnd.nextInt(possibleMoves.length)];
	}
	
	public static MOVE safeMoveForPacMan( Game game) {
		MOVE[] possibleMoves;
		int nodePacMan = game.getPacmanCurrentNodeIndex();
		MOVE lastMoveMade = game.getPacmanLastMoveMade();
		possibleMoves = game.getPossibleMoves(nodePacMan, lastMoveMade);
		for(MOVE moveCheck : possibleMoves) {
			if(safeWayForPacMan(moveCheck, game)) return moveCheck;
		}
		return possibleMoves[rnd.nextInt(possibleMoves.length)];
	}

	//Devuelve true si no hay fantasma cercano y hay alguno comestible.
	public static boolean poweredSafe(Game game) {
		return isNearGhost(game) == null && (game.isGhostEdible(GHOST.BLINKY) || game.isGhostEdible(GHOST.INKY) || game.isGhostEdible(GHOST.PINKY) || game.isGhostEdible(GHOST.SUE));
	}

	//Devuelvo -1 si hay un fantasma mas cerca que yo a la powerpill mas cercana, si no devuelvo el nodo de la powerpill a la que ir.
	public static int canEatPP(Game game) {
		int nearestPowerPill = -1, solution = -1;
		int[] powerPillIndices = game.getActivePowerPillsIndices();
		double distanceToNearestPowerPill = Integer.MAX_VALUE;
		for (int i = 0; i < powerPillIndices.length; i++) {
			double distanceToPowerPill = game.getDistance(game.getPacmanCurrentNodeIndex(), powerPillIndices[i], DM.PATH);
			if (distanceToPowerPill != -1 && distanceToPowerPill < distanceToNearestPowerPill) {
				nearestPowerPill = i;
				distanceToNearestPowerPill = distanceToPowerPill;
			}
		}


		double distanceToNearestPowerPillGhost = Integer.MAX_VALUE;
		for (GHOST ghostType : GHOST.values()) {
			double distanceToPowerPillGhost = game.getDistance(game.getGhostCurrentNodeIndex(ghostType), powerPillIndices[nearestPowerPill], DM.PATH);
			if (distanceToPowerPillGhost != -1 && distanceToPowerPillGhost < distanceToNearestPowerPillGhost) {
				distanceToNearestPowerPillGhost = distanceToPowerPillGhost;
			}
		}
		if (distanceToNearestPowerPillGhost > distanceToNearestPowerPill) solution = powerPillIndices[nearestPowerPill];
		return solution;
	}

	//Devuelve si quedan powerPills
	public static boolean powerPills(Game game) {
		return game.getActivePowerPillsIndices().length > 0;
	}

	//Devuelve si hay un fantasma amenazante en una distancia.
	public static GHOST isNearGhost(Game game) {
		int nodePacman = game.getPacmanCurrentNodeIndex();
		double distanceToNearestGhost = Integer.MAX_VALUE;
		GHOST ghost = null;

		for (GHOST ghostType : GHOST.values()) {
			double distanceToGhost = game.getDistance(nodePacman, game.getGhostCurrentNodeIndex(ghostType), DM.PATH);
			if (distanceToGhost != -1 && distanceToGhost < limitToGhost && distanceToGhost < distanceToNearestGhost && !game.isGhostEdible(ghostType)) {
				distanceToNearestGhost = distanceToGhost;
				ghost = ghostType;
			}
		}
		return ghost;
	}

	//Devuelve el fantasma que me puedo comer en el tiempo que me queda de power.
	public static GHOST isPossibleEat(Game game) {
		int nodePacman = game.getPacmanCurrentNodeIndex();
		double distanceToNearestGhost = Integer.MAX_VALUE;
		GHOST ghost = null;

		for (GHOST ghostType : GHOST.values()) {
			double distanceToGhost = game.getDistance(nodePacman, game.getGhostCurrentNodeIndex(ghostType), DM.PATH);
			if (distanceToGhost != -1 && game.isGhostEdible(ghostType) && distanceToGhost < game.getGhostEdibleTime(ghostType) * 2 && distanceToGhost < distanceToNearestGhost) //falta comprobar que ese movimiento sea seguro puede provocar suicidios
			{
				distanceToNearestGhost = distanceToGhost;
				ghost = ghostType;
			}
		}
		return ghost;
	}
}
