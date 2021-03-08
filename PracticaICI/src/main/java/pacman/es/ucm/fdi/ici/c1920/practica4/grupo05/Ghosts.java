package pacman.es.ucm.fdi.ici.c1920.practica4.grupo05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController implements StandardCBRApplication {

	Connector _connector;
	double successLim = 100;
	CBRCaseBase _casebase;
	//int solId = 0;
	Random rand = new Random();
	ArrayList<CBRCase> cases = new ArrayList<>();
	Collection<RetrievalResult> _eval;
	int casesFiltered = 5; // use in
	private static final Double MAX_DISTANCE = 200.0;
	private static Integer idGhost = 1;

	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);

	@Override
	public void configure() throws ExecutionException {
		// TODO Auto-generated method stub

		try {
			_connector = new PlainTextConnector();
			_connector.initFromXMLfile(es.ucm.fdi.gaia.jcolibri.util.FileIO
					.findFile("main/java/pacman/es/ucm/fdi/ici/c1920/practica4/grupo05/data/plaintextghost.xml"));
			_casebase = new CorrectCachedLinealCaseBase();

		} catch (Exception e) {
			System.out.println("Fail on configure()");
			e.printStackTrace();
			throw new ExecutionException();
		}

	}

	@Override
	public CBRCaseBase preCycle() throws ExecutionException {

		_casebase.init(_connector);
		return _casebase;
	}

	@Override
	public void cycle(CBRQuery query) throws ExecutionException {
		// TODO Auto-generated method stub
		NNConfig simConfig = new NNConfig();

		simConfig.setDescriptionSimFunction(new Average());

		simConfig.addMapping(new Attribute("ghost", GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("currentGToPacDistance", GhostsDescription.class), new Interval(200.0));
		simConfig.addMapping(new Attribute("ghost1ToPacDistance", GhostsDescription.class), new Interval(200.0));
		simConfig.addMapping(new Attribute("ghost2ToPacDistance", GhostsDescription.class), new Interval(200.0));
		simConfig.addMapping(new Attribute("ghost3ToPacDistance", GhostsDescription.class), new Interval(200.0));
		simConfig.addMapping(new Attribute("edibleGhosts", GhostsDescription.class), new Interval(4));
		simConfig.addMapping(new Attribute("pacToClosestActivePPDistance", GhostsDescription.class),
				new Interval(200.0));
		simConfig.addMapping(new Attribute("activePP", GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("isGhostEdible", GhostsDescription.class), new Equal());
		simConfig.addMapping(new Attribute("lastMove", GhostsDescription.class), new Equal());

		_eval = NNScoringMethod.evaluateSimilarity(_casebase.getCases(), query, simConfig);
		_eval = SelectCases.selectTopKRR(_eval, this.casesFiltered);


	}

	@Override
	public void postCycle() throws ExecutionException {
		this._casebase.learnCases(this.cases);
		this._casebase.close();
	}

	public boolean isTherePPills(Game game) {
		int[] ppills = game.getActivePillsIndices();
		return ppills.length != 0;
	}

	double pacmanDistanceToPPill(Game game) {
		double result = Double.MAX_VALUE;
		int arrPP[] = game.getActivePowerPillsIndices();

		for (int i = 0; i < arrPP.length; i++) {
			double xdg = game.getDistance(game.getPacmanCurrentNodeIndex(), arrPP[i], DM.PATH);
			if (xdg < 40 + rand.nextInt(21))
				result = xdg;

		}
		return result;
	}

	public boolean amIEdible(Game game, GHOST ghost) {
		return game.isGhostEdible(ghost);
	}

	public int edibleGhosts(Game game) {
		int ghostsEdible = 0;
		for (GHOST ghostType : GHOST.values()) {
			if (game.isGhostEdible(ghostType)) {
				ghostsEdible++;
			}
		}
		return ghostsEdible;
	}

	public ArrayList<Double> findGhostsToPacmanDistances(Game game, GHOST gh) {
		ArrayList<Double> dists = new ArrayList<>();
		for (GHOST ght : GHOST.values()) {
			if (gh != ght) {
				double dd = game.getDistance(game.getGhostCurrentNodeIndex(ght), game.getPacmanCurrentNodeIndex(),
						DM.PATH);
				if (dd != -1)
					dists.add(dd);
				else
					dists.add(200.0);
			}
		}
		Collections.sort(dists);
		return dists;

	}

	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue) {

		for (GHOST ghostType : GHOST.values()) {

			if (game.wasGhostEaten(ghostType)) {
				// Recuperar los ultimos 5 casos de un ghost especifico y bajar la proporcion
				// filtro los casos del ghost especifico
				// nosotros tenemos que salir solo cuando se encuentren 5 de mi tipoghost, como
				// estan mezclados
				// no sabemos cuando , recorremos hasta encontrarlos
				int cnt = 0;
				for (int i = this.cases.size() - 1; i > 0; i--) {
					if (((GhostsDescription) this.cases.get(i).getDescription()).getGhost() == ghostType) {
						Double succProb = ((GhostsSolutions) this.cases.get(i).getSolution()).getSuccessProb();
						((GhostsSolutions) this.cases.get(i).getSolution())
								.setSuccessProb(succProb > 0 ? succProb - 0.1 : 0.0);
						cnt++;
					}
					if (cnt == casesFiltered)
						break;
				}

			}

			if (game.doesGhostRequireAction(ghostType)) {

				// Aqui tomamos una decision, hacemos una consulta comparativa con los casos
				// guardados
				if (game.isJunction(game.getGhostCurrentNodeIndex(ghostType))) {

					GhostsDescription queryDesc = new GhostsDescription();

					// recogemos todos los atributos pero no miramos todos , dependiendo si es
					// edible o no
					ArrayList<Double> ghostsDists = findGhostsToPacmanDistances(game, ghostType);
					//No deberiamos verificar el nombre pq agreamos 2 veces el mismo case si no cumple
					
					queryDesc.setId(idGhost);
					queryDesc.setGhost(ghostType);
					queryDesc.setCurrentGToPacDistance(game.getDistance(game.getGhostCurrentNodeIndex(ghostType),
							game.getPacmanCurrentNodeIndex(), DM.PATH));
					
					if (game.isGhostEdible(ghostType)) {
						queryDesc.setGhost1ToPacDistance(MAX_DISTANCE);
						queryDesc.setGhost2ToPacDistance(MAX_DISTANCE);
						queryDesc.setGhost3ToPacDistance(MAX_DISTANCE);
						queryDesc.setIsGhostEdible(true);
					} else {
						queryDesc.setGhost1ToPacDistance(ghostsDists.get(0));
						queryDesc.setGhost2ToPacDistance(ghostsDists.get(1));
						queryDesc.setGhost3ToPacDistance(ghostsDists.get(2));
						queryDesc.setIsGhostEdible(false);
					}

					double distanceToPPill = pacmanDistanceToPPill(game);
					queryDesc.setPacToClosestActivePPDistance(distanceToPPill > 200 ? 200 : distanceToPPill);
					queryDesc.setActivePP(isTherePPills(game));
					queryDesc.setEdibleGhosts(edibleGhosts(game));
					queryDesc.setLastMove(game.getGhostLastMoveMade(ghostType));

					// hace la consulta y guarda el resultado en _eval
					CBRQuery query = new CBRQuery();
					query.setDescription(queryDesc);
					try {
						this.cycle(query);
					} catch (ExecutionException e1) {
						e1.printStackTrace();
					}
					// Quiz'as funcione con un map

					Map<MOVE, Double> _map = new HashMap<>();

					_map.put(MOVE.UP, 0.0);
					_map.put(MOVE.DOWN, 0.0);
					_map.put(MOVE.RIGHT, 0.0);
					_map.put(MOVE.LEFT, 0.0);

					for (RetrievalResult nse : _eval) {
						MOVE sol = ((GhostsSolutions) nse.get_case().getSolution()).getSolution();
						double succProb = ((GhostsSolutions) nse.get_case().getSolution()).getSuccessProb();
						switch (sol) {
						case UP:
							_map.replace(MOVE.UP, _map.get(MOVE.UP) + succProb);
						case DOWN:
							_map.replace(MOVE.DOWN, _map.get(MOVE.DOWN) + succProb);
						case RIGHT:
							_map.replace(MOVE.RIGHT, _map.get(MOVE.RIGHT) + succProb);
						case LEFT:
							_map.replace(MOVE.LEFT, _map.get(MOVE.LEFT) + succProb);
						default:
							break;

						}
					}

					
					Double finaMoveIdx = Collections.max(_map.values());
					MOVE resul = MOVE.NEUTRAL;
					
					MOVE[] m;
					
					for (Entry<MOVE, Double> mv : _map.entrySet()) {
						if(mv.getValue()<successLim) {
							m = game.getPossibleMoves(game.getGhostCurrentNodeIndex(ghostType));
							resul = m[rand.nextInt(m.length)];
						}else if (mv.getValue().equals(finaMoveIdx)) {
							resul = mv.getKey();
							break;
						}
					}

//					Double bestEval = best similar case in _eval;
//					compare that case with case about to be stored
					// si no es un buen caso el similar de los K-NN tengo que almacenar mi nuevo caso
					CBRCase _case = new CBRCase();
					_case.setDescription(queryDesc);
					GhostsSolutions sol = new GhostsSolutions();
					sol.setId(idGhost);
					sol.setSolution(resul);
					sol.setSuccessProb((Double) 1.0);
					_case.setSolution(sol);
					cases.add(_case);
					idGhost++;
					
					// el mejor entre los knn lo comparo con el caso, si es muy similar (similitud > 0.9 lo reemplazo
					//_case = best K-NN
					sol = (GhostsSolutions) _case.getSolution();

					/*
					 * 
					 * ArrayList<Double> votes = new ArrayList<>(); votes.add(0.0); votes.add(0.0);
					 * votes.add(0.0); votes.add(0.0); for (RetrievalResult nse : _eval) { MOVE sol
					 * = ((GhostsSolutions) nse.get_case().getSolution()).getSolution(); double
					 * succProb= ((GhostsSolutions) nse.get_case().getSolution()).getSuccessProb();
					 * switch(sol) { case UP: votes.set(0, votes.get(0) + succProb); break; case
					 * DOWN: votes.set(1, votes.get(1) + succProb); break; case RIGHT: votes.set(2,
					 * votes.get(2) + succProb); break; case LEFT: votes.set(3, votes.get(3) +
					 * succProb); break; default: break } }
					 * 
					 * Double xx = Collections.max(votes); MOVE finalMove = MOVE.NEUTRAL;
					 * 
					 * int finalMoveIndex = votes.indexOf(xx); switch(finalMoveIndex) { case 0:
					 * finalMove = MOVE.UP; case 1: finalMove = MOVE.DOWN; case 2: finalMove =
					 * MOVE.RIGHT; case 3: finalMove = MOVE.LEFT; }
					 * 
					 * CBRCase _case = new CBRCase(); _case.setDescription(queryDesc);
					 * GhostsSolutions sol = new GhostsSolutions(); sol.setId(solId);
					 * sol.setSuccessProb((Double)1.0); sol.setSolution(finalMove);
					 * _case.setSolution(sol); cases.add(_case); //casosAdd.add(_case); solId++;
					 */

				}

			} else
				moves.put(ghostType, MOVE.NEUTRAL);
		}

		return moves;
	}

	@Override
	public void preCompute(String opponent) {
		super.preCompute(opponent);
		try {
			this.configure();
			this._casebase = this.preCycle();
			Ghosts.idGhost = this._casebase.getCases().size() + 1;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void postCompute() {
		super.postCompute();
		for (GHOST ghostType : GHOST.values()) {
			int cnt = 0;
			for (int i = this.cases.size() - 1; i > 0; i--) {
				if (((GhostsDescription) this.cases.get(i).getDescription()).getGhost() == ghostType) {
					Double succProb = ((GhostsSolutions) this.cases.get(i).getSolution()).getSuccessProb();
					((GhostsSolutions) this.cases.get(i).getSolution())
							.setSuccessProb(succProb > 0 ? succProb - 0.1 : 0.0);
					cnt++;
				}
				if (cnt == casesFiltered)
					break;
			}
		}
		try {
			this.postCycle();
		} catch (ExecutionException e) {
			
			e.printStackTrace();
		}
	}

}
