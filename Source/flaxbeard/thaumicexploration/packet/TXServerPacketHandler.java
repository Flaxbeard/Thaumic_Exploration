package flaxbeard.thaumicexploration.packet;

import ibxm.Player;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import org.apache.commons.lang3.tuple.MutablePair;

import thaumcraft.client.fx.FXLightningBolt;
import thaumcraft.common.config.ConfigBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.data.TXWorldData;
import flaxbeard.thaumicexploration.event.DamageSourceTX;
import flaxbeard.thaumicexploration.misc.SortingInventory;
import flaxbeard.thaumicexploration.tile.TileEntityAutoSorter;
import flaxbeard.thaumicexploration.tile.TileEntityBoundChest;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;

public class TXServerPacketHandler {

	  
	  private void handleTypeChangePacket(ByteBufInputStream dat, EntityPlayerMP player)
	  {
		  try {
			dat.readByte();
			int dim = dat.readInt();
			World world = DimensionManager.getWorld(dim);
		    int x = dat.readInt();
		    int y = dat.readInt();
		    int z = dat.readInt();
		    int x2 = dat.readInt();
		    int y2 = dat.readInt();
		    int z2 = dat.readInt();
		    int type = dat.readInt();
		    int side = dat.readInt();
	
		    TileEntityAutoSorter switcher = (TileEntityAutoSorter) world.getTileEntity(x, y, z);
		    SortingInventory inv = switcher.chestSorts.get(MutablePair.of(new ChunkCoordinates(x2,y2,z2),side));
		    inv.type = type;
		    switcher.chestSorts.put(MutablePair.of(new ChunkCoordinates(x2,y2,z2),side), inv);
		  }
		  catch (Exception e) {}
	  }
	  


	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
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
           
            if (packetID == 44) {
            	this.handleTypeChangePacket(bbis,player);
            }
            if (packetID == 2 && world!= null) {
            	int readInt = bbis.readInt();
            	if (world.getEntityByID(readInt) != null) {
            		EntityLivingBase target = (EntityLivingBase) world.getEntityByID(readInt);
	                readInt = bbis.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		
	            		if (player.getCurrentArmor(3) != null) {
	            		player.getCurrentArmor(3).damageItem(1, player);
	            		if (player.getCurrentArmor(3).getItemDamage() == player.getCurrentArmor(3).getMaxDamage()) {
	            			player.inventory.armorInventory[3] = null;
	            		}
	            		//player.worldObj.spawnParticle("explode", (double)(target.posX + Math.random()-0.5F), (double)(target.boundingBox.maxY + Math.random()/2), (double)(target.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);


	            		target.attackEntityFrom(DamageSourceTX.witherPlayerDamage(player), 1);
	            		ByteBuf buf = Unpooled.buffer();
	            		ByteBufOutputStream out = new ByteBufOutputStream(buf);
				        try
				        {
				            out.writeByte(3);
				            out.writeInt(world.provider.dimensionId);
				            out.writeInt(target.getEntityId());
				            out.writeInt(player.getEntityId());
				           
				        }
				        catch (Exception ex)
				        {
				            ex.printStackTrace();
				        }
				        FMLProxyPacket packet = new FMLProxyPacket(buf,"tExploration");
				        ThaumicExploration.channel.sendToAll(packet);
				        out.close();
	            		}
	            	}
            	}
            	
            }
            
            
            
            if (packetID == 4 ) {

            	if (world!= null) {
            	int readInt = bbis.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            	
	            		ItemStack item = player.inventory.armorItemInSlot(0);
	            		if (!player.onGround && item != null) {
		            		if (!item.hasTagCompound()) {
		            			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
			        			item.setTagCompound(par1NBTTagCompound );
			        			item.stackTagCompound.setBoolean("IsSmashing", true);
			        			item.stackTagCompound.setInteger("smashTicks",0);
			        			item.stackTagCompound.setInteger("airTicks",0);
		            		}
		            		if (item.stackTagCompound.getInteger("airTicks") > 5) {
		            			item.stackTagCompound.setBoolean("IsSmashing", true);
		            			
		            		}
		            		
	            		}
	            	}
            	}
            }
            
            if (packetID == 5 ) {

            	if (world!= null) {
            	int readInt = bbis.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		
	            		if (player.inventory.armorItemInSlot(0) != null) {
	            		ItemStack item = player.inventory.armorItemInSlot(0);
	            		if (!player.onGround  && item != null) {
		            		if (!item.hasTagCompound()) {
		            			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
			        			item.setTagCompound(par1NBTTagCompound );
			        			item.stackTagCompound.setBoolean("IsSmashing", true);
			        			item.stackTagCompound.setInteger("smashTicks",0);
			        			item.stackTagCompound.setInteger("airTicks",0);
		            		}
		            			item.stackTagCompound.setInteger("airTicks", 10);
		            		
	            		}
	            	}
	            	}
            	}
            }
            
            if (packetID == 6) {
            	int x2 = bbis.readInt();
            	int y2 = bbis.readInt();
            	int z2 = bbis.readInt();
            	int x = bbis.readInt();
            	int y = bbis.readInt();
            	int z = bbis.readInt();
            	int color = bbis.readInt();
            	
            	//ThaumicExploration.proxy.spawnEssentiaAtLocation(world, x+0.5F, y+1.1F, z+0.5F, x2+0.5F, y2+0.5F, z2+0.5F, 5,color);
            }
        


            if (packetID == 1 && world != null)
            {

            	int x = bbis.readInt();
            	int y = bbis.readInt();
            	int z = bbis.readInt();
            	byte type = bbis.readByte();
            	
            	
            	
            	int readInt = bbis.readInt();

            	
            	TileEntity te = world.getTileEntity(x, y, z);
            	
            	//System.out.println(FMLCommonHandler.instance().getEffectiveSide());

				if (type == 1) {
					
					world.setBlock(x, y, z, ThaumicExploration.boundChest, world.getBlockMetadata(x, y, z),1);
					int nextID = TXWorldData.get(world).getNextBoundChestID();
					((TileEntityBoundChest) world.getTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundChest) world.getTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 2) {
					world.setBlock(x, y, z, ThaumicExploration.boundChest, world.getBlockMetadata(x, y, z),1);
					int nextID = player.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
					((TileEntityBoundChest) world.getTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundChest) world.getTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
				}
	
				else if (type == 3) {
					int color = ((TileEntityBoundChest) world.getTileEntity(x, y, z)).getSealColor();		
					if (15-(player.inventory.getCurrentItem().getItemDamage()) == color) {

						int nextID = ((TileEntityBoundChest) world.getTileEntity(x, y, z)).id;
						ItemStack linkedSeal = new ItemStack(ThaumicExploration.chestSealLinked, 1, player.inventory.getCurrentItem().getItemDamage());
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("ID", nextID);
						tag.setInteger("x", x);
						tag.setInteger("y", y);
						tag.setInteger("z", z);
						tag.setInteger("dim", world.provider.dimensionId);
						linkedSeal.setTagCompound(tag);

						player.inventory.addItemStackToInventory(linkedSeal);
						if (!player.capabilities.isCreativeMode)
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				else if (type == 4) {
					
					
					world.setBlock(x, y, z, ThaumicExploration.boundJar, world.getBlockMetadata(x, y, z),1);
					List<EntityItem> test = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
					for (EntityItem entity : test) {
						ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
						if (stack.getItem() == Item.getItemFromBlock(ConfigBlocks.blockJar)) {
							if (stack.getItemDamage() == 0) {
								ItemStack newStack = stack;
								newStack.stackSize--;
								entity.setEntityItemStack(newStack);
								break;
							}
						}
			
					}
					int nextID = TXWorldData.get(world).getNextBoundJarID();
					((TileEntityBoundJar) world.getTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundJar) world.getTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 5) {
					if (player.inventory.getCurrentItem() != null) {
					world.setBlock(x, y, z, ThaumicExploration.boundJar, world.getBlockMetadata(x, y, z),1);
					List<EntityItem> test = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
					for (EntityItem entity : test) {
						ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
						if (stack.getItem() == Item.getItemFromBlock(ConfigBlocks.blockJar)) {
							if (stack.getItemDamage() == 0) {
								ItemStack newStack = stack;
								newStack.stackSize--;
								entity.setEntityItemStack(newStack);
								break;
							}
						}
			
					}
					
					//System.out.println("Name is: " + player.inventory.getCurrentItem().getUnlocalizedName());
					int nextID = player.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
					((TileEntityBoundJar) world.getTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundJar) world.getTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
	
				else if (type == 6) {
					int color = ((TileEntityBoundJar) world.getTileEntity(x, y, z)).getSealColor();		
					if (15-(player.inventory.getCurrentItem().getItemDamage()) == color) {

						int nextID = ((TileEntityBoundJar) world.getTileEntity(x, y, z)).id;
						ItemStack linkedSeal = new ItemStack(ThaumicExploration.jarSealLinked, 1, player.inventory.getCurrentItem().getItemDamage());
						NBTTagCompound tag = new NBTTagCompound();
						tag.setInteger("ID", nextID);
						tag.setInteger("x", x);
						tag.setInteger("y", y);
						tag.setInteger("z", z);
						tag.setInteger("dim", world.provider.dimensionId);
						linkedSeal.setTagCompound(tag);

						player.inventory.addItemStackToInventory(linkedSeal);
						if (!player.capabilities.isCreativeMode)
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				else if (type == 7) {
					if (player.inventory.getCurrentItem().getItem() instanceof IFluidContainerItem) {
						((IFluidContainerItem)player.inventory.getCurrentItem().getItem()).fill(player.inventory.getCurrentItem(), new FluidStack(FluidRegistry.WATER, 1000), true);
					}
					else if (player.inventory.getCurrentItem().getItem() == Items.bucket) {
						
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
						player.inventory.addItemStackToInventory(new ItemStack(Items.water_bucket, 1));
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
