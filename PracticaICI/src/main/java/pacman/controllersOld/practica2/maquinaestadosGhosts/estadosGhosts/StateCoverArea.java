package pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts;


import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;

public class StateCoverArea extends State{

	public StateCoverArea(String id) {
		super(id);
	}

	@Override
	public MOVE doAction(Game game) {
		return null;
	}

	@Override
	public MOVE doAction(Game game, GHOST ghost) {
		
		return game.getApproximateNextMoveTowardsTarget(
                game.getGhostCurrentNodeIndex(ghost),
                UtilsGhosts.getNearestActivePillIndex(game, ghost),
                game.getGhostLastMoveMade(ghost),
                DM.PATH);
	}

}
