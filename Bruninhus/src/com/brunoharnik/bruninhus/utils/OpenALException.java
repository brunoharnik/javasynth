package com.brunoharnik.bruninhus.utils;

import static org.lwjgl.openal.AL10.*;

public class OpenALException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpenALException(int codigoErro) {
		super("Erro interno "
				+ (codigoErro == AL_INVALID_NAME ? "invalid name"
						: codigoErro == AL_INVALID_ENUM ? "invalid enum"
								: codigoErro == AL_INVALID_VALUE ? "invalid value"
										: codigoErro == AL_INVALID_OPERATION ? "invalid operation" : "unknown")
				+ "OpenAL Exception");
	}
}
