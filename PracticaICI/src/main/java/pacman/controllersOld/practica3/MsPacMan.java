package pacman.controllersOld.practica3;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import java.util.HashMap;

/*
 * The Class RandomPacMan.
 */
public final class MsPacMan extends PacmanController {

	
	public static double CALMA_LIMIT = 3.5;
	public static double RUNAWAY__LIMIT = 20.0;
	public static double EATGHOST_LIMIT = 7.0;
	
	public static final double MAX_DISTANCE = 200.0;
	public static final double MAX_CONFIDENCE = 100.0;
	private boolean[] posPP = {true,true,true,true}; 
	private int lastLevel = 0;
	
	FuzzyEngine fe;
	HashMap<String, Double> input;
	HashMap<String, Double> output;
	
	public MsPacMan() {
		fe = new FuzzyEngine(FuzzyEngine.FUZZY_CONTROLLER.MSPACMAN);
		input = new HashMap<String,Double>();
		output = new HashMap<String,Double>();
	}
	
	private double[] distances = {MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE};
	private double[] distancesEdible = {MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE};
	private double[] confidence = {MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE,MAX_DISTANCE};
	private int current;
	
    @Override
    public MOVE getMove(Game game, long timeDue) {
    	//Resets iniciales
        current = game.getPacmanCurrentNodeIndex();
        input.clear(); output.clear();
        if(game.getCurrentLevel() != lastLevel) {
        	lastLevel = game.getCurrentLevel();
        	for(int i = 0; i < 4; i++) posPP[i] = true;
        }
        if(game.wasPacManEaten()) {
        	for(int i = 0; i < 4; i++) {
        		distances[i] = MAX_DISTANCE;
        		distancesEdible[i] = MAX_DISTANCE;
        		confidence[i] = MAX_CONFIDENCE;
        	}
        	
        }

        double  min = MAX_DISTANCE; double timeEdible = MAX_DISTANCE;
        //Calculos iniciales
        for (GHOST ghost : GHOST.values()) {
            double distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
            if(distance == -1) {
            	distance = MAX_DISTANCE;
            	confidence[ghost.ordinal()] = MAX_CONFIDENCE;
            }
            if(distance != 0) {
            	confidence[ghost.ordinal()] = MAX_CONFIDENCE;
            	distances[ghost.ordinal()] = distance;
            	if(game.isGhostEdible(ghost)) {
            		distancesEdible[ghost.ordinal()] = distance;
            		if(distance < min) timeEdible = game.getGhostEdibleTime(ghost);
            	}
            }
            else if (distance == 0 && confidence[ghost.ordinal()] > 0) confidence[ghost.ordinal()]--;
            input.put(ghost.name()+"distance", distances[ghost.ordinal()]);
            input.put(ghost.name()+"distanceEdible", distancesEdible[ghost.ordinal()]);
            input.put(ghost.name()+"confidence", confidence[ghost.ordinal()]);
        }
        input.put("TimeEdible",  timeEdible);
        fe.evaluate("FuzzyMsPacMan", input, output);
        
		double runaway = output.get("runAway");
		//System.out.println("runAway: " + runaway);
	        
		double eatGhost = output.get("eatGhost");
		//System.out.println("eatGhost: " + eatGhost);
		
		for(int i = 0; i < 4; i++)
		{
			if(game.isPowerPillStillAvailable(i)!=null && !game.isPowerPillStillAvailable(i)) posPP[i] = false;
		}
		
		if(runaway < CALMA_LIMIT) return goToPills(game);
		if(runaway > RUNAWAY__LIMIT) {
			if(eatGhost > EATGHOST_LIMIT) return runToClosestGhost(game);
			return runAwayFromClosestGhost(game);
		}
		return goToPowerPills(game);
    }

	private MOVE runToClosestGhost(Game game) {
    	//////System.out.println("Ghost");
    	double min_distance = Double.MAX_VALUE;
        GHOST closest_ghost = null;
        for (GHOST ghost : GHOST.values()) {
            double distance = distances[ghost.ordinal()];    		
    		if(distance < min_distance)
    		{
    			min_distance = distance;
    			closest_ghost = ghost;
    		}
        }
    	return game.getNextMoveTowardsTarget(current, game.getGhostCurrentNodeIndex(closest_ghost), DM.PATH);
    }
    
    private MOVE runAwayFromClosestGhost(Game game) {
    	//////System.out.println("RunAway");
    	double min_distance = Double.MAX_VALUE;
        GHOST closest_ghost = null;
        for (GHOST ghost : GHOST.values()) {
            double distance = distances[ghost.ordinal()];    		
    		if(distance < min_distance)
    		{
    			min_distance = distance;
    			closest_ghost = ghost;
    		}
        }
    	return game.getNextMoveAwayFromTarget(current, game.getGhostCurrentNodeIndex(closest_ghost), DM.PATH);
    }
    
	private MOVE goToPowerPills(Game game) {
    	////System.out.println("goToPowerPills");
		MOVE nextMove = null;
		int nodoPacman = game.getPacmanCurrentNodeIndex();
		double distMin = Double.MAX_VALUE;int i=0;
		for (int nodoPP : game.getPowerPillIndices())
		{ 	
			double distToPP = game.getDistance(nodoPacman, nodoPP, DM.PATH);
			MOVE possibleMove = game.getNextMoveTowardsTarget(nodoPacman, nodoPP, DM.PATH);
			if(distToPP < distMin && posPP[i])
			{
				distMin = distToPP;
				nextMove = possibleMove;
			}
			i++;
		}
		return nextMove;
	}
    
	private MOVE goToPills(Game game) {
		////System.out.println("goToPills");
		MOVE nextMove = null;
		int nodoPacman = game.getPacmanCurrentNodeIndex();
		double distMin = Double.MAX_VALUE;
		for (int nodoP : game.getActivePillsIndices())
		{ 
			double distToP = game.getDistance(nodoPacman, nodoP, DM.PATH);
			MOVE possibleMove = game.getNextMoveTowardsTarget(nodoPacman, nodoP, DM.PATH);
			if(distToP < distMin)
			{
				distMin = distToP;
				nextMove = possibleMove;
			}
		}
		return nextMove;
	}
	
}