package pacman.controllersOld.practica2.maquinaestadosPacMan;

import java.util.ArrayList;

import pacman.controllersOld.practica2.maquinaestados.FSM;
import pacman.controllersOld.practica2.maquinaestados.State;
import pacman.controllersOld.practica2.maquinaestados.Transicion;

public class FSMPowered  extends FSM
{

    public FSMPowered(ArrayList<State> listaEstados, ArrayList<Transicion> listaTransiciones, String id) {
		super(listaEstados, listaTransiciones, id);
	}

	@Override
    protected void initFSM()
    {
		//A�adidos estados, transiciones y tabla de transiciones a la m�quina FSMPowered.
		this.addTransicion(this.getState("ComerG"), this.getTransicion("TransicionComerGComerPP"), this.getState("ComerPP"));
        this.addTransicion(this.getState("ComerG"), this.getTransicion("TransicionComerGComerP"), this.getState("ComerP"));
        this.addTransicion(this.getState("ComerPP"), this.getTransicion("TransicionComerPPComerG"), this.getState("ComerG"));
        
        this.estadoActual = this.getState("ComerG");
        this.estadoInicial = this.getState("ComerG");
    }
}
