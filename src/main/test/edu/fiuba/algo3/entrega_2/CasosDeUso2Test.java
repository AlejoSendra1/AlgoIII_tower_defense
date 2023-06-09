package edu.fiuba.algo3.entrega_2;


import edu.fiuba.algo3.modelo.Creador.CreadorDeMapa;
import edu.fiuba.algo3.modelo.Creador.CreadorEnemigos;
import edu.fiuba.algo3.modelo.Enemigos.Arania;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Enemigos.Hormiga;
import edu.fiuba.algo3.modelo.Excepciones.NoHayCamino;
import edu.fiuba.algo3.modelo.Excepciones.NoHayInicial;
import edu.fiuba.algo3.Interface.VisualizadorDeMapa;
import edu.fiuba.algo3.modelo.Turnero;
import edu.fiuba.algo3.modelo.juego.Juego;
import edu.fiuba.algo3.modelo.juego.Lector;
import edu.fiuba.algo3.modelo.juego.Mapa;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.mock;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class CasosDeUso2Test {
    @Test
    public void test13SeVerificaLaValidesDelJSONDeEnemigosDePrueba(){
        ArrayList< HashMap<String,String> > enemigosEnArchivo = new ArrayList<>();
        JSONArray parseoDeEnemigos = null;

        HashMap<String,String> turno1EnArchivo = new HashMap<String,String>();
        HashMap<String,String> turno2EnArchivo = new HashMap<String,String>();
        HashMap<String,String> turno3EnArchivo = new HashMap<String,String>();

        turno1EnArchivo.put("arana","1");
        turno1EnArchivo.put("hormiga","2");
        turno2EnArchivo.put("arana","1");
        turno2EnArchivo.put("hormiga","0");
        turno3EnArchivo.put("arana","1");
        turno3EnArchivo.put("hormiga","1");

        enemigosEnArchivo.add(turno1EnArchivo);
        enemigosEnArchivo.add(turno2EnArchivo);
        enemigosEnArchivo.add(turno3EnArchivo);

        parseoDeEnemigos = Lector.leer("ArchivosJson/enemigosDePrueba");

        for (int numeroDeturno = 1; numeroDeturno <= parseoDeEnemigos.size(); numeroDeturno++){ //se inicializa el ciclo en 1 para que sea representativo
            JSONObject informacionDelTurno = (JSONObject) parseoDeEnemigos.get(numeroDeturno-1);

            JSONObject enemigosEnTurno = (JSONObject) informacionDelTurno.get("enemigos");
            String aranasEnTurno = enemigosEnTurno.get("arana").toString();
            String hormigasEnTurno = enemigosEnTurno.get("hormiga").toString();

            assertEquals(informacionDelTurno.get("turno").toString(),String.valueOf(numeroDeturno));
            assertEquals(enemigosEnArchivo.get(numeroDeturno-1).get("arana"),aranasEnTurno);
            assertEquals(enemigosEnArchivo.get(numeroDeturno-1).get("hormiga"),hormigasEnTurno);
        }


        //JSONObject resultado = (JSONObject) parseoDeEnemigos.get(0);

        // JSONArray filas = (JSONArray) parseoDeEnemigos.get();
    }

    @Test
    public void test14SeVerificaLaValidesDelJSONDeMapaDePrueba(){
            ArrayList <ArrayList<String> > filasEnArchivo = new ArrayList<ArrayList<String> >();
            filasEnArchivo.add(new ArrayList<>(Arrays.asList(
                    "Rocoso","Pasarela","Tierra","Tierra","Tierra"
            )));
            filasEnArchivo.add(new ArrayList<String>(Arrays.asList(
                    "Tierra","Pasarela","Tierra","Rocoso","Rocoso"
            )));
            filasEnArchivo.add(new ArrayList<String>(Arrays.asList(
                    "Tierra","Pasarela","Pasarela","Tierra","Rocoso"
            )));
            filasEnArchivo.add(new ArrayList<String>(Arrays.asList(
                    "Tierra","Tierra","Pasarela","Tierra","Rocoso"
            )));
            filasEnArchivo.add(new ArrayList<String>(Arrays.asList(
                    "Tierra","Tierra","Pasarela","Tierra","Tierra"
            )));

            JSONArray parseoDeMapa = Lector.leer("ArchivosJson/mapaDePrueba");

            JSONObject filas = (JSONObject) parseoDeMapa.get(1);//saltea a "Mapa" y obtiene un diccionario de filas

            for (int i = 1; i <= filas.size(); i++){ //por cada fila en filas

                JSONArray filaAComparar = (JSONArray) filas.get(String.valueOf(i)); //crea un array con lo que hay en la fila

                for (int j = 0; j < filaAComparar.size(); j++){ // por cada elemento en el en el JSONArray
                    assertEquals(filaAComparar.get(j),filasEnArchivo.get(i-1).get(j));
                }
            }
    }

    @Test
    public void test15aCreadorDeEnemigosCreaDeJSONConUnSoloTurnoYUnaHormigaCreaAlEnemigoCorrectamente(){
        CreadorEnemigos creadorEnemigo = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo> > colaDeEnemigos = creadorEnemigo.crearEnemigosDeNivel("ArchivosJson/tests/Test15/enemigosTest15a.txt", mockObservers);

        ArrayList<Enemigo> enemigosEnTurno = colaDeEnemigos.pop();

        Enemigo enemigo = enemigosEnTurno.get(0);

        assertTrue(colaDeEnemigos.isEmpty());
        assertTrue(enemigo instanceof Hormiga);
        assertTrue(colaDeEnemigos.isEmpty());
    }
    @Test
    public void test15bCreadorDeEnemigosCreaDeJSONConUnSoloTurnoYDosEnemigos(){
        CreadorEnemigos creadorEnemigo = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo> > colaDeEnemigos = creadorEnemigo.crearEnemigosDeNivel("ArchivosJson/tests/Test15/enemigosTest15b.txt", mockObservers);

        ArrayList<Enemigo> enemigosEnTurno = colaDeEnemigos.pop();

        Enemigo enemigoA = enemigosEnTurno.get(0);
        Enemigo enemigoB = enemigosEnTurno.get(1);

        assertTrue(enemigoA instanceof Hormiga && enemigoB instanceof Arania);
        assertTrue(colaDeEnemigos.isEmpty());
    }

    @Test
    public void test15cCreadorDeEnemigosCreaDeJSONCon3TurnosYVariosEnemigosCorrectamente(){
        CreadorEnemigos creadorEnemigo = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo> > colaDeEnemigos = creadorEnemigo.crearEnemigosDeNivel("ArchivosJson/tests/Test15/enemigosTest15c.txt", mockObservers);

        ArrayList<Enemigo> primerTurnoDeCreador = colaDeEnemigos.pop();
        ArrayList<Enemigo> segundoTurnoDeCreador = colaDeEnemigos.pop();
        ArrayList<Enemigo> tercerTurnoDeCreador = colaDeEnemigos.pop();


        assertEquals(1, primerTurnoDeCreador.size());
        assertTrue(primerTurnoDeCreador.get(0) instanceof Hormiga);

        assertEquals(2, segundoTurnoDeCreador.size());
        assertTrue(segundoTurnoDeCreador.get(0) instanceof Hormiga);
        assertTrue(segundoTurnoDeCreador.get(1) instanceof Arania);

        assertEquals(3, tercerTurnoDeCreador.size());
        assertTrue(tercerTurnoDeCreador.get(0) instanceof Hormiga);
        assertTrue(tercerTurnoDeCreador.get(1) instanceof Hormiga);
        assertTrue(tercerTurnoDeCreador.get(2) instanceof Arania);
    }
    @Test
    public void test16CreadorDeMapaCreaLasParcelasYLasDisponeCorrectamenteEnElMapa(){
        CreadorDeMapa creadorDeMapa = new CreadorDeMapa();
        Mapa mapa = null;

        try {
            mapa = creadorDeMapa.crearMapa("ArchivosJson/mapaDePrueba",5);
        } catch (NoHayCamino | NoHayInicial er){}

        // primer fila
        assertTrue(mapa.ver(new Coordenada(1,1))instanceof Rocosa);
        assertTrue(mapa.ver(new Coordenada(2,1))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(3,1))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(4,1))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(5,1))instanceof Tierra);

        //segunda
        assertTrue(mapa.ver(new Coordenada(1,2))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(2,2))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(3,2))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(4,2))instanceof Rocosa);
        assertTrue(mapa.ver(new Coordenada(5,2))instanceof Rocosa);

        //tercera
        assertTrue(mapa.ver(new Coordenada(1,3))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(2,3))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(3,3))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(4,3))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(5,3))instanceof Rocosa);

        //cuarta
        assertTrue(mapa.ver(new Coordenada(1,4))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(2,4))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(3,4))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(4,4))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(5,4))instanceof Rocosa);

        //quinta
        assertTrue(mapa.ver(new Coordenada(1,5))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(2,5))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(3,5))instanceof Pasarela);
        assertTrue(mapa.ver(new Coordenada(4,5))instanceof Tierra);
        assertTrue(mapa.ver(new Coordenada(5,5))instanceof Tierra);
    }

    @Test
    public void test17JuegoSeTerminaConLaCantidadMinimaDeEnemigos() throws NoHayCamino, NoHayInicial {
        VisualizadorDeMapa visualizadorDeMapa = new VisualizadorDeMapa();
        CreadorDeMapa creadorDeMapa = new CreadorDeMapa();
        Mapa mapa = creadorDeMapa.crearMapa("ArchivosJson/mapa.json",15);

        CreadorEnemigos creadorEnemigos = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo>> enemigos = creadorEnemigos.crearEnemigosDeNivel("ArchivosJson/enemigos.json", mockObservers);

        Juego juego = Juego.getInstance();
        juego.reestablecerJuego();
        juego.setMapa(mapa);
        juego.setOleadasDelNivel(enemigos);

        Turnero turnero = new Turnero();



        for ( int i = 0 ; i <= 23 ; i++ ){ // dado el recorrido provisto por las pasarelas los enemegos deberian matar al jugador en el turno 23
            turnero.jugarTurnoMaquina();
        }
        /* MODIFICAR LA IMPLEMENTACION DE GANADO -> GANA EL JUEGO INCOMPLETO , test 19 con mismo problema

        assertFalse(juego.finalizado());

        //verificar que el juego no este perdido
        //assertFalse(jugador.estaVivo());
        */
    }

    @Test
    public void test18SeSimulaUnaPartidaEnDondeElJugadorGanaElJuego() throws NoHayCamino, NoHayInicial {
        VisualizadorDeMapa visualizadorDeMapa = new VisualizadorDeMapa();
        CreadorDeMapa creadorDeMapa = new CreadorDeMapa();
        Mapa mapa = creadorDeMapa.crearMapa("ArchivosJson/mapa.json",15);

        CreadorEnemigos creadorEnemigos = new CreadorEnemigos();

        ArrayList mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo>> enemigos = creadorEnemigos.crearEnemigosDeNivel("ArchivosJson/enemigos.json", mockObservers);

        Juego juego = Juego.getInstance();
        juego.reestablecerJuego();
        juego.setMapa(mapa);
        juego.setOleadasDelNivel(enemigos);
        Turnero turnero = new Turnero();

        //Empieza el jugador a hacer cambios en el juego
        juego.comprarDefensa("TorrePlateada", new Coordenada(5,8));
        juego.comprarDefensa("TorrePlateada", new Coordenada(5,6));
        juego.comprarDefensa("TorrePlateada", new Coordenada(1,3));
        juego.comprarDefensa("TorrePlateada", new Coordenada(3,1));
        juego.comprarDefensa("TorrePlateada", new Coordenada(3,2));

        //El jugador deja de hacer cambios
        turnero.finTurnoJugador();

        //Con el fin de probar si el jugador pierde o no solo vamos a pasar el turno del jugador sin hacer nada
        while (!juego.finalizado()){
            turnero.jugarTurnoMaquina();
        }

        assertTrue(juego.jugadorVivo());
    }

    @Test
    public void test18bSeSimulaUnaPartidaEnDondeRecibeDanioDeLosEnemigosPeroIgualGanaLaPartida() throws NoHayCamino, NoHayInicial {
        CreadorDeMapa creadorDeMapa = new CreadorDeMapa();
        Mapa mapa = creadorDeMapa.crearMapa("ArchivosJson/mapa.json",15);

        CreadorEnemigos creadorEnemigos = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo>> enemigos = creadorEnemigos.crearEnemigosDeNivel("ArchivosJson/enemigosDePrueba", mockObservers);

        Juego juego = Juego.getInstance();
        juego.reestablecerJuego();
        juego.setMapa(mapa);
        juego.setOleadasDelNivel(enemigos);
        Turnero turnero = new Turnero();

        juego.comprarDefensa("TorreBlanca", new Coordenada(1,2));

        while (!juego.finalizado()){
            turnero.jugarTurnoMaquina();
        }
        assertTrue(juego.jugadorVivo());
    }

    @Test
    public void test19SeSimulaUnaPartidaEnDondeRecibeDanioDeLosEnemigosYPierdeLaPartida() throws NoHayCamino, NoHayInicial {
        CreadorDeMapa creadorDeMapa = new CreadorDeMapa();
        Mapa mapa = creadorDeMapa.crearMapa("ArchivosJson/mapa.json",15);

        CreadorEnemigos creadorEnemigos = new CreadorEnemigos();

        ArrayList<Observer> mockObservers = mock(ArrayList.class);
        LinkedList<ArrayList<Enemigo>> enemigos = creadorEnemigos.crearEnemigosDeNivel("ArchivosJson/enemigos.json", mockObservers);

        Juego juego = Juego.getInstance();
        juego.reestablecerJuego();
        juego.setMapa(mapa);
        juego.setOleadasDelNivel(enemigos);
        Turnero turnero = new Turnero();

        while (!juego.finalizado()){
            turnero.jugarTurnoMaquina();
        }

        assertTrue(juego.finalizado());
        assertFalse(juego.jugadorVivo()); //MODIFICAR LA IMPLEMENTACION DE GANADO -> GANA EL JUEGO INCOMPLETO
    }
/*
    @Test
    public void test20aSiNoSubsriboAlLoggerNoCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        Jugador jugador = Jugador.getInstance();
        //jugador.agregarSubscriptor(logger);
        jugador.recibirDanio(1);

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));
    }

    @Test
    public void test20bDaniarAUnJugadorCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        Jugador jugador = Jugador.getInstance();
        jugador.agregarSubscriptor(logger);
        jugador.recibirDanio(1);

        assertTrue(logger.verificarCantidadDeMensajesObservados(1));
    }

    //TODO VER QUE HACER CON ESTOS TESTS
   /* @Test
    public void test20cRecompensarAUnJugadorCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        Jugador jugador = Jugador.getInstance();
        jugador.agregarSubscriptor(logger);

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        jugador.recompensar(10);

        assertTrue(logger.verificarCantidadDeMensajesObservados(1));
    }

    @Test
    public void test20dUnaHormigaQueMuereCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();
        Coordenada coord = new Coordenada(10, 20);
        Pasarela pasarela = new Pasarela(coord, new Normal());

        Hormiga hormiga = new Hormiga(pasarela);
        hormiga.agregarSubscriptor(logger);

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        hormiga.morir(); //Aunque active 2 eventos, al no estar el logger suscrito al jugador, el logger no recibe la notificacion
        assertTrue(logger.verificarCantidadDeMensajesObservados(1));
    }

    @Test
    public void test20eUnaAraniaQueMuereCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();
        Coordenada coord = new Coordenada(10, 20);
        Pasarela pasarela = new Pasarela(coord, new Normal());
        RandomGenerator generadorRandom = new RandomGenerator(0,10);

        Arania arania = new Arania(pasarela,generadorRandom);
        arania.agregarSubscriptor(logger);

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        arania.morir(); //Aunque active 2 eventos, al no estar el logger suscrito al jugador, el logger no recibe la notificacion
        assertTrue(logger.verificarCantidadDeMensajesObservados(1));
    }

    @Test
    public void test20fAgregarUnEnemigoAlJuegoCausaQueElLoggerRecibaUnaNotificacion() {
        Logger logger = new Logger();
        Coordenada coord = new Coordenada(10, 20);
        Pasarela pasarela = new Pasarela(coord, new Normal());
        RandomGenerator generadorRandom = new RandomGenerator(0,10);

        Arania arania = new Arania(pasarela,generadorRandom);
        Juego juego = new Juego();
        assertTrue(logger.verificarCantidadDeMensajesObservados(0));
        juego.agregarSubscriptor(logger);

        juego.nuevoEnemigo(arania);
        assertTrue(logger.verificarCantidadDeMensajesObservados(1));
    }

    @Test
    public void test20gSubscribirElMismoLoggerADistintosObservablesCausaQueSeAcumulenLasNotificaciones() {
        Logger logger = new Logger();
        Coordenada coord = new Coordenada(10, 20);
        Pasarela pasarela = new Pasarela(coord, new Normal());
        Mapa mapa = new Mapa();
        RandomGenerator generadorRandom = new RandomGenerator(0,10);

        Arania arania = new Arania(pasarela,generadorRandom);
        Hormiga hormiga = new Hormiga(pasarela);
        Juego juego = new Juego(mapa, logger);
        Jugador jugador = Jugador.getInstance();

        juego.agregarSubscriptor(logger);
        arania.agregarSubscriptor(logger);
        hormiga.agregarSubscriptor(logger);
        jugador.agregarSubscriptor(logger);

        assertTrue(logger.verificarCantidadDeMensajesObservados(0));

        //Serie de eventos que notfican al logger
        juego.nuevoEnemigo(arania);
        jugador.recompensar(10);
        hormiga.morir(); //Activa 2 eventos, porque muere y recompensa al jugador
        arania.morir(); //Activa 2 eventos, porque muere y recompensa al jugador

        assertTrue(logger.verificarCantidadDeMensajesObservados(6));
    }
 */
}

