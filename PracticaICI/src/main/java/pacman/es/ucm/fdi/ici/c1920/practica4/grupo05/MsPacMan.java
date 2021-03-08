package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import es.ucm.fdi.gaia.jcolibri.cbraplications.StandardCBRApplication;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Attribute;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRCaseBase;
import es.ucm.fdi.gaia.jcolibri.cbrcore.CBRQuery;
import es.ucm.fdi.gaia.jcolibri.cbrcore.Connector;
import es.ucm.fdi.gaia.jcolibri.connector.PlainTextConnector;
import es.ucm.fdi.gaia.jcolibri.exception.ExecutionException;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.RetrievalResult;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import es.ucm.fdi.gaia.jcolibri.method.retrieve.selection.SelectCases;
import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MsPacMan extends PacmanController implements StandardCBRApplication {

	Connector _connector;
	CBRCaseBase _caseBase;
	Collection<RetrievalResult> _eval;
	private static Integer idNum = 1;
	ArrayList<CBRCase> casos = new ArrayList<CBRCase>();
	private static final double valores[] = {0.8,0.6,0.4,0.2,0.0};
	private static final double limite = 0.6;
	
	
	
	@Override
	public void configure() throws ExecutionException {
		_connector = new PlainTextConnector();
		_connector.initFromXMLfile(es.ucm.fdi.gaia.jcolibri.util.FileIO.findFile("main/java/pacman/es/ucm/fdi/ici/c1920/practica4/grupo05/data/plaintextconfig.xml"));
		_caseBase = new CorrectCachedLinealCaseBase();
	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {
		_caseBase.init(_connector);
		return _caseBase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException
	{
		NNConfig simConfig = new NNConfig();
		simConfig.setDescriptionSimFunction(new Average());
		
		//Variables de la PowerPill
		simConfig.addMapping(new Attribute("distanceToPP",PacmanDescription.class),new Interval(200));
		simConfig.addMapping(new Attribute("orientacionPP",PacmanDescription.class),new Equal());
		
		//Variable junction
		Attribute nodePacmanAttribute = new Attribute("nodePacman",PacmanDescription.class);
		simConfig.addMapping(nodePacmanAttribute,new Equal());
		simConfig.setWeight(nodePacmanAttribute, 2.0);
		
		//Variables Ghost1
		simConfig.addMapping(new Attribute("distanceToGhost1",PacmanDescription.class),new Interval(200));
		simConfig.addMapping(new Attribute("edibleGhost1",PacmanDescription.class),new Equal());
		simConfig.addMapping(new Attribute("orientacionGhost1",PacmanDescription.class),new Equal());
		
		//Variables Ghost2
		simConfig.addMapping(new Attribute("distanceToGhost2",PacmanDescription.class),new Interval(200));
		simConfig.addMapping(new Attribute("edibleGhost2",PacmanDescription.class),new Equal());
		simConfig.addMapping(new Attribute("orientacionGhost2",PacmanDescription.class),new Equal());
		
		//Variables Ghost3
		simConfig.addMapping(new Attribute("distanceToGhost3",PacmanDescription.class),new Interval(200));
		simConfig.addMapping(new Attribute("edibleGhost3",PacmanDescription.class),new Equal());
		simConfig.addMapping(new Attribute("orientacionGhost3",PacmanDescription.class),new Equal());
				
		//Variables Ghost4
		simConfig.addMapping(new Attribute("distanceToGhost4",PacmanDescription.class),new Interval(200));
		simConfig.addMapping(new Attribute("edibleGhost4",PacmanDescription.class),new Equal());
		simConfig.addMapping(new Attribute("orientacionGhost4",PacmanDescription.class),new Equal());
		
		_eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), query, simConfig);
		
		_eval = SelectCases.selectTopKRR(_eval, 5);
	}

	@Override
	public void postCycle() throws ExecutionException {
		this._caseBase.learnCases(this.casos);
		this._caseBase.close();
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		
		if(game.wasPacManEaten() && casos.size() >= 5) {
			for(int i = this.casos.size() - 5; i < this.casos.size(); i++) {
				((PacmanSolution) this.casos.get(i).getSolution()).setExito(valores[i-(this.casos.size() - 5)]);
			}
			this._caseBase.learnCases(this.casos);
			this.casos = new ArrayList<CBRCase>();
		}
		
		if(game.isJunction(game.getPacmanCurrentNodeIndex())) 
		{
			int nodePacman = game.getPacmanCurrentNodeIndex();
			
			//*****Consulta del caso actual*****//
			PacmanDescription queryDesc = new PacmanDescription();
			queryDesc.setId(idNum);
			
			queryDesc.setNodePacman(nodePacman);
			
			int nodeNearestPP = this.nodeNearestPP(game);
			queryDesc.setDistanceToPP(game.getDistance(nodePacman, nodeNearestPP, DM.PATH));
			queryDesc.setOrientacionPP(game.getNextMoveTowardsTarget(nodePacman, nodeNearestPP, DM.PATH));
			
			int nodeGhost = game.getGhostCurrentNodeIndex(GHOST.BLINKY);
			queryDesc.setDistanceToGhost1(game.getDistance(nodePacman, nodeGhost, DM.PATH));
			queryDesc.setEdibleGhost1(game.isGhostEdible(GHOST.BLINKY));
			queryDesc.setOrientacionGhost1(game.getNextMoveTowardsTarget(nodePacman, nodeGhost, DM.PATH));
			
			nodeGhost = game.getGhostCurrentNodeIndex(GHOST.PINKY);
			queryDesc.setDistanceToGhost2(game.getDistance(nodePacman, nodeGhost, DM.PATH));
			queryDesc.setEdibleGhost2(game.isGhostEdible(GHOST.PINKY));
			queryDesc.setOrientacionGhost2(game.getNextMoveTowardsTarget(nodePacman, nodeGhost, DM.PATH));
			
			nodeGhost = game.getGhostCurrentNodeIndex(GHOST.INKY);
			queryDesc.setDistanceToGhost3(game.getDistance(nodePacman, nodeGhost, DM.PATH));
			queryDesc.setEdibleGhost3(game.isGhostEdible(GHOST.INKY));
			queryDesc.setOrientacionGhost3(game.getNextMoveTowardsTarget(nodePacman, nodeGhost, DM.PATH));
			
			nodeGhost = game.getGhostCurrentNodeIndex(GHOST.SUE);
			queryDesc.setDistanceToGhost4(game.getDistance(nodePacman, nodeGhost, DM.PATH));
			queryDesc.setEdibleGhost4(game.isGhostEdible(GHOST.SUE));
			queryDesc.setOrientacionGhost4(game.getNextMoveTowardsTarget(nodePacman, nodeGhost, DM.PATH));
			//*****Fin de la descripcion de la consulta*****//
			
			//*****Creacion de la consulta y consulta*****//
			CBRQuery query =  new CBRQuery();
			query.setDescription(queryDesc);
			try {
				this.cycle(query);
			} catch (ExecutionException e1) {
				e1.printStackTrace();
			}
			//*****Fin de la consulta*****//
			
			//*****Votacion para el movimiento final*****//
			int cantidad[] = new int[4];
			double votacion[] = new double[4];
			ArrayList<MOVE> listMovesPosibles = new ArrayList<MOVE>();
			MOVE moves[] = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade());
			for(int i = 0; i < moves.length; i++) {
				listMovesPosibles.add(moves[i]);
			}
			for(int i = 0; i < 4; i++) {
				cantidad[i] = 0; 
				votacion[i] = 0.0;
			}
			for(RetrievalResult nse: _eval)
			{
				MOVE result = ((PacmanSolution) nse.get_case().getSolution()).getResultado();
				double exito = ((PacmanSolution) nse.get_case().getSolution()).getExito();
				if(listMovesPosibles.contains(result)) {
					switch(result) {
						case UP: votacion[0] += exito; cantidad[0]++; break;
						case DOWN: votacion[1] += exito; cantidad[1]++; break;
						case RIGHT: votacion[2] += exito; cantidad[2]++;  break;
						case LEFT: votacion[3] += exito; cantidad[3]++;  break;
						default: break;
					}
				}
				
			}
			double max = votacion[0];
			int moveFinal = 0;
			for(int i = 1; i < 4; i++) {
				if(votacion[i] > max) {
					max = votacion[i];
					moveFinal = i;
				}
			}
			MOVE moveToReturn = MOVE.NEUTRAL;
			if(cantidad[moveFinal] == 0 || (votacion[moveFinal] / cantidad[moveFinal]) <= limite) {
				moveToReturn =  game.getPossibleMoves(game.getPacmanCurrentNodeIndex())[new Random().nextInt(game.getPossibleMoves(game.getPacmanCurrentNodeIndex()).length)] ;
			}
			else {
				switch(moveFinal) 
				{
					case 0: moveToReturn = MOVE.UP; break;
					case 1: moveToReturn = MOVE.DOWN; break;
					case 2: moveToReturn = MOVE.RIGHT; break;
					case 3: moveToReturn = MOVE.LEFT; break;
				}
			}
			
			//*****Creacion del nuevo caso que vamos a aprender*****//
			CBRCase _case = new CBRCase();
			_case.setDescription(queryDesc);
			PacmanSolution sol = new PacmanSolution();
			sol.setId(idNum);
			sol.setExito(1.0);
			sol.setResultado(moveToReturn);
			_case.setSolution(sol);
			casos.add(_case);
			idNum++;
			//*****************************************************//
			
			return moveToReturn;
		}
		
		return MOVE.NEUTRAL;
	}

	private int nodeNearestPP(Game game)
	{
		int nodoPacman = game.getPacmanCurrentNodeIndex();
		double distMin = Double.MAX_VALUE;
		int result = 0;
		for (int nodoPP : game.getActivePowerPillsIndices())
		{ 
			double distToPP = game.getDistance(nodoPacman, nodoPP, DM.PATH);
			if(distToPP < distMin)
			{
				distMin = distToPP;
				result = nodoPP;
			}
		}
		return result;
	}
	
	@Override
	public void preCompute(String opponent) {
		super.preCompute(opponent);
		try {
			this.configure();
			this._caseBase = this.preCycle();
			MsPacMan.idNum = this._caseBase.getCases().size() + 1;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void postCompute() {
		super.postCompute();
		if (casos.size() >= 5) {			
			for(int i = this.casos.size() - 5; i < this.casos.size(); i++) {
				((PacmanSolution) this.casos.get(i).getSolution()).setExito(valores[i-(this.casos.size() - 5)]);
			}
		}
		try {
			this.postCycle();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
