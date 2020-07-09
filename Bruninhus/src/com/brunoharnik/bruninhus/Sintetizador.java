package com.brunoharnik.bruninhus;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Sintetizador extends BorderPane {

	public boolean deveGerar;
	public int posOnda;

	private final Oscilador[] osciladores = new Oscilador[6];

	private VBox vBoxOsciladores = new VBox();

	public final AudioThread audioThread = new AudioThread(() -> {
		if (!deveGerar) {
			return null;
		}
		short[] s = new short[AudioThread.TAMANHO_BUFFER];
		for (int i = 0; i < AudioThread.TAMANHO_BUFFER; i++) {
			double d = 0;
			for (Oscilador o : osciladores) {
				d += o.proximoSample() / osciladores.length;
			}
			s[i] = (short) (Short.MAX_VALUE * d);
		}
		return s;
	});

	public static class AudioInfo {
		public static final int SAMPLE_RATE = 44100;
	}

	public Sintetizador(Stage stage) {
		
		vBoxOsciladores.setPadding(new Insets(20));
		vBoxOsciladores.setStyle("-fx-background-color: #2277CC");
		vBoxOsciladores.setSpacing(10);
		
		for (int i = 0; i < osciladores.length; i++) {
			osciladores[i] = new Oscilador();
			osciladores[i].lblOscilador.setText("Oscilador " + (i + 1) + ":");
			vBoxOsciladores.getChildren().add(osciladores[i]);
		}

		this.setLeft(vBoxOsciladores);

	}
}
