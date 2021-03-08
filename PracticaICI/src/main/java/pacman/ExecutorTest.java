package pacman;

import java.util.ArrayList;

import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;
import pacman.controllersOld.practica1.Ghosts;
import pacman.controllersOld.practica2.MsPacMan;

@SuppressWarnings("unused")
public class ExecutorTest {
	
	public static void main(String[] args) {
			
		Executor executor = new Executor.Builder()
		.setTickLimit(4000)
		.setVisual(true)
		.setScaleFactor(2.0)
		.build();
			
		PacmanController msPacMan = new MsPacMan();
			
		GhostController ghosts = new Ghosts();
			
		executor.runGame(msPacMan, ghosts, 1);
		
		//executor.runExperiment(msPacMan, ghosts, 100, "");
		
		//System.out.println(executor.runExperiment(msPacMan, ghosts, 15, "")[0].getAverage());

	}
}