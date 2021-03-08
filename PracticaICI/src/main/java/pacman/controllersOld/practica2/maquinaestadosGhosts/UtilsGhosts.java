package pacman.controllersOld.practica2.maquinaestadosGhosts;

import java.util.Random;

import pacman.controllersOld.practica2.maquinaestadosGhosts.UtilsGhosts;
import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class UtilsGhosts {

	protected static int numGhostsCoveringEscape = 0;
	protected static GHOST ghostCoveringEscape = null;
	protected final static int limitGhostCoveringEscape = 1;
	protected static int limitSuicideGhost = 1;
	protected static int numSuicideGhost = 0;
	protected final static int ghostToPPLimit = 30;
	protected final static int pacmanToPPlimit = 20;
	protected final static Random rand = new Random();
	protected final static int ghostToPacmanLimit = 70;
	protected static GHOST suicideGhost = null;
	protected final static int ghostToProtectiveGhostLimit = 50;
	protected final static int distanceLimitToHelpAGhost = 50;

	
	public static void addSuicideGhost(GHOST ghost) {
		numSuicideGhost++;
		suicideGhost = ghost;
	}
	
	public static int getLimitGhostCoveringEscape() {
		return limitGhostCoveringEscape;
	}
	
	public static int getLimitSuicideGhost() {
		return limitSuicideGhost;
	}
	
	public static int getNumSuicideGhost() {
		return numSuicideGhost;
	}
	
	public static void resetNumSuicideGhost() {
		numSuicideGhost = 0;
		resetSuicideGhost();
	}
	
	protected static void resetSuicideGhost() {
		suicideGhost = null;
	}
	
	public static void addGhostCoveringEscape(GHOST ghost) {
		numGhostsCoveringEscape++;
		ghostCoveringEscape = ghost;
	}
	
	public static void resetGhostsCoveringEscape() {
		numGhostsCoveringEscape = 0;
		ghostCoveringEscape = null;
	}
	
	public static int getNumGhostCoveringEscape() {
		return numGhostsCoveringEscape;
	}
	
	public static int closestJunctionToChasedPacman(Game game) {
		
		GHOST ghostType = UtilsGhosts.getClosestGhostToPacman(game);
		int index = game.getGhostCurrentNodeIndex(ghostType);
		int[] junctionsArr = game.getJunctionIndices();
		double minDistance = Integer.MAX_VALUE;
		int minIndex = -1;
		//sacar el indexjunction del mas cercano 
		for(int i = 0; i<junctionsArr.length;i++) {
			double currentDistance = game.getManhattanDistance(index, junctionsArr[i]);
			if(currentDistance < minDistance ) {
				minDistance = currentDistance;
				minIndex = junctionsArr[i];
			}
		}
		
		return minIndex;
	}
	
	public static int closestJunctionToChasedPacman(Game game, int indexToAvoid) {
		
		GHOST ghostType = UtilsGhosts.getClosestGhostToPacman(game);
		int index = game.getGhostCurrentNodeIndex(ghostType);
		int[] junctionsArr = game.getJunctionIndices();
		double minDistance = Integer.MAX_VALUE;
		int minIndex = -1;
		//sacar el indexjunction del mas cercano 
		for(int i = 0; i<junctionsArr.length;i++) {
			double currentDistance = game.getManhattanDistance(index, junctionsArr[i]);
			if(currentDistance < minDistance && junctionsArr[i] != indexToAvoid) {
				minDistance = currentDistance;
				minIndex = junctionsArr[i];
			}
		}
		
		return minIndex;
	}
	
	/*Si un ghost esta cubriendo una ppill descarto esa ppill */
	public static boolean isTherePPillNotCovered(Game game) {
		
		for(GHOST ghostsType: GHOST.values()) {
			int nearestPPill = getNearestPPillIndex(game, ghostsType);
			
			if(!isGhostCoveringPP(game, ghostsType, nearestPPill));
				return true;
		}
		return false;
	}
	
	/**Method to know if a ghost is near enough to an specific PP*/
	public static boolean isGhostCoveringPP(Game game, GHOST ghost,int PPIndex) {

		return game.getManhattanDistance(
				game.getGhostCurrentNodeIndex(ghost), PPIndex) < ghostToPPLimit;
	}
	
	public static int getNearestPPillIndex(Game game, GHOST ghost) {
        int[] allPillIndices = game.getActivePowerPillsIndices();
        if(allPillIndices.length == 0)
        	return -1;
        
        int minPillIndex = 0;
        double minDistance = game.getDistance(game.getGhostCurrentNodeIndex(ghost),
                allPillIndices[minPillIndex],
                DM.MANHATTAN);
        for(int i=1; i<allPillIndices.length; i++) {
            double currentDistance = game.getDistance(
                    game.getGhostCurrentNodeIndex(ghost)
                    , allPillIndices[i], DM.MANHATTAN);
            if(currentDistance < minDistance) {
                minPillIndex = i;
                minDistance = currentDistance;
            }
        }
        return allPillIndices[minPillIndex];
    }
	
	public static boolean isAnyGhostCoveringPP(Game game,int PPIndex) {
		
		for(GHOST ghosts : GHOST.values()) {
			if(isGhostCloseToPPill(game, ghosts, PPIndex)) {
				return true;
			}
		}
		return false;
	}
	
	/**Compare distance from ghost to pacman, if it's the closest
	 * returns TRUE otherwise not*/
	public static boolean amIClosestGhostToPacman(Game game,GHOST ghost) {
		
		GHOST closestGhostToPacman = getClosestGhostToPacman(game);
		if(ghost.equals(closestGhostToPacman))
			return true;
		return false;
	}
	
	public static GHOST getClosestGhostToPacman(Game game){
		
		//get the fartest index from pacman
		
		double distance = 100000;
		double newDistance = 0;
		GHOST closestGhost = null;
		for(GHOST ghostType : GHOST.values()) {
			
			if((newDistance = game.getManhattanDistance(
					game.getGhostCurrentNodeIndex(ghostType), 
					game.getPacmanCurrentNodeIndex()) )< distance) {
				distance = newDistance;
				closestGhost = ghostType;	
			}
		}	
		
		return closestGhost;
	}
	
	public static int getNearestActivePillIndex(Game game, GHOST ghost) {
        int[] allPillIndices = game.getActivePillsIndices();
        int minPillIndex = 0;
        double minDistance = game.getDistance(game.getGhostCurrentNodeIndex(ghost),
                allPillIndices[minPillIndex],
                DM.PATH);
        for(int i=1; i<allPillIndices.length; i++) {
            double currentDistance = game.getDistance(
                    game.getGhostCurrentNodeIndex(ghost)
                    , allPillIndices[i], DM.PATH);
            if(currentDistance < minDistance) {
                minPillIndex = i;
                minDistance = currentDistance;
            }
        }
        return allPillIndices[minPillIndex];
    }
	
	public static boolean isGhostCloseToPPill(Game game,GHOST ghost) {
		
		int[] PPList =  game.getActivePowerPillsIndices();
		if(PPList.length>1) {
			for(int i = 0; i< PPList.length;i++) {
				if(game.getManhattanDistance(
						game.getGhostCurrentNodeIndex(ghost), PPList[i])
						< ghostToPPLimit)
					return true;
			}
		}
		return false;
	}
	
	public static boolean isGhostCloseToPPill(Game game,GHOST ghost,int PPIndex) {
		
		if(game.getManhattanDistance(game.getGhostCurrentNodeIndex(ghost), PPIndex)
						< ghostToPPLimit)
			return true;
		return false;
	}
	
	public static boolean isPPillCoverYet(Game game, GHOST ghost) {
		
		int closestPP = getNearestPPillIndex(game, ghost);
		for(GHOST ghostsType : GHOST.values()) {
			if(!ghost.equals(ghostsType) && isGhostCloseToPPill(game, ghostsType,closestPP)) {
				return true;
			}
		}
		return false;
	}
	
	/**Returns TRUE when edible ghost could follow another ghost which is not edible
	 * */
	public static boolean couldIBeProtectedByGhost(Game game,GHOST ghost) {
		//mirar cuanto tiempo me toma llegar a posicionarme detras del ghost 
		//not edible para q  me proteja
		
		//Viene de asegurarse que haya uno no comible
		for(GHOST ghostsType : GHOST.values()) {
			if(amICloseEnoughToGhost(game, ghost, ghostsType))
				return true;
		}
		return false;
	}

	
	
	public static int getIndexFromClosestNotEdibleGhost(Game game,GHOST ghost) {
		int index = -1;
		for(GHOST ghostsType : GHOST.values()) {
			if(!game.isGhostEdible(ghostsType) && 
					amICloseEnoughToGhost(game, ghost,ghostsType)) {
				index = game.getGhostCurrentNodeIndex(ghostsType);
			}
		}
		return index;
	}
	
	
	public static boolean allGhostsAreEdible(Game game) {
		for(GHOST ghostType:GHOST.values()) {
			if(!game.isGhostEdible(ghostType)) {
				return false;
			}
		}
		return true;
	}
	
	/**@Return TRUE when specific Ghost is edible*/
	public static boolean amIEdible(Game game,GHOST ghost) {
		return game.isGhostEdible(ghost);
	}
	
	
	public static boolean isPacmanCloseToPP(Game game) {
        int arrPP[] = game.getActivePowerPillsIndices();

        for(int i = 0; i<arrPP.length;i++) {
            if(game.getDistance(game.getPacmanCurrentNodeIndex(),
                    arrPP[i], DM.PATH) < pacmanToPPlimit + rand.nextInt(21))
                return true;

        }
        return false;
    }
	
	
	public static boolean isGhostCloseToPacman(Game game,GHOST ghost) {

        return game.getManhattanDistance(
        		game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex())
        		< ghostToPacmanLimit + rand.nextInt(21);
    }
	
	/**Used when ghost is not edible but pacman is going to eat a PP*/
	public static boolean isGhostInDanger(Game game, GHOST ghostType) {
        return isPacmanCloseToPP(game) && isGhostCloseToPacman(game, ghostType);
    }
	
	public static boolean amICloseEnoughToHelp(Game game,GHOST fromGhost,GHOST toGhost) {

		return game.getManhattanDistance(
				game.getGhostCurrentNodeIndex(fromGhost), 
				game.getGhostCurrentNodeIndex(toGhost))< distanceLimitToHelpAGhost;
	}
	
	/**It is used when two ghost are trying to capture pacman
	 * in a junction*/
	public static boolean amICloseEnoughToHelp(Game game,GHOST ghost) {
		
		return amICloseEnoughToHelp(game, ghost, getClosestGhostToPacman(game));
	}
	
	public static GHOST getClosestGhostToMe(Game game,GHOST ghost) {
		double distance = Integer.MAX_VALUE;
		double newDistance = 0;
		GHOST closestGhost = null;
		for(GHOST ghostType : GHOST.values()) {
			if(!ghostType.equals(ghost)) {
				if((newDistance = game.getManhattanDistance(
						game.getGhostCurrentNodeIndex(ghostType), 
						game.getGhostCurrentNodeIndex(ghost)) )< distance) {
					distance = newDistance;
					closestGhost = ghostType;	
				}
			}
		}	
		
		return closestGhost;
	}
	
	public static boolean amICloseEnoughToGhost(Game game,GHOST fromGhost,GHOST toGhost) {

		return game.getManhattanDistance(
				game.getGhostCurrentNodeIndex(fromGhost), 
				game.getGhostCurrentNodeIndex(toGhost))< ghostToProtectiveGhostLimit;
	}
	
	/**Returns TRUE when i'm close enough from a protective Ghost */
	public static boolean amICloseToAProtectiveGhost(Game game,GHOST ghost) {
		
		GHOST protectiveGhost = getClosestGhostToMe(game, ghost);
		if(amIEdible(game, protectiveGhost))
			return false;
		return amICloseEnoughToGhost(game, ghost, protectiveGhost);
	}
	
	public static boolean amITheSuicideGhost(Game game,GHOST ghost) {
		if(suicideGhost != null) {
			if(ghost.equals(suicideGhost))
				return true;
		}
		return false;
	}
	
	public static boolean isTherePPills(Game game) {
		int[] ppills = game.getActivePillsIndices();
		return ppills.length != 0;
	}
}
