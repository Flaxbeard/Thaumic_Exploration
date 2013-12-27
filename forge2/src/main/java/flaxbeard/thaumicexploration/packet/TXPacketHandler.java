package flaxbeard.thaumicexploration.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.data.TXWorldDataInfoWorldData;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;
// cpw.mods.fml.common.Side;

public class TXPacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals("tExploration"))
        {
        	//System.out.println("got a packet!" );
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
            	byte type = inputStream.readByte();
            	EntityPlayer player = (EntityPlayer) world.getEntityByID(inputStream.readInt());
            	
            	TileEntity te = world.getBlockTileEntity(x, y, z);
            	
            	//System.out.println(FMLCommonHandler.instance().getEffectiveSide());

				if (type == 1) {
					
					world.setBlock(x, y, z, ThaumicExploration.boundChest.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = TXWorldDataInfoWorldData.get(world).getNextBoundChestID();
					((TileEntityBoundChest) world.getBlockTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundChest) world.getBlockTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 2) {
					////System.out.println("dafuk");
					world.setBlock(x, y, z, ThaumicExploration.boundChest.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = player.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
					((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
				}
	
				else if (type == 3) {
					int color = ((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).getSealColor();		
					if (15-(player.inventory.getCurrentItem().getItemDamage()) == color) {

						int nextID = ((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).id;
						ItemStack linkedSeal = new ItemStack(ThaumicExploration.chestSealLinked.itemID, 1, player.inventory.getCurrentItem().getItemDamage());
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("ID", nextID);
						tag.setInteger("x", x);
						tag.setInteger("y", y);
						tag.setInteger("z", z);
						tag.setInteger("dim", world.provider.dimensionId);
						linkedSeal.setTagCompound(tag);

						player.inventory.addItemStackToInventory(linkedSeal);
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				else if (type == 4) {
					
					world.setBlock(x, y, z, ThaumicExploration.boundJar.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = TXWorldDataInfoWorldData.get(world).getNextBoundJarID();
					((TileEntityBoundJar) world.getBlockTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundJar) world.getBlockTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 5) {
					world.setBlock(x, y, z, ThaumicExploration.boundJar.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = player.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
					((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
				}
	
				else if (type == 6) {
					int color = ((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).getSealColor();		
					if (15-(player.inventory.getCurrentItem().getItemDamage()) == color) {

						int nextID = ((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).id;
						ItemStack linkedSeal = new ItemStack(ThaumicExploration.jarSealLinked.itemID, 1, player.inventory.getCurrentItem().getItemDamage());
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("ID", nextID);
						tag.setInteger("x", x);
						tag.setInteger("y", y);
						tag.setInteger("z", z);
						tag.setInteger("dim", world.provider.dimensionId);
						linkedSeal.setTagCompound(tag);

						player.inventory.addItemStackToInventory(linkedSeal);
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
            }
     
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }
    }
}