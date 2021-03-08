package pacman.controllersOld.practica3;

import java.util.HashMap;

import java.util.Random;
import pacman.controllers.POGhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends POGhostController{

	private static final Double NO_MAYBE_LIMIT = 10.0;
	private static final Double MAYBE_GO_LIMIT = 20.0;
	private static final Double MAX_DISTANCE = 200.0;
//	private static final int GHOST_COVER_LIMIT = 5;
	
	FuzzyEngine fe;
	HashMap<String, Double> input;   HashMap<String, Double> output;
	
	public Ghosts() {
		fe = new FuzzyEngine(FuzzyEngine.FUZZY_CONTROLLER.GHOSTS);
		input = new HashMap<String, Double>();
		output = new HashMap<String, Double>();
//		timesGhostCoveredPills.put(GHOST.BLINKY, 0);
//		timesGhostCoveredPills.put(GHOST.INKY, 0);
//		timesGhostCoveredPills.put(GHOST.PINKY, 0);
//		timesGhostCoveredPills.put(GHOST.SUE, 0);
	}
	
	private int currentGhost;
	private int currentPacman;
//	private HashMap<GHOST, Integer> timesGhostCoveredPills = new HashMap<GHOST, Integer>();
	
	@Override
	public MOVE getMove(GHOST ghost, Game game, long timeDue) {
		// TODO Auto-generated method stub
		//System.out.println("getMove de Ghost");
		currentGhost = game.getGhostCurrentNodeIndex(ghost);
		currentPacman = game.getPacmanCurrentNodeIndex();
		
		input.clear();output.clear();

		double distance = getPacmanDistanceFromGhost(game, ghost);
		double pacmanToPPillDistance = 0.0;
		
//		System.out.println("distancia: " + distance);
		
//		System.out.println("GHOST " + ghost.name() + " distance to pacman " + distance);
		double minDis = MAX_DISTANCE;
		input.put("PACMANdistance", distance);
			// get nearest ppill to pacman
		int[] ppillIndexes = game.getPowerPillIndices();
		for (int i = 0; i< ppillIndexes.length; i++) {
			pacmanToPPillDistance = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), ppillIndexes[i]);
			if(pacmanToPPillDistance!=0 && pacmanToPPillDistance<minDis) {
				minDis = pacmanToPPillDistance;
			}
				
		}
		if (minDis != MAX_DISTANCE)
			input.put("PACMANdistanceToPPill", minDis);
		else
			input.put("PACMANdistanceToPPill", 200.0);
		
		
		fe.evaluate("FuzzyGhosts", input, output);
		//System.out.println("OUTPUT : "+output.get("chasePac"));
		//fe.debugRules("FuzzyGhosts", "GhostsRules");
		
		//System.out.println(" ------------------------------------------- ");

		
		double fDistance = output.get("chasePac");
		
		if(fDistance<NO_MAYBE_LIMIT) {
//			if(timesGhostCoveredPills.get(ghost) < GHOST_COVER_LIMIT)
				int nearestPill = getNearestPillIndex(game, ghost);
			//else
				
			if(nearestPill == -1)
				return MOVE.NEUTRAL;
			return game.getApproximateNextMoveTowardsTarget(
                    game.getGhostCurrentNodeIndex(ghost),
                    nearestPill,
                    game.getGhostLastMoveMade(ghost),
                    DM.PATH);
		}else if(fDistance < MAYBE_GO_LIMIT) {
			MOVE[] m = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost));
			if(m.length==0)
				return MOVE.NEUTRAL;
			Random r = new Random();
			int ind = r.nextInt(m.length);
			return m[ind];
		}else {
			if(!game.isGhostEdible(ghost))
				return game.getApproximateNextMoveTowardsTarget(
                    game.getGhostCurrentNodeIndex(ghost),
                    game.getPacmanCurrentNodeIndex(),
                    game.getGhostLastMoveMade(ghost),
                    DM.PATH);
			else
				// suicide?
				return game.getApproximateNextMoveAwayFromTarget(
	                    game.getGhostCurrentNodeIndex(ghost),
	                    game.getPacmanCurrentNodeIndex(),
	                    game.getGhostLastMoveMade(ghost),
	                    DM.PATH);
		}
		
		
		//fin prueba

	}
	
	private int getNearestPillIndex(Game game, GHOST ghost) {
		//System.out.println("CoverArea");
        int[] allPillIndices = game.getActivePillsIndices();
        if(allPillIndices.length == 0)
        	return -1;
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
//        timesGhostCoveredPills.put(ghost, timesGhostCoveredPills.get(ghost)+1);
        return allPillIndices[minPillIndex];
    }
	
	private double getPacmanDistanceFromGhost(Game game, GHOST ghost) {
		double distance;
		try {
			 distance = game.getShortestPathDistance(currentGhost,
					 currentPacman, game.getGhostLastMoveMade(ghost));
		}catch(Exception e) {
			distance = 200;
		}
		if(distance == 0 )
			distance = 200;
		
		return distance;
	}

}


/*
public class Ghosts extends POGhostController{

	private static final Double NO_MAYBE_LIMIT = 10.0;
	private static final Double MAYBE_GO_LIMIT = 20.0;
	private static final Double MAX_DISTANCE = 200.0;
	
	FuzzyEngine fe;
	HashMap<String, Double> input;   HashMap<String, Double> output;
	
	public Ghosts() {
		fe = new FuzzyEngine(FuzzyEngine.FUZZY_CONTROLLER.GHOSTS);
		input = new HashMap<String, Double>();
		output = new HashMap<String, Double>();
	}
	
	private int currentGhost;
	private int currentPacman;
	
	@Override
	public MOVE getMove(GHOST ghost, Game game, long timeDue) {
		// TODO Auto-generated method stub
		
		currentGhost = game.getGhostCurrentNodeIndex(ghost);
		currentPacman = game.getPacmanCurrentNodeIndex();
		input.clear();output.clear();
		double distance;
		try {
			 distance = game.getShortestPathDistance(currentGhost,
					 currentPacman, game.getGhostLastMoveMade(ghost));
		}catch(Exception e) {
			distance = 200;
		}
		if(distance == 0 )
			distance = 200;
		//prueba
		if(distance!=200)
			//System.out.println("distancia: " + distance);
		
		//System.out.println("GHOST "+ghost.name() + " distance to pacman " + distance);

		input.put("PACMANdistance", distance);
		fe.evaluate("FuzzyGhosts", input, output);
		//System.out.println("OUTPUT : "+output.get("chasePac"));
		//fe.debugRules("FuzzyGhosts", "GhostsRules");
		
		//System.out.println(" ------------------------------------------- ");
		
		double fDistance = output.get("chasePac");
		
		if(fDistance<NO_MAYBE_LIMIT) {
			int nearestPill = getNearestPillIndex(game, ghost);
			if(nearestPill == -1)
				return MOVE.NEUTRAL;
			return game.getApproximateNextMoveTowardsTarget(
                    game.getGhostCurrentNodeIndex(ghost),
                    nearestPill,
                    game.getGhostLastMoveMade(ghost),
                    DM.PATH);
		}else if(fDistance < MAYBE_GO_LIMIT) {
			MOVE[] m = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghost));
			if(m.length==0)
				return MOVE.NEUTRAL;
			Random r = new Random();
			int ind = r.nextInt(m.length);
			return m[ind];
		}else {
			if(!game.isGhostEdible(ghost))
				return game.getApproximateNextMoveTowardsTarget(
                    game.getGhostCurrentNodeIndex(ghost),
                    game.getPacmanCurrentNodeIndex(),
                    game.getGhostLastMoveMade(ghost),
                    DM.PATH);
			else
				return game.getApproximateNextMoveAwayFromTarget(
	                    game.getGhostCurrentNodeIndex(ghost),
	                    game.getPacmanCurrentNodeIndex(),
	                    game.getGhostLastMoveMade(ghost),
	                    DM.PATH);
		}
		
		
		//fin prueba

	}
	
	
	private int getNearestPillIndex(Game game, GHOST ghost) {
		//System.out.println("CoverArea");
        int[] allPillIndices = game.getActivePillsIndices();
        if(allPillIndices.length == 0)
        	return -1;
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

}*/
