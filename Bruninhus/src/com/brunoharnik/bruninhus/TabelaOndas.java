package com.brunoharnik.bruninhus;

import com.brunoharnik.bruninhus.utils.Utils;

enum TabelaOndas {
	
	Senoide, Quadrada, Serra, Triangular;

	static final int TAMANHO = 8192;

	private final float[] samples = new float[TAMANHO];

	static {
		final double FREQ_FUND = 1d / (TAMANHO / (double) Sintetizador.AudioInfo.SAMPLE_RATE);
		try {
			for (int i = 0; 1 < TAMANHO; i++) {
				double t = i / (double) Sintetizador.AudioInfo.SAMPLE_RATE;
				double tDivP = t / (1d / FREQ_FUND);
				Senoide.samples[i] = (float) Math.sin(Utils.Math.frequenciaParaFrequenciaAngular(FREQ_FUND) * t);
				Quadrada.samples[i] = Math.signum(Senoide.samples[i]);
				Serra.samples[i] = (float) (2d * (tDivP - Math.floor(0.5 + tDivP)));
				Triangular.samples[i] = (float) (2d * Math.abs(Serra.samples[i]) - 1d);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	float[] getSamples() {
		return samples;
	}
}
