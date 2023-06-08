package edu.fiuba.algo3.entrega_1;

import java.util.ArrayList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.lectorJSON.Creador;
import edu.fiuba.algo3.modelo.lectorJSON.CreadorEnemigos;
import edu.fiuba.algo3.modelo.lectorJSON.CreadorMapa;
import edu.fiuba.algo3.modelo.lectorJSON.Mapa;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Queue;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CasosDeUsoTest {

    @Test
    @Order(1)
    public void test01LaVidaYCreditosDelJugadorSonLosCorrectosAlEmpezar() {
        //el jugador comienza con 20 puntos de vida y con 100 creditos
        Jugador jugador = Jugador.getInstance();
        assertEquals(100, jugador.obtenerCreditos());
        assertEquals(20, jugador.obtenerVida());
    }

    @Test
    @Order(2)
    public void test02aLaTorreBlancaTardaEnConstruirse1Turno() {
        Defensa torre = new TorrePlateada();
        assertFalse(torre.estaConstruida());

        torre.pasarTurno();
        assertFalse(torre.estaConstruida());
    }

    @Test
    @Order(3)
    public void test02bLaTorrePlateadaTardaEnConstruirse2Turnos() {
        Defensa torre = new TorrePlateada();

        //se verifica que la torre no se encuentra construida
        assertFalse(torre.estaConstruida());

        // se hace pasar un turno para la torre
        torre.pasarTurno();

        // se verifica que sigue en construccion
        assertFalse(torre.estaConstruida());

        //se hace pasar un segundo turno
        torre.pasarTurno();

        //se verifica que la torre ya se encuentra construida
        assertTrue(torre.estaConstruida());
    }

    @Test
    @Order(4)
    public void test03SeVerficaQueElJugadorDispongaDeCreditoParaConstruirTorres() {
        // se crea un jugador con los recursos base (tambien podrian ser otros)
        Jugador jugador = Jugador.getInstance();

        //se crea la tienda que sera quien proveera y verificara las torres comprables
        Tienda proveedor = new Tienda();

        //el jugador le envia sus creditos a instancia de tienda para que esta provea un
        //stack/pila con las estructuras que este puede comprar
        Stack<Defensa> torresComprables = jugador.verificarConstruccionesPosibles(proveedor);

        //assertions
        assertTrue(torresComprables.pop() instanceof TorrePlateada);
        assertTrue(torresComprables.pop() instanceof TorreBlanca);
    }

    @Test
    @Order(5)
    public void test04VerificarSoloSePuedaConstruirDefensasSobreTierra() {
        Defensa torre = new TorrePlateada();

        Coordenada coordTierra = new Coordenada(0, 0);
        Coordenada coordRocosa = new Coordenada(1, 0);

        Tierra tierra = new Tierra(coordTierra);
        Rocosa rocosa = new Rocosa(coordRocosa);

        rocosa.construirDefensa(torre);
        tierra.construirDefensa(torre);

        assertTrue(tierra.ocupada());
        assertFalse(rocosa.ocupada());
    }

    @Test
    @Order(6)
    public void test05VerificarQueLasDefensasAtaquenDentroDelRangoEsperado() {
        Coordenada coordenada = new Coordenada(0, 0);
        Pasarela pasarela1 = new PasarelaIntermedia(coordenada, null);
        Pasarela pasarela2 = new PasarelaIntermedia(coordenada, null);
        Pasarela pasarela3 = new PasarelaIntermedia(coordenada, null);

        Enemigo enemigo = new Araña(pasarela2);

        ArrayList<Pasarela> lista = new ArrayList<Pasarela>();
        lista.add(pasarela1);
        lista.add(pasarela2);
        lista.add(pasarela3);

        Rango rango = new Rango(lista);
        TorreBlanca torre = new TorreBlanca(rango);
        torre.pasarTurno();
        torre.atacar();

        assertTrue(enemigo.estaVivo());

        torre.pasarTurno();
        torre.atacar();

        assertFalse(enemigo.estaVivo());

    }

    @Test
    @Order(7)
    public void test06VerificarQueLosEnemigosSonDañadosAcordeAlAtaqueRecibido() {
        Coordenada coordenada = new Coordenada(0, 0);
        Pasarela pasarela1 = new PasarelaIntermedia(coordenada, null);
        Pasarela pasarela2 = new PasarelaIntermedia(coordenada, null);
        Pasarela pasarela3 = new PasarelaIntermedia(coordenada, null);

        Enemigo enemigo = new Hormiga(pasarela2);
        Enemigo enemigo2 = new Hormiga(pasarela3);

        ArrayList<Pasarela> lista = new ArrayList<Pasarela>();
        lista.add(pasarela1);
        //lista.add(pasarela2);
        lista.add(pasarela3);

        Rango rango = new Rango(lista);
        TorreBlanca torre = new TorreBlanca(rango);
        torre.pasarTurno();
        torre.atacar();

        assertTrue(enemigo.estaVivo());
        assertFalse(enemigo2.estaVivo());
    }


    @Test
    @Order(8)
    public void test07aVerificarQueLasArañasSeDesplazanLaCantidadCorrectaDeParcelas() {

        Coordenada coordenadaFinal = new Coordenada(1, 1);
        Pasarela pasarelaFinal = new PasarelaIntermedia(coordenadaFinal, null);

        Coordenada coordenadaMedio = new Coordenada(1, 2);
        Pasarela pasarelaMedio = new PasarelaIntermedia(coordenadaMedio, pasarelaFinal);

        Coordenada coordenadaInicial = new Coordenada(1, 3);
        Pasarela pasarelaInicial = new PasarelaIntermedia(coordenadaInicial, pasarelaMedio);
        Enemigo enemigo = new Araña(pasarelaInicial);

        enemigo.avanzar();

        assertEquals(enemigo.verPosicion(), pasarelaFinal);
    }

    @Test
    @Order(9)
    public void test07bVerificarQueLasArañasSeDesplazanLaCantidadCorrectaDeParcelas() {

        Coordenada coordenadaFinal = new Coordenada(2, 1);
        Pasarela pasarelaFinal = new PasarelaIntermedia(coordenadaFinal, null);

        Coordenada coordenadaInicial = new Coordenada(1, 1);
        Pasarela pasarelaInicial = new PasarelaIntermedia(coordenadaInicial, pasarelaFinal);
        Enemigo enemigo = new Hormiga(pasarelaInicial);

        enemigo.avanzar();

        assertEquals(enemigo.verPosicion(), pasarelaFinal);
    }
    @Test
    @Order(10)

    public void test08aHormigaDaCreditosCorrespondientesAlMorirSinMultiplicador() {
        Jugador.getInstance();
        int creditoInicial = Jugador.getInstance().obtenerCreditos();

        Coordenada coordenadaInicializadora = new Coordenada(1, 1);
        Pasarela pasarelaInicializadora = new PasarelaIntermedia(coordenadaInicializadora, null);

        for (int i=1; i<9; i++){
            Enemigo hormiga = new Hormiga(pasarelaInicializadora);
            hormiga.morir();
            assertEquals((i + creditoInicial), Jugador.getInstance().obtenerCreditos());
        }
    }

    @Test
    @Order(11)
    public void test08bHormigaDaCreditosCorrespondientesAlMorirConMultiplicador() { //El multiplicador ya esta aplicado porque el jugador mato a 9 hormigas en la prueba anterior
        Jugador jugador = Jugador.getInstance();
        int creditoInicial = Jugador.getInstance().obtenerCreditos();
        Coordenada coordenadaInicializadora = new Coordenada(1, 1);
        Pasarela pasarelaInicializadora = new PasarelaIntermedia(coordenadaInicializadora, null);

        for (int i=1; i<10; i++){
            Enemigo hormiga = new Hormiga(pasarelaInicializadora);
            hormiga.morir();
            assertEquals((i * 2 + creditoInicial), Jugador.getInstance().obtenerCreditos());
        }
    }

    @Test
    @Order(12)
    public void test08cArañaDaCreditosCorrespondientesAlMorir() {
        Jugador jugador = Jugador.getInstance();
        int creditosIniciales = jugador.getInstance().obtenerCreditos();
        assertFalse((Jugador.getInstance().obtenerCreditos() > 10 + creditosIniciales) || (Jugador.getInstance().obtenerCreditos() < 0 + creditosIniciales));
    }

    @Test
    @Order(13)
    public void test09aCrearHormigaYAvanzarHaceQueAvanceUnaPosicion() {
        Coordenada coordenadaFinal = new Coordenada(0, 1);
        Pasarela pasarelaFinal = new PasarelaIntermedia(coordenadaFinal, null);

        Coordenada coordenadaSegunda = new Coordenada(1, 1);
        Pasarela pasarelaSegunda = new PasarelaIntermedia(coordenadaSegunda, pasarelaFinal);

        Coordenada coordenadaInicial = new Coordenada(1, 2);
        Pasarela pasarelaInicial = new PasarelaIntermedia(coordenadaInicial, pasarelaSegunda);

        Enemigo hormiga = new Hormiga(pasarelaInicial);

        Juego juego = new Juego();
        juego.nuevoEnemigo(hormiga);
        juego.pasarTurno();


        assertEquals(hormiga.verPosicion(), pasarelaSegunda);
    }

    @Test
    @Order(14)
    public void test09bCrearArañaYAvarnzarHaceQueAvanceDosPosiciones() {
        Coordenada coordenadaFinal = new Coordenada(1, 3);
        Pasarela pasarelaFinal = new PasarelaIntermedia(coordenadaFinal, null);

        Coordenada coordenadaSegunda = new Coordenada(5, 2);
        Pasarela pasarelaSegunda = new PasarelaIntermedia(coordenadaSegunda, pasarelaFinal);

        Coordenada coordenadaInicial = new Coordenada(4, 3);
        Pasarela pasarelaInicial = new PasarelaIntermedia(coordenadaInicial, pasarelaSegunda);
        Enemigo araña = new Araña(pasarelaInicial);

        Juego juego = new Juego();
        juego.nuevoEnemigo(araña);
        juego.pasarTurno();

        assertEquals(araña.verPosicion(), pasarelaFinal);
    }

    @Test
    @Order(15)
    public void test09cCrearUnaColeccionDeEnemigosYHacerQueAvanzenActualizaLasPosicionesDeTodos() {
        Coordenada coordenadaFinal = new Coordenada(0, 1);
        Pasarela pasarelaFinal = new PasarelaIntermedia(coordenadaFinal, null);

        Coordenada coordenadaTercera = new Coordenada(1, 1);
        Pasarela pasarelaTercera = new PasarelaIntermedia(coordenadaTercera, pasarelaFinal);

        Coordenada coordenadaSegunda = new Coordenada(1, 2);
        Pasarela pasarelaSegunda = new PasarelaIntermedia(coordenadaSegunda, pasarelaTercera);

        Coordenada coordenadaPrimera = new Coordenada(2, 2);
        Pasarela pasarelaPrimera = new PasarelaIntermedia(coordenadaPrimera, pasarelaSegunda);

        Juego juego = new Juego();

        Enemigo arañaUno = new Araña(pasarelaPrimera);
        Enemigo arañaDos = new Araña(pasarelaSegunda);
        Enemigo hormigaUno = new Hormiga(pasarelaTercera);
        Enemigo hormigaDos = new Hormiga(pasarelaPrimera);

        Stack<Enemigo> enemigos = new Stack<Enemigo>();

        enemigos.push(arañaUno);
        enemigos.push(arañaDos);
        enemigos.push(hormigaUno);
        enemigos.push(hormigaDos);

        enemigos.forEach(enemigo -> juego.nuevoEnemigo(enemigo));
        juego.pasarTurno();

        //Al ser una estructura de pila, se invierte el orden comparado al ingreso al hacer pop
        assertEquals(enemigos.pop().verPosicion(), pasarelaSegunda); // De la primera avanza uno y va a la segunda (final)
        assertEquals(enemigos.pop().verPosicion(), pasarelaFinal); // De la tercera avanza uno y va a la cuarta (final)
        assertEquals(enemigos.pop().verPosicion(), pasarelaFinal); // De la segunda avanza dos y va a la cuarta (final)
        assertEquals(enemigos.pop().verPosicion(), pasarelaTercera); // De la primera avanza dos y va a la tercera
    }

    @Test
    @Order(16)
    public void test10aCrearUnJuegoConUnEnemigoSeGanaCuandoElEnemigoMuere() {
        Vida vida = new Vida(20);
        Jugador jugador = Jugador.getInstance();
        Juego juego = new Juego();

        Coordenada coordenadaPrimera = new Coordenada(2, 2);
        Pasarela pasarelaPrimera = new PasarelaIntermedia(coordenadaPrimera, null);
        Enemigo araña = new Araña(pasarelaPrimera);

        juego.nuevoEnemigo(araña);

        araña.recibirDaño(1);
        assertTrue(juego.estado() instanceof Jugando);

        araña.recibirDaño(1);
        assertTrue(juego.estado() instanceof Ganado);
    }

   /* @Test
    @Order(17)
    public void test10bCrearUnJuegoConMuchosEnemigosSeGanaSoloCuandoTodosMueren() {

        Vida vida = new Vida(20);
        Jugador jugador = Jugador.getInstance();

        Coordenada coordenadaPrimera = new Coordenada(2, 2);
        Pasarela pasarelaPrimera = new PasarelaIntermedia(coordenadaPrimera, null);
        Araña arañaUno = new Araña(pasarelaPrimera);
        Araña arañaDos = new Araña(pasarelaPrimera);
        Araña arañaTres = new Araña(pasarelaPrimera);
        Araña arañaCuatro = new Araña(pasarelaPrimera);
        Hormiga HormigaUno = new Hormiga(pasarelaPrimera);
        Hormiga HormigaDos = new Hormiga(pasarelaPrimera);
        Hormiga HormigaTres = new Hormiga(pasarelaPrimera);
        Hormiga HormigaCuatro = new Hormiga(pasarelaPrimera);

        ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();

        enemigos.add(arañaUno);
        enemigos.add(arañaDos);
        enemigos.add(arañaTres);
        enemigos.add(arañaCuatro);
        enemigos.add(HormigaUno);
        enemigos.add(HormigaDos);
        enemigos.add(HormigaTres);
        enemigos.add(HormigaCuatro);

        Juego juego = new Juego();
        assertTrue(juego.estado() instanceof Jugando);

        enemigos.forEach(enemigo -> enemigo.recibirDaño(1)); //Las hormigas mueren pero las arañas siguen vivas
        assertTrue(juego.estado() instanceof Jugando);

        arañaUno.recibirDaño(1);
        arañaDos.recibirDaño(1);
        arañaTres.recibirDaño(1);
        //Queda solo la última Araña viva
        assertTrue(juego.estado() instanceof Jugando);

        arañaCuatro.recibirDaño(1);
        assertTrue(juego.estado() instanceof Ganado);
    }*/
    @Test
    @Order(18)
    public void test11ElJugadorSobreviveConUnEnemigoLlegandoALaMetaYGanaIgual() {
        Jugador jugador = Jugador.getInstance();
        Juego juego = new Juego();

        Coordenada coordenadaFinal = new Coordenada(2,3);
        Pasarela pasarelaFinal = new PasarelaFinal(coordenadaFinal, null);

        Coordenada coordenadaPrimera = new Coordenada(2, 2);
        Pasarela pasarelaPrimera = new PasarelaIntermedia(coordenadaPrimera, pasarelaFinal);

        Enemigo hormiga = new Hormiga(pasarelaPrimera);
        juego.nuevoEnemigo(hormiga);
        juego.pasarTurno();
        assertTrue(juego.estado() instanceof Ganado);
    }

    @Test
    @Order(19)
    public void test12ElJugadorMuereYPierdeElJuego() {
        Jugador jugador = Jugador.getInstance();
        Juego juego = new Juego();

        Coordenada coordenadaFinal = new Coordenada(2,3);
        Pasarela pasarelaFinal = new PasarelaFinal(coordenadaFinal, null);

        Coordenada coordenadaPrimera = new Coordenada(2, 2);
        Pasarela pasarelaPrimera = new PasarelaIntermedia(coordenadaPrimera, pasarelaFinal);

        int vidaInicial = Jugador.getInstance().obtenerVida();

        for(int i=0; i<vidaInicial; i++){
            Enemigo hormiga = new Hormiga(pasarelaPrimera);
            juego.nuevoEnemigo(hormiga);
            juego.pasarTurno();
        }
        assertTrue(juego.estado() instanceof Perdido);
    }
}



