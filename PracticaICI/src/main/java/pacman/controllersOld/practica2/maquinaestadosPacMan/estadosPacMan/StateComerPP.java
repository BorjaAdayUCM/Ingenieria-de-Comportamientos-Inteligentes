package pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class StateComerPP extends State {

	public StateComerPP(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		//Ir a por la ppill mas cercana
		
		MOVE nextMove = null;
		int nodoPacman = game.getPacmanCurrentNodeIndex();
		double distMin = Double.MAX_VALUE;
		for (int nodoPP : game.getActivePowerPillsIndices())
		{ 
			double distToPP = game.getDistance(nodoPacman, nodoPP, DM.PATH);
			MOVE possibleMove = game.getNextMoveTowardsTarget(nodoPacman, nodoPP, DM.PATH);
			if(distToPP < distMin && UtilsPacMan.safeWayForPacMan(possibleMove, game))
			{
				distMin = distToPP;
				nextMove = possibleMove;
			}
		}
		
		//Si no hay ningun movimiento que me lleve a comerme una powerpill de modo seguro hago un move seguro. Si no uno random
		if(nextMove == null) return UtilsPacMan.safeMoveForPacMan(game);
		
		return nextMove;
		
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		return null;
	}

}
