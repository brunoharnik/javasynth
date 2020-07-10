package com.brunoharnik.bruninhus;

import java.util.HashMap;

import com.brunoharnik.bruninhus.utils.Utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bruninhus extends Application {

	private static final HashMap<Character, Double> FREQUENCIAS_NOTAS = new HashMap<>();

	static {
		final int NOTA_INICIO = 16;
		final int INCREMENTO_DE_FREQUENCIA = 1;
		final char[] NOTAS = "AWSEDFTGYHUJK".toCharArray();
		for (int i = NOTA_INICIO, nota = 0; i < NOTAS.length * INCREMENTO_DE_FREQUENCIA
				+ NOTA_INICIO; i += INCREMENTO_DE_FREQUENCIA, ++nota) {
			FREQUENCIAS_NOTAS.put(NOTAS[nota], Utils.Math.getFrequenciaNota(i));
		}
	}

	public static void main(String[] args) {
		Application.launch(Bruninhus.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		Sintetizador sintetizador = new Sintetizador(stage);
		Scene cena = new Scene(sintetizador, 800, 600);

		acoesTeclas(cena, sintetizador);

		stage.setScene(cena);
		stage.setResizable(false);
		stage.setTitle("Bruninhus – V.1.0");
		stage.show();

		stage.setOnHiding(e -> {
			sintetizador.audioThread.close();
		});

	}

	public void acoesTeclas(Scene cena, Sintetizador sintetizador) {
		
		cena.setOnKeyPressed(evt -> {
			if(!FREQUENCIAS_NOTAS.containsKey(evt.getCode().toString().charAt(0))) return;
			if (!sintetizador.audioThread.isRodando()) {

				if (FREQUENCIAS_NOTAS.containsKey(evt.getCode().toString().charAt(0))) {
					sintetizador.deveGerar = true;
					sintetizador.audioThread.triggerPlayback();
					for (Oscilador o : sintetizador.osciladores) {
						o.setFrequencia(FREQUENCIAS_NOTAS.get(evt.getCode().toString().charAt(0)));
					}
				}
			}
		});

		cena.setOnKeyReleased(evt -> {
			if(!FREQUENCIAS_NOTAS.containsKey(evt.getCode().toString().charAt(0))) return;
			if (sintetizador.audioThread.isRodando()) {
				sintetizador.deveGerar = false;
			}
		});
	}
}
