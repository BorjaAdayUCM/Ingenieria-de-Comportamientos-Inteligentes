package pacman.controllersOld.practica2.maquinaestadosPacMan;

import pacman.controllersOld.practica2.maquinaestados.HFSM;
import pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan.StateComerG;
import pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan.StateComerP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan.StateComerPP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.estadosPacMan.StateHuir;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionAllGhostInJail;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionComerGComerP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionComerGComerPP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionComerPPComerG;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionHuirComerP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionHuirComerPP;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionIsNearGhost;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionNoAllGhostInJailNoPower;
import pacman.controllersOld.practica2.maquinaestadosPacMan.transicionesPacMan.TransicionNoAllGhostInJailPower;


public class HFSMPacman extends HFSM {

    @Override
    protected void initHFSM()
    {
    	//Estados de la m�quina
    	this.addState(new StateHuir("Huir"));
    	this.addState(new StateComerP("ComerP"));
    	this.addState(new StateComerPP("ComerPP"));
    	this.addState(new StateComerG("ComerG"));
    	
    	//Transiciones de la m�quina jer�rquica
    	this.addTransicion(new TransicionNoAllGhostInJailNoPower("TransicionNoAllGhostInJailNoPower"));
    	this.addTransicion(new TransicionNoAllGhostInJailPower("TransicionNoAllGhostInJailPower"));
    	this.addTransicion(new TransicionAllGhostInJail("TransicionAllGhostInJail"));
    	
    	//Transiciones de FSMNotPowered
    	this.addTransicion(new TransicionHuirComerPP("TransicionHuirComerPP"));
    	this.addTransicion(new TransicionHuirComerP("TransicionHuirComerP"));
    	this.addTransicion(new TransicionIsNearGhost("TransicionIsNearGhost"));
    	
    	//Transiciones de FSMPowered
    	this.addTransicion(new TransicionComerGComerPP("TransicionComerGComerPP"));
    	this.addTransicion(new TransicionComerGComerP("TransicionComerGComerP"));
    	this.addTransicion(new TransicionComerPPComerG("TransicionComerPPComerG"));
    	
    	
    	//A�adidas las m�quinas de estados
    	this.addFSM(new FSMNotPowered(this.listaEstados, this.listaTransiciones, "NotPowered"));
    	this.addFSM(new FSMPowered(this.listaEstados, this.listaTransiciones, "Powered"));
    	this.addFSM(new FSMNoGhost(this.listaEstados, this.listaTransiciones, "NoGhost"));
    	
    	//Tabla de transiciones de la HFSM
    	this.addTransicion(this.getFSM("Powered"), this.getTransicion("TransicionNoAllGhostInJailNoPower"), this.getFSM("NotPowered"));
        this.addTransicion(this.getFSM("NotPowered"),this.getTransicion("TransicionNoAllGhostInJailPower"), this.getFSM("Powered"));
        this.addTransicion(this.getFSM("NoGhost"), this.getTransicion("TransicionNoAllGhostInJailPower"), this.getFSM("Powered"));
        this.addTransicion(this.getFSM("NoGhost"), this.getTransicion("TransicionNoAllGhostInJailNoPower"), this.getFSM("NotPowered"));
        this.addTransicion(this.getFSM("NotPowered"), this.getTransicion("TransicionAllGhostInJail"), this.getFSM("NoGhost"));
        this.addTransicion(this.getFSM("Powered"), this.getTransicion("TransicionAllGhostInJail"), this.getFSM("NoGhost"));
    	
        //Inicializaci�n de la m�quina
    	this.FSMInicial = this.getFSM("NoGhost");
    	this.FSMActual = this.getFSM("NoGhost");
        
    }
}
