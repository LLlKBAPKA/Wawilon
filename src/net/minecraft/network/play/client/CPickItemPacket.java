package net.minecraft.network.play.client;

import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

import java.io.IOException;

public class CPickItemPacket implements IPacket<IServerPlayNetHandler>
{
    private int pickIndex;

    public CPickItemPacket()
    {
    }

    public CPickItemPacket(int pickIndexIn)
    {
        this.pickIndex = pickIndexIn;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException
    {
        this.pickIndex = buf.readVarInt();
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException
    {
        buf.writeVarInt(this.pickIndex);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(IServerPlayNetHandler handler)
    {
        handler.processPickItem(this);
    }

    public int getPickIndex()
    {
        return this.pickIndex;
    }
}
