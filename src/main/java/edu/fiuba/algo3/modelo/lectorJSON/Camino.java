package edu.fiuba.algo3.modelo.lectorJSON;

import edu.fiuba.algo3.modelo.Coordenada;
import edu.fiuba.algo3.modelo.Pasarela;
import edu.fiuba.algo3.modelo.PasarelaFinal;
import edu.fiuba.algo3.modelo.PasarelaIntermedia;

import java.util.ArrayList;

public class Camino {
   public ArrayList<Pasarela> camino;

   private Mapa mapa;

   public Camino(Mapa mapaNuevo) {
      camino = new ArrayList<Pasarela>();
      mapa = mapaNuevo;
   }

   public void agregar(Coordenada coordenadaNueva) {
      Pasarela nuevaPasarela;
      if (camino.isEmpty()) {
         nuevaPasarela = new PasarelaFinal(coordenadaNueva, null);
      } else {
         nuevaPasarela = new PasarelaIntermedia(coordenadaNueva, camino.get(camino.size()));
      }
      mapa.agregar(nuevaPasarela);
   }
}