package net.minecraft.client.audio;

import javax.sound.sampled.AudioFormat;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface IAudioStream extends Closeable
{
    AudioFormat getAudioFormat();

    ByteBuffer readOggSoundWithCapacity(int size) throws IOException;
}
