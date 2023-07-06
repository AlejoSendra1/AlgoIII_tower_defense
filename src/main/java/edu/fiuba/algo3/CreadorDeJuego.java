package edu.fiuba.algo3;

import edu.fiuba.algo3.modelo.Creador.CreadorDeMapa;
import edu.fiuba.algo3.modelo.Creador.CreadorEnemigos;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Excepciones.NoHayCamino;
import edu.fiuba.algo3.modelo.Excepciones.NoHayInicial;
import edu.fiuba.algo3.Interface.ControladorCompra;
import edu.fiuba.algo3.Interface.VisualizadorDeMapa;
import edu.fiuba.algo3.Interface.VisualizadorPanelJugador;
import edu.fiuba.algo3.modelo.Turnero;
import edu.fiuba.algo3.modelo.juego.Juego;
import edu.fiuba.algo3.modelo.juego.Mapa;
import edu.fiuba.algo3.vista.VistaEstadoJuego;
import edu.fiuba.algo3.vista.VistaJugador;
import edu.fiuba.algo3.vista.VistaSprays;

import java.util.ArrayList;
import java.util.LinkedList;


//
// ELIMINAR ESTA CLASE
//

public class CreadorDeJuego {

    public static void crearJuego(String pathArchivoEnemigos, String pathArchivoMapa, int tamanioMapa, String nombreDelJugador) throws NoHayCamino, NoHayInicial {

        CreadorEnemigos creadorEnemigos = new CreadorEnemigos();
        CreadorDeMapa creadorMapa = new CreadorDeMapa();
        Mapa mapa = creadorMapa.crearMapa(pathArchivoMapa, tamanioMapa);
        VisualizadorDeMapa visualizadorDeMapa = new VisualizadorDeMapa();
        VistaSprays vistaSprays = new VistaSprays(visualizadorDeMapa);
        ControladorCompra.getInstance().setVisualizadorMapa(visualizadorDeMapa);

        LinkedList<ArrayList<Enemigo>> enemigos = creadorEnemigos.crearEnemigosDeNivel(pathArchivoEnemigos, vistaSprays);

        mapa.cargarOleadas(enemigos);

        Juego.getInstance().reestablecerJuego();
        Juego.getInstance().setMapa(mapa);
        Juego.getInstance().cargarObserverParaDefensas(vistaSprays);
        Juego.getInstance().addObserver(new VistaEstadoJuego(visualizadorDeMapa));
        Juego.getInstance().setNombreDelJugador(nombreDelJugador);

        Turnero turnero = new Turnero();

        VisualizadorPanelJugador visualizadorPanelJugador = new VisualizadorPanelJugador(visualizadorDeMapa);

        visualizadorPanelJugador.inicializarPanelJugador(turnero);

        VistaJugador vistaJugador = new VistaJugador(visualizadorPanelJugador);

        Juego.getInstance().cargarObserverParaJugador(vistaJugador);

        Juego.getInstance().notifyObservers();

        visualizadorDeMapa.cargarMapa(mapa);
        visualizadorDeMapa.mostrar();
    }
}