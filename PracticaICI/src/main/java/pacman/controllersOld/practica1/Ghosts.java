package pacman.controllersOld.practica1;

import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.util.EnumMap;
import java.util.Random;

public class Ghosts extends GhostController {

    private EnumMap<GHOST,MOVE> moves = new EnumMap<GHOST,MOVE>(GHOST.class);
    private final static int pacmanToPPlimit = 40;
    private final static int ghostToPacmanLimit = 70;
    private final static Random rand = new Random();



    private int getNearestPillIndex(Game game, GHOST ghost) {
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

    public boolean isItWorthChasingPacman(Game game, GHOST ghostType) {
        return !isPacmanCloseToPP(game) && isGhostCloseToPacman(game, ghostType);
    }

    public boolean isGhostInDanger(Game game, GHOST ghostType) {
        return isPacmanCloseToPP(game) && isGhostCloseToPacman(game, ghostType);
    }

    boolean isPacmanCloseToPP(Game game) {
        int arrPP[] = game.getActivePowerPillsIndices();

        for(int i = 0; i<arrPP.length;i++) {
            if(game.getDistance(game.getPacmanCurrentNodeIndex(),
                    arrPP[i], DM.PATH) < pacmanToPPlimit + rand.nextInt(21))
                return true;

        }
        return false;
    }

    boolean isGhostCloseToPacman(Game game,GHOST ghost) {

        return game.getDistance(game.getGhostCurrentNodeIndex(ghost),
                game.getPacmanCurrentNodeIndex(), DM.PATH) < ghostToPacmanLimit + rand.nextInt(21);

    }



    @Override
    public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

        moves.clear();
        MOVE lastMoveMade;
        for (GHOST ghostType: GHOST.values()) {
            int nearestPillNodeIndex = getNearestPillIndex(game,ghostType);
            lastMoveMade = game.getGhostLastMoveMade(ghostType);
            MOVE newMove = MOVE.NEUTRAL;

            if(isGhostInDanger(game,ghostType) || game.isGhostEdible(ghostType)) {
                newMove = game.getApproximateNextMoveAwayFromTarget(
                        game.getGhostCurrentNodeIndex(ghostType),
                        game.getPacmanCurrentNodeIndex(),
                        lastMoveMade,
                        DM.PATH);
            }else if(isItWorthChasingPacman(game,ghostType)) {
                newMove = game.getApproximateNextMoveTowardsTarget(
                        game.getGhostCurrentNodeIndex(ghostType),
                        game.getPacmanCurrentNodeIndex(),
                        lastMoveMade,
                        DM.PATH);
            }else {
                newMove = game.getApproximateNextMoveTowardsTarget(
                        game.getGhostCurrentNodeIndex(ghostType),
                        nearestPillNodeIndex,
                        lastMoveMade,
                        DM.PATH);
            }

            moves.put(ghostType, newMove);
        }

        return moves;
    }


}
