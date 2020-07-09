package com.brunoharnik.bruninhus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(Main.class, args);
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
			switch (evt.getCode()) {
			case A:
				if (!sintetizador.audioThread.isRodando()) {
					sintetizador.deveGerar = true;
					sintetizador.audioThread.triggerPlayback();
				}
				break;
			case W:
				break;
			case S:
				break;
			case E:
				break;
			case D:
				break;
			case F:
				break;
			case T:
				break;
			case G:
				break;
			case Y:
				break;
			case H:
				break;
			case U:
				break;
			case J:
				break;
			case K:
				break;
			default:
				break;
			}

		});

		cena.setOnKeyReleased(evt -> {
			switch (evt.getCode()) {
			case A:
				if (sintetizador.audioThread.isRodando()) {
					sintetizador.deveGerar = false;
				}
				break;
			case W:
				break;
			case S:
				break;
			case E:
				break;
			case D:
				break;
			case F:
				break;
			case T:
				break;
			case G:
				break;
			case Y:
				break;
			case H:
				break;
			case U:
				break;
			case J:
				break;
			case K:
				break;
			default:
				break;
			}

		});
	}

}
