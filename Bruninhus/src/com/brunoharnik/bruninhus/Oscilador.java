package com.brunoharnik.bruninhus;

import java.util.Random;

import com.brunoharnik.bruninhus.utils.Utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Oscilador extends VBox {

	public Label lblOscilador = new Label();

	private HBox hBoxComponentes = new HBox();

	private ComboBox<TabelaOndas> comboBox = new ComboBox<>();
	private TabelaOndas tabelaOndas = TabelaOndas.Senoide;

	private int tamanhoPassoTabelaOndas;
	private int indexTabelaOndas;

	private VBox vBoxTom = new VBox();
	private Label lblTituloTom = new Label("Tom:");
	private Label lblTom = new Label("x0.0");

	private double offsetTom;

	private double frequenciaNota;

	public Oscilador() {

		comboBox.getItems().addAll(TabelaOndas.values());
		comboBox.getSelectionModel().select(TabelaOndas.Senoide);

		comboBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				tabelaOndas = comboBox.getValue();
			}
		});

		editaLabel(lblOscilador, lblTituloTom);
		lblOscilador.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));

		editaLabelTom(lblTom);

		this.setAlignment(Pos.CENTER);
		this.setMinWidth(260);
		this.setPadding(new Insets(10));
		this.setStyle("-fx-border-color: white;");

		vBoxTom.setAlignment(Pos.CENTER);
		vBoxTom.getChildren().addAll(lblTituloTom, lblTom);
		hBoxComponentes.getChildren().addAll(comboBox, vBoxTom);
		this.getChildren().addAll(lblOscilador, hBoxComponentes);
	}

	public void setFrequencia(double frequencia) {
		frequenciaNota = frequencia;
		aplicaOffsetTom();
	}

	private double getOffsetTom() {
		return offsetTom / 10000d;
	}

	public double proximoSample() {
		double sample = tabelaOndas.getSamples()[indexTabelaOndas];
		indexTabelaOndas = (indexTabelaOndas + tamanhoPassoTabelaOndas) % tabelaOndas.TAMANHO;
		return sample;
	}

	private void editaLabelTom(Label lblTom) {

		lblTom.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
		lblTom.setPadding(new Insets(0, 10, 5, 10));
		lblTom.setTextFill(Color.YELLOW);

		lblTom.setOnMouseMoved(e -> {
			this.getScene().cursorProperty().set(Cursor.V_RESIZE);
		});

		lblTom.setOnMouseEntered(e -> {
			this.getScene().cursorProperty().set(Cursor.V_RESIZE);
		});

		lblTom.setOnMouseExited(e -> {
			this.getScene().cursorProperty().set(Cursor.DEFAULT);
		});

		lblTom.setOnMouseDragged(e -> {
			this.getScene().cursorProperty().set(Cursor.CLOSED_HAND);
			offsetTom -= e.getY();
			lblTom.setText("x" + getOffsetTom());
			aplicaOffsetTom();
		});

		lblTom.setOnMouseDragReleased(e -> {
			this.getScene().cursorProperty().set(Cursor.DEFAULT);
		});

		lblTom.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				offsetTom = 0;
				lblTom.setText("x" + getOffsetTom());
				aplicaOffsetTom();
			}
		});
	}

	private void editaLabel(Label... labels) {
		for (Label label : labels) {
			label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label.setPadding(new Insets(0, 10, 5, 10));
			label.setTextFill(Color.WHITE);
		}
	}

	private void aplicaOffsetTom() {
		tamanhoPassoTabelaOndas = (int) (TabelaOndas.TAMANHO * (frequenciaNota * Math.pow(2, getOffsetTom()))
				/ Sintetizador.AudioInfo.SAMPLE_RATE);
	}
}
