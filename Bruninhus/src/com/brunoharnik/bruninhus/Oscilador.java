package com.brunoharnik.bruninhus;

import java.util.Random;

import com.brunoharnik.bruninhus.utils.Utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Oscilador extends VBox {

	private static final double FREQUENCIA = 440;

	private FormaDaOnda formaDaOnda = FormaDaOnda.senoide;

	private int posOnda;
	
	private final Random random = new Random();
	
	public Label lblOscilador = new Label();

	public Oscilador() {
		
		ComboBox<FormaDaOnda> comboBox = new ComboBox<>();
		comboBox.getItems().setAll(FormaDaOnda.values());
		comboBox.getSelectionModel().select(FormaDaOnda.senoide);

		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				switch (comboBox.getValue()) {

				case senoide:
					formaDaOnda = FormaDaOnda.senoide;
					break;
				case quadrada:
					formaDaOnda = FormaDaOnda.quadrada;
					break;
				case serra:
					formaDaOnda = FormaDaOnda.serra;
					break;
				case triangular:
					formaDaOnda = FormaDaOnda.triangular;
					break;
				case ruido:
					formaDaOnda = FormaDaOnda.ruido;
					break;
				default:
					formaDaOnda = FormaDaOnda.senoide;
					break;
				}
			}
		});

		lblOscilador.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));
		lblOscilador.setPadding(new Insets(0, 0, 10, 0));
		lblOscilador.setTextFill(Color.WHITE);
		this.setMinWidth(200);
		this.setPadding(new Insets(10));
		this.setStyle("-fx-border-color: white;");
		this.getChildren().addAll(lblOscilador, comboBox);
	}

	private enum FormaDaOnda {
		senoide, quadrada, serra, triangular, ruido
	}

	public double proximoSample() {
		
		double tDivP = (posOnda++ / (double) Sintetizador.AudioInfo.SAMPLE_RATE) / (1d / FREQUENCIA);
		
		switch (formaDaOnda) {
		case senoide:
			return Math.sin(Utils.Math.frequenciaParaFrequenciaAngular(FREQUENCIA) * (posOnda - 1)
					/ Sintetizador.AudioInfo.SAMPLE_RATE);
		case quadrada:
			return Math.signum(Math.sin(Utils.Math.frequenciaParaFrequenciaAngular(FREQUENCIA) * (posOnda - 1)
					/ Sintetizador.AudioInfo.SAMPLE_RATE));
		case serra:
			return 2d * (tDivP - Math.floor(0.5 + tDivP));
		case triangular:
			return 2d * Math.abs(2d * (tDivP - Math.floor(0.5 + tDivP))) - 1;
		case ruido:
			return random.nextDouble();
		default:
			new RuntimeException("Erro na seleção do oscilador");
			return 0;
		}
	}
}
