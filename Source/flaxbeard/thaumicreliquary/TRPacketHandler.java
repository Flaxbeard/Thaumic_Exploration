package flaxbeard.thaumicreliquary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.PacketHandler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import flaxbeard.thaumicreliquary.research.TRResearch;
// cpw.mods.fml.common.Side;

public class TRPacketHandler implements IPacketHandler
{
	public static ArrayList<String> enabledPlayers = new ArrayList<String>();
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals("tReliquary"))
        {
            handlePacket(packet,player);
        }
    }

    private void handlePacket(Packet250CustomPayload packet,Player players)
    {
    	ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        byte packetID;

        try
        {
            packetID = inputStream.readByte();
            
            if (packetID == 1) {
            	this.handleOptionalPacket(dat,players);
            }
            if (packetID == 2) {
            	this.handleEnabledPacket(dat);
            }
     
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }

	private void handleEnabledPacket(ByteArrayDataInput dat) {
		dat.readByte();
		int worldID = dat.readInt();
		World world = DimensionManager.getWorld(worldID);
		String username = dat.readUTF();
		boolean disabled = dat.readBoolean();
		if (!disabled && enabledPlayers.contains(username)) {
			enabledPlayers.remove(username);
		}
		if (disabled && !enabledPlayers.contains(username)) {
			enabledPlayers.add(username);
		}
		if (disabled && ThaumicReliquary.optional) {
			System.out.println(username + " has disabled Thaumic Reliquary.");
			EntityPlayer targetPlayer = null;

			List players = world.playerEntities;
			for (Object player : players) {
				System.out.println(player.toString());
				if (player instanceof EntityPlayer) {
					if (((EntityPlayer) player).username.equals(username)) {
						targetPlayer = ((EntityPlayer) player);
					}
				}
			}
			if (targetPlayer != null) {
				//	System.out.println("REMOVING VIRTUAL RESEARCH SERVERSIDE");
			    List<String> research = Thaumcraft.proxy.getPlayerKnowledge().researchCompleted.get(username);
			    for (String tag : TRResearch.trResearch) {
				    if (tag != null && research != null && research.contains(tag)) {
				    	research.remove(tag);
				    }
			    }
			    if (research != null && research.contains("TRELIQUARY")) {
			    	research.remove("TRELIQUARY");
			    }
			    System.out.println("Removing TRELIQUARY");
			    Thaumcraft.proxy.getPlayerKnowledge().researchCompleted.put(username,research);
			    PacketHandler.sendPlayerDataPacketResearch((EntityPlayerMP) targetPlayer);
//			    List<String> research2 = Thaumcraft.proxy.getPlayerKnowledge().researchCompleted.get(username);
//			    for (String tag : research2) {
//				    System.out.println(tag);
//			    }
			   // targetPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("§Your Reliquary research has been disabled."));
			}
		}
		else
		{
				System.out.println(username + " has enabled Thaumic Reliquary.");
				EntityPlayer targetPlayer = null;

				List players = world.playerEntities;
				for (Object player : players) {
					System.out.println(player.toString());
					if (player instanceof EntityPlayer) {
						if (((EntityPlayer) player).username.equals(username)) {
							targetPlayer = ((EntityPlayer) player);
						}
					}
				}
				if (targetPlayer != null) {
					Thaumcraft.proxy.researchManager.completeResearch(targetPlayer, "TRELIQUARY");
					
				}
			}
	}
	
	private void handleOptionalPacket(ByteArrayDataInput dat, Player players) {
		dat.readByte();
		ThaumicReliquary.optional = dat.readBoolean();
		if (ThaumicReliquary.optional) {
			System.out.println("Thaumic Reliquary is OPTIONAL.");
		}
		else
		{
			System.out.println("Thaumic Reliquary is MANDATORY.");
		}
		System.out.println("WHY IS THIS HAPPENING");
		sendEnabledPacket();
	}

	public static void sendOptionalPacket(EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    DataOutputStream dos = new DataOutputStream(bos);
	    try
	    {
	      dos.writeByte(1);
	      dos.writeBoolean(ThaumicReliquary.optional);
	    }
	    catch (IOException e) {}
	    Packet250CustomPayload pkt = new Packet250CustomPayload();
	    pkt.channel = "tReliquary";
	    pkt.data = bos.toByteArray();
	    pkt.length = bos.size();
	    pkt.isChunkDataPacket = false;
	    PacketDispatcher.sendPacketToPlayer(pkt, (Player) player);
	}
	
	public static void sendEnabledPacket() {
		EntityPlayer player = TRClientThingy.getPlayer();
		if (player != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    DataOutputStream dos = new DataOutputStream(bos);
		    try
		    {
		      dos.writeByte(2);
		      dos.writeInt(player.worldObj.provider.dimensionId);
		      dos.writeUTF(player.username);
		      dos.writeBoolean(ThaumicReliquary.disable);
		    }
		    catch (IOException e) {}
		    Packet250CustomPayload pkt = new Packet250CustomPayload();
		    pkt.channel = "tReliquary";
		    pkt.data = bos.toByteArray();
		    pkt.length = bos.size();
		    pkt.isChunkDataPacket = false;
		    PacketDispatcher.sendPacketToServer(pkt);
		    
		}
	}
}