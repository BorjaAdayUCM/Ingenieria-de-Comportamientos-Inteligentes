package pacman.controllersOld.practica2;

import pacman.controllers.PacmanController;
import pacman.controllersOld.practica2.maquinaestadosPacMan.HFSMPacman;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public final class MsPacMan extends PacmanController {

	private HFSMPacman hfms;
	
	public MsPacMan()
	{
		super();
		this.hfms = new HFSMPacman();
	}
	
	
	@Override
	public MOVE getMove(Game game, long timeDue)
	{
		return hfms.nextEstado(game).doAction(game);
	}
}