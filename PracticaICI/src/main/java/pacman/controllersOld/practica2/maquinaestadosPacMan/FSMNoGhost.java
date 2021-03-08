package pacman.controllersOld.practica2.maquinaestadosPacMan;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMNoGhost extends FSM {

	public FSMNoGhost(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
	protected void initFSM() {
		//Inicializaci�n de la m�quina con un solo estado.
		this.estadoActual = this.getState("ComerP");
		this.estadoInicial = this.getState("ComerP");
	}
}
