package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class GhostsDescription implements CaseComponent {

	Integer id;

	GHOST ghost;
	Double currentGToPacDistance;
	Double ghost1ToPacDistance;
	Double ghost2ToPacDistance;
	Double ghost3ToPacDistance;
	Integer edibleGhosts;
	Double pacToClosestActivePPDistance;
	Boolean activePP;
	Boolean isGhostEdible;
	MOVE lastMove;
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id",GhostsDescription.class);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public GHOST getGhost() {
		return ghost;
	}

	public void setGhost(GHOST ghost) {
		this.ghost = ghost;
	}

	public Double getCurrentGToPacDistance() {
		return currentGToPacDistance;
	}

	public void setCurrentGToPacDistance(Double currentGToPacDistance) {
		this.currentGToPacDistance = currentGToPacDistance;
	}

	public Double getGhost1ToPacDistance() {
		return ghost1ToPacDistance;
	}

	public void setGhost1ToPacDistance(Double ghost1ToPacDistance) {
		this.ghost1ToPacDistance = ghost1ToPacDistance;
	}

	public Double getGhost2ToPacDistance() {
		return ghost2ToPacDistance;
	}

	public void setGhost2ToPacDistance(Double ghost2ToPacDistance) {
		this.ghost2ToPacDistance = ghost2ToPacDistance;
	}

	public Double getGhost3ToPacDistance() {
		return ghost3ToPacDistance;
	}

	public void setGhost3ToPacDistance(Double ghost3ToPacDistance) {
		this.ghost3ToPacDistance = ghost3ToPacDistance;
	}

	public Integer getEdibleGhosts() {
		return edibleGhosts;
	}

	public void setEdibleGhosts(Integer edibleGhosts) {
		this.edibleGhosts = edibleGhosts;
	}

	public Double getPacToClosestActivePPDistance() {
		return pacToClosestActivePPDistance;
	}

	public void setPacToClosestActivePPDistance(Double pacToClosestActivePPDistance) {
		this.pacToClosestActivePPDistance = pacToClosestActivePPDistance;
	}

	public Boolean getActivePP() {
		return activePP;
	}

	public void setActivePP(Boolean activePP) {
		this.activePP = activePP;
	}

	public Boolean getIsGhostEdible() {
		return isGhostEdible;
	}

	public void setIsGhostEdible(Boolean isGhostEdible) {
		this.isGhostEdible = isGhostEdible;
	}

	public MOVE getLastMove() {
		return lastMove;
	}

	public void setLastMove(MOVE lastMove) {
		this.lastMove = lastMove;
	}


}
