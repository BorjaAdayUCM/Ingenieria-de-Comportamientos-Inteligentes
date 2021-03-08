package pacman.controllersOld.practica2.maquinaestadosGhosts;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMChasingPacman extends FSM{

	protected FSMChasingPacman(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
	protected void initFSM() {
		
		this.addTransicion(this.getState("StateNormalChasing"), this.getTransicion("TransitionNormalChasingToCoverEscape"), this.getState("StateCoverEscape"));
		this.addTransicion(this.getState("StateCoverEscape"), this.getTransicion("TransitionCoverEscapeToNormalChasing"), this.getState("StateNormalChasing"));
		
		this.estadoInicial = this.getState("StateNormalChasing");
		this.estadoActual = this.getState("StateNormalChasing");
	}

}
