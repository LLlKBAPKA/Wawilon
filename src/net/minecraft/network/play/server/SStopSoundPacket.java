package net.minecraft.network.play.server;

import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import javax.annotation.Nullable;
import java.io.IOException;

public class SStopSoundPacket implements IPacket<IClientPlayNetHandler>
{
    private ResourceLocation name;
    private SoundCategory category;

    public SStopSoundPacket()
    {
    }

    public SStopSoundPacket(@Nullable ResourceLocation p_i47929_1_, @Nullable SoundCategory p_i47929_2_)
    {
        this.name = p_i47929_1_;
        this.category = p_i47929_2_;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        int i = buf.readByte();

        if ((i & 1) > 0)
        {
            this.category = buf.readEnumValue(SoundCategory.class);
        }

        if ((i & 2) > 0)
        {
            this.name = buf.readResourceLocation();
        }
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        if (this.category != null)
        {
            if (this.name != null)
            {
                buf.writeByte(3);
                buf.writeEnumValue(this.category);
                buf.writeResourceLocation(this.name);
            }
            else
            {
                buf.writeByte(1);
                buf.writeEnumValue(this.category);
            }
        }
        else if (this.name != null)
        {
            buf.writeByte(2);
            buf.writeResourceLocation(this.name);
        }
        else
        {
            buf.writeByte(0);
        }
    }

    @Nullable
    public ResourceLocation getName()
    {
        return this.name;
    }

    @Nullable
    public SoundCategory getCategory()
    {
        return this.category;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IClientPlayNetHandler handler)
    {
        handler.handleStopSound(this);
    }
}
