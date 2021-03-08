package pacman;

/**
 * PacManEvaluator
 * Clase para evaluar las entregas de MsPacMan
 * Ingenier�a de Comportamientos Inteligentes
 * @author Juan A. Recio-Garcia
 */

import pacman.controllers.GhostController;
import pacman.controllers.PacmanController;
import pacman.controllersOld.practica2.Ghosts;
import pacman.controllersOld.practica2.MsPacMan;

public class PacManEvaluator {
	
	public static final int MAX_GRUPOS = 7;
	public static final int TRIALS = 200;
	public static final String CURSO = "c1920";
	public static final String PRACTICA = "1";
	public static final String MsPACMAN_CLASS = "MsPacMan";
	public static final String GHOSTS_CLASS = "Ghosts";

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Executor executor = new Executor.Builder()
                .setTickLimit(4000)
                .setScaleFactor(3.0)
                .build();

        PacmanController msPacMan = new MsPacMan();
        GhostController[] lista_ghosts = new GhostController[MAX_GRUPOS];
        double suma = 0;
        //Instanciar los grupos
        for(int i = 0; i < MAX_GRUPOS; i++) {
        	if(i == 4) {
        		lista_ghosts[i] = new Ghosts();
        	}
        	else {
        		String paquete = String.format("es.ucm.fdi.ici.%s.practica%s.grupo0%s.", CURSO, PRACTICA, i + 1);
        		lista_ghosts[i] = (GhostController)Class.forName(paquete+ GHOSTS_CLASS).newInstance();  
        	}
        	suma += executor.runExperiment(msPacMan, lista_ghosts[i], 1000, msPacMan.getClass().getName() +  " - " + lista_ghosts[i].getClass().getName())[0].getAverage();
        }
        System.out.println(suma / 7);
        
        /*for(int grupo=1 ; grupo<=MAX_GRUPOS; grupo++)
        {
        	if(grupo == 6) {
        		lista_pacMan[grupo-1] = new MsPacMan();
        		lista_ghosts[grupo-1] = new Ghosts();
        	}
        	else {
        		String paquete = String.format("es.ucm.fdi.ici.%s.practica%s.grupo0%s.", CURSO, PRACTICA, grupo);
            	
            	try {            
            		lista_pacMan[grupo-1] = (PacmanController)Class.forName(paquete+ MsPACMAN_CLASS).newInstance();
            		lista_ghosts[grupo-1] = (GhostController)Class.forName(paquete+ GHOSTS_CLASS).newInstance();     

    		    }catch(Exception e) {
    		    	System.err.println("Error instanciando grupo "+grupo);
    		    	System.err.println(e);
    		    }
        	}
    	}
        
        //Ejecutar
	    int p = 0;
	    for(PacmanController pacMan: lista_pacMan)
	    {
	    	int g=0;
	    	for(GhostController ghosts: lista_ghosts)
	    	{
	    		if(p == 4) {
	    			try {  
	    	    		Stats[] stats = executor.runExperiment(pacMan, ghosts, TRIALS, pacMan.getClass().getName()+ " - " + ghosts.getClass().getName());
	    	    		resultados[p][g++] = stats[0];
	    	        	}catch(Exception e) {
	    	        		System.err.println("Error ejecutando pacman "+p+"  ghost: "+g);
	    	        		System.err.println(e);	
	    	        	}
	    		}
	            
	        }
	    	p++;
	    }

       // Mostrar
	    double suma = 0;
	    for(int i = 0; i < 6; i++) {
	    	
	    	suma += resultados[4][i].getAverage();
	    }
	    System.out.println(suma / 7);
	    
	   /* p=0;
        for(Stats[] result_pacman : resultados)
        {
        		double sumas = 0;
            	for(Stats s: result_pacman)
            	{
            		sumas += s.getAverage();
            		System.out.print(s.getAverage()+ "\t");
            	}
            	System.out.println(sumas / 7);
        	}
        }*/
	}
}