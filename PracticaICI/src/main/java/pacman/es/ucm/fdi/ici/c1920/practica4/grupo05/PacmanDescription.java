package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CaseComponent;
import pacman.game.Constants.MOVE;

public class PacmanDescription implements CaseComponent {

	
	Integer id;
	
	Double distanceToGhost1;
	Boolean edibleGhost1;
	MOVE orientacionGhost1;
	
	Double distanceToGhost2;
	Boolean edibleGhost2;
	MOVE orientacionGhost2;
	
	Double distanceToGhost3;
	Boolean edibleGhost3;
	MOVE orientacionGhost3;
	
	Double distanceToGhost4;
	Boolean edibleGhost4;
	MOVE orientacionGhost4;
	
	Double distanceToPP;
	MOVE orientacionPP;
	
	Integer nodePacman;
	
	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", PacmanDescription.class);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDistanceToGhost1() {
		return distanceToGhost1;
	}

	public void setDistanceToGhost1(Double distanceToGhost1) {
		this.distanceToGhost1 = distanceToGhost1;
	}

	public Boolean getEdibleGhost1() {
		return edibleGhost1;
	}

	public void setEdibleGhost1(Boolean edibleGhost1) {
		this.edibleGhost1 = edibleGhost1;
	}

	public MOVE getOrientacionGhost1() {
		return orientacionGhost1;
	}

	public void setOrientacionGhost1(MOVE orientacionGhost1) {
		this.orientacionGhost1 = orientacionGhost1;
	}

	public Double getDistanceToGhost2() {
		return distanceToGhost2;
	}

	public void setDistanceToGhost2(Double distanceToGhost2) {
		this.distanceToGhost2 = distanceToGhost2;
	}

	public Boolean getEdibleGhost2() {
		return edibleGhost2;
	}

	public void setEdibleGhost2(Boolean edibleGhost2) {
		this.edibleGhost2 = edibleGhost2;
	}

	public MOVE getOrientacionGhost2() {
		return orientacionGhost2;
	}

	public void setOrientacionGhost2(MOVE orientacionGhost2) {
		this.orientacionGhost2 = orientacionGhost2;
	}

	public Double getDistanceToGhost3() {
		return distanceToGhost3;
	}

	public void setDistanceToGhost3(Double distanceToGhost3) {
		this.distanceToGhost3 = distanceToGhost3;
	}

	public Boolean getEdibleGhost3() {
		return edibleGhost3;
	}

	public void setEdibleGhost3(Boolean edibleGhost3) {
		this.edibleGhost3 = edibleGhost3;
	}

	public MOVE getOrientacionGhost3() {
		return orientacionGhost3;
	}

	public void setOrientacionGhost3(MOVE orientacionGhost3) {
		this.orientacionGhost3 = orientacionGhost3;
	}

	public Double getDistanceToGhost4() {
		return distanceToGhost4;
	}

	public void setDistanceToGhost4(Double distanceToGhost4) {
		this.distanceToGhost4 = distanceToGhost4;
	}

	public Boolean getEdibleGhost4() {
		return edibleGhost4;
	}

	public void setEdibleGhost4(Boolean edibleGhost4) {
		this.edibleGhost4 = edibleGhost4;
	}

	public MOVE getOrientacionGhost4() {
		return orientacionGhost4;
	}

	public void setOrientacionGhost4(MOVE orientacionGhost4) {
		this.orientacionGhost4 = orientacionGhost4;
	}

	public Double getDistanceToPP() {
		return distanceToPP;
	}

	public void setDistanceToPP(Double distanceToPP) {
		this.distanceToPP = distanceToPP;
	}

	public MOVE getOrientacionPP() {
		return orientacionPP;
	}

	public void setOrientacionPP(MOVE orientacionPP) {
		this.orientacionPP = orientacionPP;
	}
	
	public Integer getNodePacman() {
		return nodePacman;
	}

	public void setNodePacman(Integer nodePacman) {
		this.nodePacman = nodePacman;
	}

	@Override
	public String toString() {
		return "PacmanDescription [id=" + id + ", distanceToGhost1=" + distanceToGhost1 + ", edibleGhost1="
				+ edibleGhost1 + ", orientacionGhost1=" + orientacionGhost1 + ", distanceToGhost2=" + distanceToGhost2
				+ ", edibleGhost2=" + edibleGhost2 + ", orientacionGhost2=" + orientacionGhost2 + ", distanceToGhost3="
				+ distanceToGhost3 + ", edibleGhost3=" + edibleGhost3 + ", orientacionGhost3=" + orientacionGhost3
				+ ", distanceToGhost4=" + distanceToGhost4 + ", edibleGhost4=" + edibleGhost4 + ", orientacionGhost4="
				+ orientacionGhost4 + ", distanceToPP=" + distanceToPP + ", orientacionPP=" + orientacionPP + "]";
	}

}
