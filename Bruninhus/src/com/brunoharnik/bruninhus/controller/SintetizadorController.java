package com.brunoharnik.bruninhus.controller;

import com.brunoharnik.bruninhus.view.Sintetizador;

public class SintetizadorController {

	private Sintetizador sintetizador;
	
	private boolean on;
	
	public SintetizadorController (Sintetizador sintetizador) {
		this.sintetizador = sintetizador;
	}
	
	public boolean isOn() {
		return on;
	}
	
	public boolean setOn(boolean on) {
		return this.on = on;
	}
	
	public Sintetizador getSintetizador() {
		return this.sintetizador;
	}
}
