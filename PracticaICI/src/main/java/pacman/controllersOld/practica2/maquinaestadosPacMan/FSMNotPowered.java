package pacman.controllersOld.practica2.maquinaestadosPacMan;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMNotPowered  extends FSM
{

    public FSMNotPowered(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
    protected void initFSM()
    {
		
		//A�adidos estados, transiciones y tabla de transiciones a la m�quina FSMNotPowered.
        this.addTransicion(this.getState("Huir"), this.getTransicion("TransicionHuirComerPP"), this.getState("ComerPP"));
        this.addTransicion(this.getState("Huir"), this.getTransicion("TransicionHuirComerP"), this.getState("ComerP"));
        this.addTransicion(this.getState("ComerPP"), this.getTransicion("TransicionHuirComerPP"), this.getState("ComerP"));
        this.addTransicion(this.getState("ComerPP"), this.getTransicion("TransicionIsNearGhost"), this.getState("Huir"));
        this.addTransicion(this.getState("ComerP"), this.getTransicion("TransicionIsNearGhost"), this.getState("Huir"));
        
        this.estadoInicial = this.getState("Huir");
        this.estadoActual = this.getState("Huir");
    }
	
}
