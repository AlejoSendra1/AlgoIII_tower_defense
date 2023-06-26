package edu.fiuba.algo3.modelo.Defensas;

import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Enemigos.Topo;
import edu.fiuba.algo3.modelo.ObserverPropio.Emisor;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;

import java.util.ArrayList;

public class Construido implements EstadoConstruccion {
    public EstadoConstruccion pasoUnTurno(Defensa defensa){
        return new Construido();
    }

    public boolean estoyConstruida(){
        return true;
    }

    @Override
    public void atacar(TipoDeDefensa tipoDeDefensa, ArrayList<Enemigo> enemigos, Coordenada coordenada, int rangoAtaque, String nombre){
        for (Enemigo enemigo : enemigos) {
            if(enemigo.estaEnRango(coordenada, rangoAtaque, tipoDeDefensa)) {
                //emisor.notificarSubscriptores("log", nombre + " ataca a " + enemigo.representacionString() + " en " + enemigo.represtacionUbicacion());
                tipoDeDefensa.atacar(enemigo);
            }
        }
    }
}