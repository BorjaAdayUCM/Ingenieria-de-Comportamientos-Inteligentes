package pacman.controllersOld.practica2.maquinaestadosGhosts;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMRunAway extends FSM{

	protected FSMRunAway(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
	protected void initFSM() {
		this.addTransicion(this.getState("StateSlippingAway"), this.getTransicion("TransitionSlippingAwayToGoForNotEdibleGhost"), this.getState("StateGoForNotEdibleGhost"));
		this.addTransicion(this.getState("StateGoForNotEdibleGhost"), this.getTransicion("TransitionGoForNotEdibleGhostToSlippingAway"), this.getState("StateSlippingAway"));
		this.addTransicion(this.getState("StateSlippingAway"), this.getTransicion("TransitionSlippingAwayToSuicide"), this.getState("StateSuicide"));
		this.addTransicion(this.getState("StateSlippingAway"), this.getTransicion("TransitionSlippingAwayToCoverClosePP"), this.getState("StateCoverClosePP"));
		this.addTransicion(this.getState("StateSuicide"), this.getTransicion("TransitionSuicideToSlippingAway"), this.getState("StateSlippingAway"));
		
		this.addTransicion(this.getState("StateCoverClosePP"), this.getTransicion("TransitionCoverClosePPToSlippingAway"), this.getState("StateSlippingAway"));
		
		this.estadoInicial = this.getState("StateSlippingAway");
		this.estadoActual = this.getState("StateSlippingAway");
		
	}

}
