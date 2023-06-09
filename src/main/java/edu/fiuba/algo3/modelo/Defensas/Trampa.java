package edu.fiuba.algo3.modelo.Defensas;

import edu.fiuba.algo3.modelo.Enemigos.Enemigo;

import edu.fiuba.algo3.modelo.Enemigos.Visitor;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import java.util.ArrayList;
import edu.fiuba.algo3.modelo.Enemigos.Efecto.Relentizado;


public class Trampa implements TipoDeDefensa{
    private final double porcentajeRelentizacion;

    public Trampa(double porcentajeRelentizacion){
        this.porcentajeRelentizacion = porcentajeRelentizacion;
    }

    @Override
    public void atacar(ArrayList<Enemigo> enemigos, Coordenada coordenada, int rangoAtaque){

        for (Enemigo enemigo : enemigos) {
            if ( enemigo.estaEnRango(coordenada, rangoAtaque, this) ){

                enemigo.setEfectoEnemigo(new Relentizado(1, this.porcentajeRelentizacion));
            }
        }
    }

    public boolean accept(Visitor enemigo){
        return enemigo.esVisiblePara(this);
    }
}
