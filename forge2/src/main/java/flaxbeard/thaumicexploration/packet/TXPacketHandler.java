package flaxbeard.thaumicexploration.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
// cpw.mods.fml.common.Side;

public class TXPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals("tExploration"))
        {
        	System.out.println("got a packet!" );
            handleResearch(packet);
        }
    }

    private void handleResearch(Packet250CustomPayload packet)
    {

        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        byte packetType;
        int dimension;
        byte packetID;

        try
        {
            packetID = inputStream.readByte();
            dimension = inputStream.readInt();
            World world = DimensionManager.getWorld(dimension);

            if (packetID == 1)
            {

            	int x = inputStream.readInt();
            	int y = inputStream.readInt();
            	int z = inputStream.readInt();
            	int number = inputStream.readInt();
            	
            	TileEntity te = world.getBlockTileEntity(x, y, z);
            	((TileEntityBoundChest) te).setAccessTicks(number);
            	System.out.println(FMLCommonHandler.instance().getEffectiveSide() + " I set it to  " + number + ",  it thinks that it's " + ((TileEntityBoundChest) te).getAccessTicks() + "!");
            	
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }
}