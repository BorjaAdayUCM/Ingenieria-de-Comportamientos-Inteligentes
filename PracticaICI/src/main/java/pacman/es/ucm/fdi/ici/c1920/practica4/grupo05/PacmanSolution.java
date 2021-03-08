package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class PacmanSolution implements CaseComponent {

	Integer id = 0;
	Double exito;
	MOVE resultado;
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", PacmanSolution.class);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getExito() {
		return exito;
	}

	public void setExito(Double exito) {
		this.exito = exito;
	}

	public MOVE getResultado() {
		return resultado;
	}

	public void setResultado(MOVE resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "PacmanSolution [id=" + id + ", exito=" + exito + ", resultado=" + resultado + "]";
	}
	
}
