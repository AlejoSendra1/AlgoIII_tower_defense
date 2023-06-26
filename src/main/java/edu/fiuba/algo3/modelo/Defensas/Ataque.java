package edu.fiuba.algo3.modelo.Defensas;

import edu.fiuba.algo3.modelo.Enemigos.Enemigo;
import edu.fiuba.algo3.modelo.Enemigos.Topo;


public class Ataque  implements TipoDeDefensa{
    private int danio;

    public Ataque(int danio){
        this.danio = danio;
    }
    @Override
    public void atacar(Enemigo enemigo){
        enemigo.recibirDanio(this.danio);
    }

}
