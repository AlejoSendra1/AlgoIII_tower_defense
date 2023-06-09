package edu.fiuba.algo3.modelo.miscelanea;

import edu.fiuba.algo3.modelo.Creador.CreadorDeDefensas;
import edu.fiuba.algo3.modelo.Defensas.Defensa;
import edu.fiuba.algo3.modelo.juego.Credito;
import edu.fiuba.algo3.modelo.juego.Juego;
import edu.fiuba.algo3.modelo.juego.Jugador;

import java.util.ArrayList;
import java.util.HashMap;

public class Tienda { // se encargara de administrar los precios de las defensas y de comunicarse con
    //un CreadorDeDefensas para obtener las intacias de las torres
    private HashMap<String,Credito> catalogo;
    private CreadorDeDefensas creador;

    public Tienda(){
        catalogo = new HashMap<String,Credito>();
        catalogo.put("TorreBlanca",new Credito(10));
        catalogo.put("TorrePlateada",new Credito(20));
        catalogo.put("TrampaArenosa", new Credito(25));
        creador = new CreadorDeDefensas();
    }

    public ArrayList<String> catalogoDisponible(Credito cantidadRecursos){
        ArrayList<String> defensasDisponibles = new ArrayList<String>();

        //por cada torre en el catalogo
        for (String i : catalogo.keySet()){

            //si los creditos pasados por parametro cubren el costo de la torre, entonces se la agrega
            if (cantidadRecursos.esMayorOIgualQue(catalogo.get(i))){
                defensasDisponibles.add(i);
            }
        }

        return defensasDisponibles;
    }

    public Defensa vendeme(String unaDefensa){
        Credito cantidadACobrar = catalogo.get(unaDefensa);

        Juego.getInstance().descontarCreditosAlJugador(cantidadACobrar);

        return creador.crear(unaDefensa);
    }
}
