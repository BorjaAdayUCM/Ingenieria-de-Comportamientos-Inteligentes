package pacman.controllersOld.practica2;

import pacman.controllers.GhostController;
import pacman.controllersOld.practica2.maquinaestadosGhosts.HFSMGhosts;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.EnumMap;


public final class Ghosts extends GhostController{

	private HFSMGhosts gHFSM1;
	private HFSMGhosts gHFSM2;
	private HFSMGhosts gHFSM3;
	private HFSMGhosts gHFSM4;
	
	private EnumMap<GHOST,MOVE> moves = new EnumMap<GHOST,MOVE>(GHOST.class);

	public Ghosts(){
		super();
		this.gHFSM1 = new HFSMGhosts();
		this.gHFSM2 = new HFSMGhosts();
		this.gHFSM3 = new HFSMGhosts();
		this.gHFSM4 = new HFSMGhosts();
	}
	
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {
		
		GHOST[] ghosts = GHOST.values();
		
		moves.put(ghosts[0], gHFSM1.nextEstado(game, ghosts[0]).doAction(game, ghosts[0]));
		moves.put(ghosts[1], gHFSM2.nextEstado(game, ghosts[1]).doAction(game, ghosts[1]));
		moves.put(ghosts[2], gHFSM3.nextEstado(game, ghosts[2]).doAction(game, ghosts[2]));
		moves.put(ghosts[3], gHFSM4.nextEstado(game, ghosts[3]).doAction(game, ghosts[3]));
		
		
		/*for(GHOST ghostType:GHOST.values()) {
			
			/*if(!game.doesGhostRequireAction(ghostType)) {
				moves.put(ghostType, MOVE.NEUTRAL);
			}else {*/
				//me esta devolviendo un null
				/*crear un estado estar en casa donde el if de arriba 
				 * se meta en ese estado para que cuando este en casa solo
				 * tenga moves neutral y falta dibujar */
				//State s = gHFSM.nextEstado(game, ghostType);
				//System.out.println(ghostType.name());
				//System.out.println(s.toString());
				//MOVE mov = s.doAction(game, ghostType);
				
				//System.out.println(mov.toString());
				//MOVE m = gHFSM.nextEstado(game, ghostsType).doAction(game,ghostsType);
				//moves.put(ghostType,mov );
			//}
		//}
		
		return moves;
	}

}