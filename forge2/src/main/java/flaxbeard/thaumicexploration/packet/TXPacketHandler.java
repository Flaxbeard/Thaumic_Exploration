package flaxbeard.thaumicexploration.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategoryList;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.client.fx.FXLightningBolt;
import thaumcraft.common.config.ConfigBlocks;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.data.TXWorldData;
import flaxbeard.thaumicexploration.event.DamageSourceTX;
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
            handlePacket(packet);
        }
    }

    private void handlePacket(Packet250CustomPayload packet)
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
            
            if (packetID == 2 && world!= null) {
            	int readInt = inputStream.readInt();
            	if (world.getEntityByID(readInt) != null) {
            		EntityLivingBase target = (EntityLivingBase) world.getEntityByID(readInt);
	                readInt = inputStream.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		EntityPlayer player = (EntityPlayer) world.getEntityByID(readInt);
	            		if (player.getCurrentItemOrArmor(4) != null) {
	            		player.getCurrentItemOrArmor(4).damageItem(1, player);
	            		if (player.getCurrentItemOrArmor(4).getItemDamage() == player.getCurrentItemOrArmor(4).getMaxDamage()) {
	            			player.inventory.armorInventory[3] = null;
	            		}
	            		//player.worldObj.spawnParticle("explode", (double)(target.posX + Math.random()-0.5F), (double)(target.boundingBox.maxY + Math.random()/2), (double)(target.posZ + Math.random()-0.5F), 0.0D, 0.0D, 0.0D);


	            		target.attackEntityFrom(DamageSourceTX.witherPlayerDamage(player), 1);
	            		
						ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
				        DataOutputStream outputStream = new DataOutputStream(bos);
				        try
				        {
				            outputStream.writeByte(3);
				            outputStream.writeInt(world.provider.dimensionId);
				            outputStream.writeInt(target.entityId);
				            outputStream.writeInt(player.entityId);
				           
				        }
				        catch (Exception ex)
				        {
				            ex.printStackTrace();
				        }
				
				        packet = new Packet250CustomPayload();
				        packet.channel = "tExploration";
				        packet.data = bos.toByteArray();
				        packet.length = bos.size();
				        PacketDispatcher.sendPacketToAllPlayers(packet);
	            		}
	            	}
            	}
            	
            }
            
            if (packetID == 3 ) {

            	if (world!= null) {
            	int readInt = inputStream.readInt();
            	if (world.getEntityByID(readInt) != null) {
            		EntityLivingBase target = (EntityLivingBase) world.getEntityByID(readInt);
	                readInt = inputStream.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		EntityPlayer player = (EntityPlayer) world.getEntityByID(readInt);

	            		if (player.username != Minecraft.getMinecraft().thePlayer.username) {
	            			
		        			FXLightningBolt bolt = new FXLightningBolt(player.worldObj, player.posX, player.boundingBox.minY + player.height / 2.0F + 0.75D, player.posZ, target.posX, target.boundingBox.maxY - 0.5F, target.posZ, player.worldObj.rand.nextLong(), 6, 0.5F, 5);
		        			bolt.defaultFractal();
		        		    bolt.setType(5);
		        	        if (player.username.equalsIgnoreCase("killajoke")) {
		        	        	bolt.setType(3);
		        	        }
		        		    bolt.setWidth(0.0625F);
		        		    bolt.finalizeBolt();
		                	//System.out.println(Minecraft.getMinecraft().thePlayer.username);
	            		}
	            	}
            	}
            }
            }
            
            if (packetID == 4 ) {

            	if (world!= null) {
            	int readInt = inputStream.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		EntityPlayer player = (EntityPlayer) world.getEntityByID(readInt);
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
            	int readInt = inputStream.readInt();
	            	if (world.getEntityByID(readInt) != null) {
	            		EntityPlayer player = (EntityPlayer) world.getEntityByID(readInt);
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
            	int x2 = inputStream.readInt();
            	int y2 = inputStream.readInt();
            	int z2 = inputStream.readInt();
            	int x = inputStream.readInt();
            	int y = inputStream.readInt();
            	int z = inputStream.readInt();
            	int color = inputStream.readInt();
            	
            	//ThaumicExploration.proxy.spawnEssentiaAtLocation(world, x+0.5F, y+1.1F, z+0.5F, x2+0.5F, y2+0.5F, z2+0.5F, 5,color);
            }
        


            if (packetID == 1 && world != null)
            {

            	int x = inputStream.readInt();
            	int y = inputStream.readInt();
            	int z = inputStream.readInt();
            	byte type = inputStream.readByte();
            	
            	
            	
            	int readInt = inputStream.readInt();
            	
            	if (world.getEntityByID(readInt) != null) {
            		EntityPlayer player = (EntityPlayer) world.getEntityByID(readInt);
            		
            	
            	TileEntity te = world.getBlockTileEntity(x, y, z);
            	
            	//System.out.println(FMLCommonHandler.instance().getEffectiveSide());

				if (type == 1) {
					
					world.setBlock(x, y, z, ThaumicExploration.boundChest.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = TXWorldData.get(world).getNextBoundChestID();
					((TileEntityBoundChest) world.getBlockTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundChest) world.getBlockTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 2) {
					world.setBlock(x, y, z, ThaumicExploration.boundChest.blockID, world.getBlockMetadata(x, y, z),1);
					int nextID = player.inventory.getCurrentItem().stackTagCompound.getInteger("ID");
					((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundChest) world.getBlockTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					if (!player.capabilities.isCreativeMode)
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
						if (!player.capabilities.isCreativeMode)
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				else if (type == 4) {
					
					
					world.setBlock(x, y, z, ThaumicExploration.boundJar.blockID, world.getBlockMetadata(x, y, z),1);
					List<EntityItem> test = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
					for (EntityItem entity : test) {
						ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
						if (stack.itemID == ConfigBlocks.blockJar.blockID) {
							if (stack.getItemDamage() == 0) {
								ItemStack newStack = stack;
								newStack.stackSize--;
								entity.setEntityItemStack(newStack);
								break;
							}
						}
			
					}
					int nextID = TXWorldData.get(world).getNextBoundJarID();
					((TileEntityBoundJar) world.getBlockTileEntity(x,y, z)).id = nextID;
					((TileEntityBoundJar) world.getBlockTileEntity(x,y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					world.markBlockForUpdate(x, y, z);
				}
				else if (type == 5) {
					if (player.inventory.getCurrentItem() != null) {
					world.setBlock(x, y, z, ThaumicExploration.boundJar.blockID, world.getBlockMetadata(x, y, z),1);
					List<EntityItem> test = world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
					for (EntityItem entity : test) {
						ItemStack stack = entity.getDataWatcher().getWatchableObjectItemStack(10);
						if (stack.itemID == ConfigBlocks.blockJar.blockID) {
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
					((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).id = nextID;
					((TileEntityBoundJar) world.getBlockTileEntity(x, y, z)).setColor(15-player.inventory.getCurrentItem().getItemDamage());
					world.markBlockForUpdate(x, y, z);
					if (!player.capabilities.isCreativeMode)
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
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
						if (!player.capabilities.isCreativeMode)
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				else if (type == 7) {
					if (player.inventory.getCurrentItem().getItem() instanceof IFluidContainerItem) {
						((IFluidContainerItem)player.inventory.getCurrentItem().getItem()).fill(player.inventory.getCurrentItem(), new FluidStack(FluidRegistry.WATER, 1000), true);
					}
					else if (player.inventory.getCurrentItem().itemID == Item.bucketEmpty.itemID) {
						
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
						player.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater, 1));
					}
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