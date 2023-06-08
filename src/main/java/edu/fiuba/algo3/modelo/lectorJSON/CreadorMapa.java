package edu.fiuba.algo3.modelo.lectorJSON;

import edu.fiuba.algo3.modelo.Coordenada;
import edu.fiuba.algo3.modelo.Rocosa;
import edu.fiuba.algo3.modelo.Tierra;
import org.json.simple.JSONArray;

import java.util.concurrent.atomic.AtomicInteger;

public class CreadorMapa implements Creador {
    public Camino camino;
    public Mapa mapa;

    public CreadorMapa(){
        mapa = new Mapa();
        camino = new Camino(mapa);
    }

    public Object crear(){
        crearMapa(Lector.leer("ArchivosJson/mapa.json", "Mapa"));
        return mapa;
    }

    private void crearMapa(JSONArray jsonArray) {
        AtomicInteger coordY = new AtomicInteger(1);
        jsonArray.forEach(listaParcelas -> {
            coordY.getAndIncrement();
            AtomicInteger coordX = new AtomicInteger(1);
            ((JSONArray) listaParcelas).forEach(tipoDeTerreno -> {
                coordX.getAndIncrement();
                crearParcela((String) tipoDeTerreno, coordY.intValue(), coordX.intValue());
            });
        });
    }

    private void crearParcela(String tipoDeTerreno, int coordY, int coordX){
        Coordenada nuevaCoordenada = new Coordenada(coordX, coordY);
        if (tipoDeTerreno.equals("Rocoso")) {
            mapa.agregar(new Rocosa(nuevaCoordenada));
        }
        if (tipoDeTerreno.equals("Pasarela")) {
            camino.agregar(nuevaCoordenada);
        }
        if (tipoDeTerreno.equals("Tierra")) {
            mapa.agregar(new Tierra(nuevaCoordenada));
        }
    }
}
