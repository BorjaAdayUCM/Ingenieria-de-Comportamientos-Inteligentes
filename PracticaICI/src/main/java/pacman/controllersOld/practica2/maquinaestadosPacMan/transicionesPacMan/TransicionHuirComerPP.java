package pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan;

import pacman.game.Constants.GHOST;
import pacman.controllersOld.practica2.maquinaestados.Transicion;
import pacman.controllersOld.practica2.maquinaestadosPacMan.UtilsPacMan;
import pacman.game.Game;

public class TransicionHuirComerPP extends Transicion {

	public TransicionHuirComerPP(String id) {
		super(id);
	}

	@Override
	public boolean check(Game game) {
		return UtilsPacMan.powerPills(game)&& UtilsPacMan.canEatPP(game) != -1 ;
	}

	@Override
	public boolean check(Game game, GHOST ghost) {
		return false;
	}

}
