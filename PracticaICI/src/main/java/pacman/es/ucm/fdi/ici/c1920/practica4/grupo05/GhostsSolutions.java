package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class GhostsSolutions implements CaseComponent{

	Integer id = 0;
	MOVE solution;
	Double successProb;
	
	@Override
	public Attribute getIdAttribute() {
		// TODO Auto-generated method stub
		return new Attribute("id",GhostsSolutions.class);
	}
	
	public MOVE getSolution() {
		return this.solution;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getSuccessProb() {
		return successProb;
	}

	public void setSuccessProb(Double successProb) {
		this.successProb = successProb;
	}

	public void setSolution(MOVE solution) {
		this.solution = solution;
	}
	
	

}
