package edu.fiuba.algo3.modelo.Enemigos;

import edu.fiuba.algo3.modelo.juego.Juego;

public class Lechuza extends Enemigo{

    private String sonido = null;

    public Lechuza(){
        super(5, 0, 5);
        this.tipoMovimiento = new MovimientoCateto(this);
    }

    @Override
    public void recibirDanio(int danio) {
        super.recibirDanio(danio);
        if (vida.obtenerPorcentajeDadoInicial(5) <= 50 ){
            //this.tipoMovimiento = new MovimientoHipotenusa(this);
        }
    }

    @Override
    public String verSonido() {
        return sonido;
    }

    @Override
    public void daniarJugador() {
        super.daniarJugador();
        //se debe  agregar la destruccion de la torre
        System.out.println("Llego a daniar jugador");
        Juego.getInstance().destruirDefensaMasAntigua();
    }

    public void morir(){
        sonido = "Morir";
    }

    public String representacionString() {
        return "Lechuza";
    }

}
