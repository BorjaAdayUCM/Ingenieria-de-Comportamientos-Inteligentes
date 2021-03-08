package pacman.controllersOld.practica0;

import java.util.Random;

import pacman.controllers.PacmanController;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

public final class MsPacManFitRandom extends PacmanController {

	private Random rnd = new Random();
	private MOVE[] allMovesPossible;
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		allMovesPossible = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
		return allMovesPossible[rnd.nextInt(allMovesPossible.length)];
	}

}