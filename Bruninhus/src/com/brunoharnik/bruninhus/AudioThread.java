package com.brunoharnik.bruninhus;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;

import com.brunoharnik.bruninhus.utils.Utils;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

import java.util.function.Supplier;

class AudioThread extends Thread {

	static final int TAMANHO_BUFFER = 512;
	static final int QTD_BUFFER = 8;
	
	private final Supplier<short[]> bufferSupplier;

	private final int[] buffers = new int[QTD_BUFFER];
	private final long dispositivo = alcOpenDevice(alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER));
	private final long contexto = alcCreateContext(dispositivo, new int[1]);
	private final int fonte;

	private int bufferIndex;

	private boolean fechado, rodando;

	public AudioThread(Supplier<short[]> bufferSupplier) {
		this.bufferSupplier = bufferSupplier;
		alcMakeContextCurrent(contexto);
		AL.createCapabilities(ALC.createCapabilities(dispositivo));
		fonte = alGenSources();

		for (int i = 0; i < QTD_BUFFER; i++) {
			bufferSamples(new short[0]);
		}
		alSourcePlay(fonte);
		catchInternalException();
		start();
	}
	
	boolean isRodando() {
		return rodando;
	}

	@Override
	public synchronized void run() {
		while (!fechado) {
			while (!rodando) {
				Utils.handleProcedure(this::wait, false);
			}
			int bufsProcessados = alGetSourcei(fonte, AL_BUFFERS_PROCESSED);
			for (int i = 0; i < bufsProcessados; i++) {
				
				short[] samples = bufferSupplier.get();
				if (samples == null) { 
					rodando = false;
					break;
				}
				
				alDeleteBuffers(alSourceUnqueueBuffers(fonte));
				buffers[bufferIndex] = alGenBuffers();
				bufferSamples(samples);
				
			}
			if (alGetSourcei(fonte, AL_SOURCE_STATE) != AL_PLAYING) {
				alSourcePlay(fonte);
			}
			catchInternalException();
		}
		alDeleteSources(fonte);
		alDeleteBuffers(buffers);
		alcDestroyContext(contexto);
		alcCloseDevice(dispositivo);
	}
	
	synchronized void triggerPlayback() {
		rodando = true;
		notify();
	}
	
	void close() {
		fechado = true;
		triggerPlayback();
	}

	private void bufferSamples(short[] samples) {
		int buf = buffers[bufferIndex++];
		alBufferData(buf, AL_FORMAT_MONO16, samples, Sintetizador.AudioInfo.SAMPLE_RATE);
		alSourceQueueBuffers(fonte, buf);
		bufferIndex %= QTD_BUFFER;
	}

	private void catchInternalException() {
		int err = alcGetError(dispositivo);
		if (err != ALC_NO_ERROR) {
			throw new OpenALException(err);
		}
	}
}
