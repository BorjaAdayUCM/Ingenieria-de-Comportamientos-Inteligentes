package pacman.controllersOld.practica2.maquinaestados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.GHOST;
import pacman.game.Game;

public abstract class HFSM {
	
	protected FSM FSMInicial;
	protected FSM FSMActual;
	protected ArrayList<FSM> listaFSMs;
	protected ArrayList<State> listaEstados;
	protected ArrayList<Transicion> listaTransiciones;
	protected HashMap<FSM, HashMap<Transicion, FSM>> transiciones;
	
	
	
	protected HFSM() {
		this.listaEstados = new ArrayList<State>();
		this.listaFSMs = new ArrayList<FSM>();
		this.listaTransiciones = new ArrayList<Transicion>();
		this.transiciones = new HashMap<FSM,HashMap<Transicion,FSM>>();
		this.initHFSM();
	}
	
	protected abstract void initHFSM();

	protected FSM getFSMInicial() {
		return this.FSMInicial;
	}

	protected FSM getFSMActual() {
		return this.FSMActual;
	}
	
	protected FSM getFSM(String id) {
		for(FSM fsm : this.listaFSMs) {
			if(id.equals(fsm.getId())) return fsm;
		}
		return null;
	}
	
	protected void addState(State estado) {
		if(!listaEstados.contains(estado)) {
			this.listaEstados.add(estado);
		}
	}

	protected void addFSM(FSM fsm) {
		if (!listaFSMs.contains(fsm)) {
			this.listaFSMs.add(fsm);
		}
	}
	
	protected void addTransicion(Transicion transicion) {
		if(!this.listaTransiciones.contains(transicion)) {
			this.listaTransiciones.add(transicion);
		}
	}
	
	protected Transicion getTransicion(String id) {
		for(Transicion transicion : this.listaTransiciones) {
			if(transicion.getId().equals(id)) return transicion;
		}
		return null;
	}


	protected void addTransicion(FSM FSMInicial, Transicion in, FSM FSMFinal) {
		this.addFSM(FSMInicial);
		this.addFSM(FSMFinal);
		if(!this.transiciones.containsKey(FSMInicial)) {
			this.transiciones.put(FSMInicial, new HashMap<Transicion, FSM>());
			this.transiciones.get(FSMInicial).put(in, FSMFinal);
		}
		else {
			this.transiciones.get(FSMInicial).put(in, FSMFinal);
		}
	}


	public State nextEstado(Game game)
	{
		HashMap<Transicion,FSM> map = this.transiciones.get(FSMActual);
		for (Entry<Transicion, FSM> entry : map.entrySet())
		{
			if(entry.getKey().check(game))
			{
				this.FSMActual = entry.getValue();
				this.FSMActual.resetEstadoActual();
				return this.FSMActual.estadoActual; 	
			}
		}
		return  this.FSMActual.nextEstado(game);
	}
	
	public State nextEstado(Game game,GHOST ghost)
	{
		HashMap<Transicion,FSM> map = this.transiciones.get(FSMActual);
		for (Entry<Transicion, FSM> entry : map.entrySet())
		{
			if(entry.getKey().check(game,ghost))
			{
				this.FSMActual = entry.getValue();
				this.FSMActual.resetEstadoActual();
				return this.FSMActual.estadoActual; 	
			}
		}
		return this.FSMActual.nextEstado(game,ghost);
	}
	
}
