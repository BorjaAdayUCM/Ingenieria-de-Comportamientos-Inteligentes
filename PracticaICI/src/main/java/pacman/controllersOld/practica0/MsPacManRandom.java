package pacman.controllersOld.practica0;

import java.util.Random;

import pacman.game.Constants.MOVE;
import pacman.controllers.PacmanController;
import pacman.game.Game;

public final class MsPacManRandom extends PacmanController {

	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		return allMoves[rnd.nextInt(allMoves.length)];
	}

}
