package pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class StateHuir extends State{

	public StateHuir(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		//Huir del fantasma noEdible mas cercano
		MOVE nextMove = null;
		int nodoPacman = game.getPacmanCurrentNodeIndex();
		double distMin = Double.MAX_VALUE;
		for (GHOST ghostType : GHOST.values())
		{
			if(!game.isGhostEdible(ghostType))
			{
				int nodoGhost = game.getGhostCurrentNodeIndex(ghostType);
				double distToGhost = game.getDistance(nodoPacman, nodoGhost, DM.PATH);
				MOVE possibleMove = game.getNextMoveAwayFromTarget(nodoPacman, nodoGhost, DM.PATH);
				if(distToGhost < distMin && UtilsPacMan.safeWayForPacMan(possibleMove, game))
				{
					distMin = distToGhost;
					nextMove = possibleMove;
				}
			}
		}
		//Si no hay ningun movimiento que me lleve a comerme un Ghost de modo seguro hago un move seguro.
		if(nextMove == null) return UtilsPacMan.safeMoveForPacMan(game);
		
		return nextMove;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		return null;
	}

}
