package pacman.controllersOld.practica2.maquinaestadosGhosts;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMCoverArea extends FSM{

	protected FSMCoverArea(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
	protected void initFSM() {
		this.estadoInicial = this.getState("StateCoverArea");
		this.estadoActual = this.getState("StateCoverArea");
	}

}
