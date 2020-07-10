package com.brunoharnik.bruninhus.utils;

import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.log;
import static java.lang.Math.pow;

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

		public static double getFrequenciaNota(int keyNum) {
			return pow(raiz(2, 12), keyNum - 49) * 440;
		}

		public static double raiz(double num, double raiz) {
			return pow(E, log(num) / raiz);
		}
	}
}