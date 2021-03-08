package pacman.controllersOld.practica2.maquinaestadosGhosts;

import pacman.controllersOld.practica2.maquinaestados.HFSM;
import pacman.controllersOld.practica2.maquinaestadosGhosts.estadosGhosts.*;
import pacman.controllersOld.practica2.maquinaestadosGhosts.transicionesGhosts.*;

public class HFSMGhosts extends HFSM{

	@Override
	protected void initHFSM() {
		/*Adding States */
		//FSMHome state
		this.addState(new StateHome("StateHome"));
		//FSMCoverArea state
		this.addState(new StateCoverArea("StateCoverArea"));
		//FSMChasingPacman states
		this.addState(new StateNormalChasing("StateNormalChasing"));
		this.addState(new StateCoverEscape("StateCoverEscape"));
		//FSMRunAway states
		this.addState(new StateCoverClosePP("StateCoverClosePP"));
		this.addState(new StateGoForNotEdibleGhost("StateGoForNotEdibleGhost"));
		this.addState(new StateSuicide("StateSuicide"));
		this.addState(new StateSlippingAway("StateSlippingAway"));
		
		/*FSMs Transitions*/
		//FSMChasingPacman transitions
		this.addTransicion(new TransitionNormalChasingToCoverEscape("TransitionNormalChasingToCoverEscape"));
		this.addTransicion(new TransitionCoverEscapeToNormalChasing("TransitionCoverEscapeToNormalChasing"));
		//FSMRunAway transitions
		this.addTransicion(new TransitionSlippingAwayToGoForNotEdibleGhost("TransitionSlippingAwayToGoForNotEdibleGhost"));
		this.addTransicion(new TransitionGoForNotEdibleGhostToSlippingAway("TransitionGoForNotEdibleGhostToSlippingAway"));
		this.addTransicion(new TransitionSlippingAwayToSuicide("TransitionSlippingAwayToSuicide"));
		this.addTransicion(new TransitionSlippingAwayToCoverClosePP("TransitionSlippingAwayToCoverClosePP"));
		this.addTransicion(new TransitionSuicideToSlippingAway("TransitionSuicideToSlippingAway"));
		
		this.addTransicion(new TransitionCoverClosePPToSlippingAway("TransitionCoverClosePPToSlippingAway"));
		
		this.addFSM(new FSMChasingPacman(this.listaEstados, this.listaTransiciones, "FSMChasingPacman"));
		this.addFSM(new FSMCoverArea(this.listaEstados, this.listaTransiciones, "FSMCoverArea"));
		this.addFSM(new FSMHome(this.listaEstados, this.listaTransiciones, "FSMHome"));
		this.addFSM(new FSMRunAway(this.listaEstados, this.listaTransiciones, "FSMRunAway"));
		
		/*HFSM transitions names*/
		this.addTransicion(new TransitionHomeToChasingPacman("TransitionHomeToChasingPacman"));
		this.addTransicion(new TransitionChasingPacmanToRunAway("TransitionChasingPacmanToRunAway"));
		this.addTransicion(new TransitionRunAwayToChasingPacman("TransitionRunAwayToChasingPacman"));
		this.addTransicion(new TransitionChasingPacmanToCoverArea("TransitionChasingPacmanToCoverArea"));
		this.addTransicion(new TransitionCoverAreaToChasingPacman("TransitionCoverAreaToChasingPacman"));
		this.addTransicion(new TransitionCoverAreaToRunAway("TransitionCoverAreaToRunAway"));
		this.addTransicion(new TransitionRunAwayToHome("TransitionRunAwayToHome"));
		
		this.addTransicion(new TransitionRunAwayToCoverArea("TransitionRunAwayToCoverArea"));
		
		/*HFSM transitions connections*/
		this.addTransicion(this.getFSM("FSMHome"), this.getTransicion("TransitionHomeToChasingPacman"), this.getFSM("FSMChasingPacman"));
		this.addTransicion(this.getFSM("FSMChasingPacman"), this.getTransicion("TransitionChasingPacmanToRunAway"), this.getFSM("FSMRunAway"));
		this.addTransicion(this.getFSM("FSMRunAway"), this.getTransicion("TransitionRunAwayToChasingPacman"), this.getFSM("FSMChasingPacman"));
		this.addTransicion(this.getFSM("FSMChasingPacman"), this.getTransicion("TransitionChasingPacmanToCoverArea"), this.getFSM("FSMCoverArea"));
		this.addTransicion(this.getFSM("FSMCoverArea"), this.getTransicion("TransitionCoverAreaToChasingPacman"), this.getFSM("FSMChasingPacman"));
		this.addTransicion(this.getFSM("FSMCoverArea"), this.getTransicion("TransitionCoverAreaToRunAway"), this.getFSM("FSMRunAway"));
		this.addTransicion(this.getFSM("FSMRunAway"), this.getTransicion("TransitionRunAwayToHome"), this.getFSM("FSMHome"));
		this.addTransicion(this.getFSM("FSMRunAway"), this.getTransicion("TransitionRunAwayToCoverArea"), this.getFSM("FSMCoverArea"));
		
		/*States for Starting HFSMs*/
		this.FSMInicial = this.getFSM("FSMHome");
		this.FSMActual = this.getFSM("FSMHome");
		
	}

}
