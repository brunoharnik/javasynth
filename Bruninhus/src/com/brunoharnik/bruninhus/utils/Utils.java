package com.brunoharnik.bruninhus.utils;

import static java.lang.Math.PI;

public class Utils {
	public static void handleProcedure(Procedure procedure, boolean printStackTrace) {
		try {
			procedure.invoke();
		} catch (Exception e) {
			if (printStackTrace) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Math {
		public static double frequenciaParaFrequenciaAngular(double freq) {
			return 2 * PI * freq;
		}
	}
}