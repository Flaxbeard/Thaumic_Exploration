package flaxbeard.thaumicexploration.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.client.fx.FXLightningBolt;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;

public class TXClientPacketHandler extends TXServerPacketHandler{
	
	  public static void sendTypeChangePacket(TileEntityAutoSorter te, ChunkCoordinates cc, int p, int side)
	  {
		ByteBuf buf = Unpooled.buffer();
		ByteBufOutputStream out = new ByteBufOutputStream(buf);
	    try
	    {
	    	out.writeByte(44);
	    	out.writeInt(te.getWorldObj().provider.dimensionId);
	    	out.writeInt(te.xCoord);
	    	out.writeInt(te.yCoord);
	    	out.writeInt(te.zCoord);
	    	out.writeInt(cc.posX);
	    	out.writeInt(cc.posY);
	    	out.writeInt(cc.posZ);
	    	out.writeInt(p);
	    	out.writeInt(side);
	    }
	    catch (IOException e) {}
	    FMLProxyPacket packet = new FMLProxyPacket(buf,"tExploration");
	    ThaumicExploration.channel.sendToServer(packet);
	    try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }

	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
		//ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
        //DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        byte packetType;
        int dimension;
        byte packetID;

        try
        {
            packetID = bbis.readByte();
            dimension = bbis.readInt();
            World world = DimensionManager.getWorld(dimension);
           
        	if (packetID == 3 ) {

            	if (world!= null) {
            	int readInt = bbis.readInt();
            	if (world.getEntityByID(readInt) != null) {
            		EntityLivingBase target = (EntityLivingBase) world.getEntityByID(readInt);
	                readInt = bbis.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		

	            		if (player.getUniqueID()!= Minecraft.getMinecraft().thePlayer.getUniqueID()) {
	            			
		        			FXLightningBolt bolt = new FXLightningBolt(player.worldObj, player.posX, player.boundingBox.minY + player.height / 2.0F + 0.75D, player.posZ, target.posX, target.boundingBox.maxY - 0.5F, target.posZ, player.worldObj.rand.nextLong(), 6, 0.5F, 5);
		        			bolt.defaultFractal();
		        		    bolt.setType(5);
//		        	        if (player.getUniqueID().equalsIgnoreCase("killajoke")) {
//		        	        	bolt.setType(3);
//		        	        }
		        		    bolt.setWidth(0.0625F);
		        		    bolt.finalizeBolt();
		                	//System.out.println(Minecraft.getMinecraft().thePlayer.username);
	            		}
	            	}
            	}
            }
            }
        	bbis.close();
        }
		catch (Exception e) {
			
		}
	}
	
}
