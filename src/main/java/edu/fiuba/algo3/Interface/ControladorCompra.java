package edu.fiuba.algo3.Interface;

import edu.fiuba.algo3.modelo.juego.Juego;
import edu.fiuba.algo3.modelo.miscelanea.Coordenada;
import edu.fiuba.algo3.modelo.parcelas.Parcela;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ControladorCompra {
    private String defensaSeleccionada;

    private static final ControladorCompra INSTANCE = new ControladorCompra();

    private static VisualizadorDeMapa visualizadorMapa;

    private ControladorCompra() {
        super();
    }

    public void seleccionarDefensa(String unaDefensa) { this.defensaSeleccionada = unaDefensa; }

    public void setVisualizadorMapa(VisualizadorDeMapa visualizadorMapa) { this.visualizadorMapa = visualizadorMapa; }
    public void cancelarSeleccionDefensa() { this.defensaSeleccionada = null; }
    public static ControladorCompra getInstance() { return INSTANCE; }

    public void ponerDefensaEn(int coordX, int coordY) {
        Coordenada coord = new Coordenada(coordX, coordY);
        if (!esCompraValida(coord)) {
            Text mensajeError = new Text("Parcela invalida para construir");
            mensajeError.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            visualizadorMapa.mostrarMensajeError(mensajeError);
            return;
        }
        Juego.getInstance().comprarDefensa(defensaSeleccionada, coord);
        Juego.getInstance().notificar("Defensa");
        actualizarPanelTienda();
        cancelarSeleccionDefensa();
        }

    public void actualizarPanelTienda() {
        try {
            visualizadorMapa.actualizarPanelTienda();
        } catch (NullPointerException e) {

        }
    }
    private boolean esCompraValida(Coordenada coordenada){
        if (defensaSeleccionada == null) {
            return false;
        }
        Parcela parcela = Juego.getInstance().verParcelaEn(coordenada);
        if (!parcela.puedeConstruir(defensaSeleccionada)) {
            return false;
        }
        if (Juego.getInstance().esInicioOFinal(Juego.getInstance().verParcelaEn(coordenada))) {
            return false;
        }
        return true;
    }
}