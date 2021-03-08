package pacman.controllersOld.practica2.maquinaestados;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.GHOST;
import pacman.game.Game;

public abstract class FSM {
	
	protected State estadoActual;
	protected State estadoInicial;
	protected ArrayList<State> listaEstados;
	protected ArrayList<Transicion> listaTransiciones;
	protected HashMap<State, HashMap<Transicion, State>> transiciones;
	
	protected String ID;
	
	protected FSM(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		this.listaEstados = listaEstados;
		this.listaTransiciones = listaTransiciones;
		this.transiciones = new HashMap<State,HashMap<Transicion,State>>();
		this.ID = id;
		this.initFSM();
	}
	
	protected abstract void initFSM();

	protected State getEstadoInicial() {
		return this.estadoInicial;
	}

	protected State getEstadoActual() {
		return this.estadoActual;
	}
	
	protected void resetEstadoActual() {
		this.estadoActual = this.estadoInicial;
	}
	
	protected Object getId() 
	{
		return this.ID;
	}

	protected HashMap<State, HashMap<Transicion, State>> getTransiciones() {
		return transiciones;
	}

	protected State getState(String id) {
		for(State state : listaEstados) {
			if(state.getId().equals(id)) return state;
		}
		return null;
	}

	protected void addEstado(State estado) {
		if(!this.listaEstados.contains(estado)) {
			this.listaEstados.add(estado);
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

	
	protected void addTransicion(State estadoInicial, Transicion in, State estadoFinal) {
		this.addEstado(estadoInicial);
		this.addEstado(estadoFinal);
		
		if(!this.transiciones.containsKey(estadoInicial)) {
			this.transiciones.put(estadoInicial, new HashMap<Transicion, State>());
			this.transiciones.get(estadoInicial).put(in, estadoFinal);
		}
		else {
			this.transiciones.get(estadoInicial).put(in, estadoFinal);
		}
	}

	protected State nextEstado(Game game)
	{
		HashMap<Transicion,State> map = this.transiciones.get(estadoActual);
		if (map != null)
		{
			for (Entry<Transicion, State> entry : map.entrySet())
			{
				if(entry.getKey().check(game)) 
				{
					this.estadoActual=entry.getValue();
					return this.estadoActual; 	
				}
			} 
		}
		return estadoActual;
	}
	
	public State nextEstado(Game game, GHOST ghost)
	{
		HashMap<Transicion,State> map = this.transiciones.get(estadoActual);
		if (map != null)
		{
			for (Entry<Transicion, State> entry : map.entrySet())
			{
				if(entry.getKey().check(game,ghost)) 
				{
					this.estadoActual=entry.getValue();
					return this.estadoActual; 	
				}
			} 
		}
		return estadoActual;
	}
		

}
