package edu.fiuba.algo3.modelo.Defensas.EstadoDeConstruccion;

import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.Defensas.TipoDeDefensa;
import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.vista.Sprayable;

import java.util.ArrayList;

public class Destruida implements EstadoConstruccion {
    public String sonido = "";

    @Override
    public EstadoConstruccion pasoUnTurno(Defensa defensa) {
        return this;
    }

    @Override
    public boolean estoyConstruida() {
        return false;
    }

    @Override
    public void atacar(TipoDeDefensa tipoDeDefensa, ArrayList<Enemigo> enemigos, Coordenada coordenada, int rangoAtaque, String nombre) {}
    @Override
    public String sprayID(Sprayable sprayable) {
        return "destruida";
    }

    @Override
    public boolean destruir( Defensa defensa) {
        return false;
    }
}
