package edu.fiuba.algo3.adicionales.testUnitarios;

import edu.fiuba.algo3.modelo.Defensas.EstadoDeConstruccion.Construido;
import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.Defensas.EstadoDeConstruccion.EnConstruccion;
import edu.fiuba.algo3.modelo.Defensas.EstadoDeConstruccion.EstadoConstruccion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class testEstadoDeConstruccion {
    @Test
    public void test01EnConstruccionDevuelveFalseAlEstoyConstruidaSiSeCreaConUnTurno(){
        EstadoConstruccion estadoBajoPrueba = new EnConstruccion(1);

        assertFalse(estadoBajoPrueba.estoyConstruida());
    }
    @Test
    public void test02ConstruidoDevuelveInstanciaDeConstruidoDespuesDePasarUnTurno(){

        //Emisor mockedEmisor = mock(Emisor.class);
        Defensa mockedDefensa = mock(Defensa.class);

        EstadoConstruccion estadoBajoPrueba = new Construido();

        estadoBajoPrueba = estadoBajoPrueba.pasoUnTurno(mockedDefensa);

        assertTrue(estadoBajoPrueba instanceof Construido);
    }
    @Test
    public void test03EnConstruccionDevuelveInstanciaDeConstruidoDespuesDePasarLosTurnosCorrespondientes(){
        //Emisor mockedEmisor = mock(Emisor.class);
        Defensa mockedDefensa = mock(Defensa.class);

        EstadoConstruccion estadoBajoPrueba = new EnConstruccion(10);

        for(int i = 0 ; i < 10 ; i++){
            estadoBajoPrueba = estadoBajoPrueba.pasoUnTurno(mockedDefensa);
        }

        assertTrue(estadoBajoPrueba instanceof Construido);
    }
}
