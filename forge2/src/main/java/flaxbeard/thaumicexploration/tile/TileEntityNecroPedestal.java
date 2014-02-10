package flaxbeard.thaumicexploration.tile;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.entity.EntityInfusionItem;

public class TileEntityNecroPedestal extends TileEntity implements ISidedInventory,IAspectContainer,IWandable {
	public AspectList infusionRequirements = new AspectList();
	  private static final int[] slots = { 0 };
	public boolean active;
	private ItemStack[] inventory = new ItemStack[1];
	private ChunkCoordinates[] myCandleLocations = new ChunkCoordinates[]{};
	@Override
	public void updateEntity() {

		if (this.active) {
			for (ChunkCoordinates pos : myCandleLocations) {
				if (this.worldObj.getBlockId(pos.posX, pos.posY, pos.posZ) != ConfigBlocks.blockCandle.blockID) {
					active = false;
					break;
				}
			}
			if (this.worldObj.getBlockId(xCoord,yCoord+1,zCoord) == Block.fire.blockID) {
				this.worldObj.setBlock(xCoord, yCoord+1, zCoord, ThaumicExploration.necroFire.blockID);
			}
		}
		else
		{
			if (this.worldObj.getBlockId(xCoord,yCoord+1,zCoord) == ThaumicExploration.necroFire.blockID) {
				this.worldObj.setBlockToAir(xCoord, yCoord+1, zCoord);
			}
			
		}
		
		if (this.getStackInSlot(0) != null && !worldObj.isRemote &&!this.active) {
			float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = worldObj.rand.nextFloat();
            EntityItem entityitem;
	    	int k1 = worldObj.rand.nextInt(21) + 10;

            k1 = this.getStackInSlot(0).stackSize;

            entityitem = new EntityInfusionItem(worldObj, (double)((float)xCoord + f), (double)((float)yCoord + f1), (double)((float)zCoord + f2), new ItemStack(this.getStackInSlot(0).itemID, k1, this.getStackInSlot(0).getItemDamage()));
            float f3 = 0.05F;
            entityitem.motionX = (double)((float)worldObj.rand.nextGaussian() * f3);
            entityitem.motionY = (double)((float)worldObj.rand.nextGaussian() * f3 + 0.2F);
            entityitem.motionZ = (double)((float)worldObj.rand.nextGaussian() * f3);

            if (this.getStackInSlot(0).hasTagCompound())
            {
                entityitem.getEntityItem().setTagCompound((NBTTagCompound)this.getStackInSlot(0).getTagCompound().copy());
            }
            ItemStack template = getStackInSlot(0).copy();
	    	if (template.stackSize > 1) {
		    	template.stackSize = template.stackSize -=1;

		    	setInventorySlotContents(0, template);
	    	}
	    	else
	    	{
	    		this.setInventorySlotContents(0, null);
	    	}
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.spawnEntityInWorld(entityitem);
		}
		
		if (this.active) {
			for (int f = 0; f<4; f++) {
				double ticks = (Minecraft.getMinecraft().thePlayer.ticksExisted + (12.5*f)) % 50;
				for (int i = 0; i<5; i++) {
					int i1 = (i+1)%5;
					
					double xDiff = (myCandleLocations[i].posX-myCandleLocations[i1].posX);
					if (myCandleLocations[i].posX > myCandleLocations[i1].posX && xDiff < 0) {
						xDiff *= -1;
					}
					if (myCandleLocations[i].posX < myCandleLocations[i1].posX && xDiff > 0) {
						xDiff *= -1;
					}
					double zDiff = (myCandleLocations[i].posZ-myCandleLocations[i1].posZ);
					if (myCandleLocations[i].posZ > myCandleLocations[i1].posZ && zDiff < 0) {
						zDiff *= -1;
					}
					if (myCandleLocations[i].posZ < myCandleLocations[i1].posZ && zDiff > 0) {
						zDiff *= -1;
					}
					float xPos = myCandleLocations[i].posX - (float)(ticks*xDiff/50);
					float zPos = myCandleLocations[i].posZ - (float)(ticks*zDiff/50);
//					if (i==3 && !this.worldObj.isRemote)
//						System.out.println(myCandleLocations[i].posX + " " + myCandleLocations[i1].posX + " SS " + zDiff + " " + (myCandleLocations[i].posZ-myCandleLocations[i1].posZ));
					Thaumcraft.proxy.sparkle(xPos+0.5F, yCoord+0.75F, zPos+0.5F, 1.0F, 0x7F0000, 0.0F);
				}
			}
		
		
			for (int f = 0; f<4; f++) {
				double ticks = (Minecraft.getMinecraft().thePlayer.ticksExisted + (12.5*f)) % 50;
				for (int i = 0; i<5; i++) {
					int i1 = (i+2)%5;
					
					double xDiff = (myCandleLocations[i].posX-myCandleLocations[i1].posX);
					if (myCandleLocations[i].posX > myCandleLocations[i1].posX && xDiff < 0) {
						xDiff *= -1;
					}
					if (myCandleLocations[i].posX < myCandleLocations[i1].posX && xDiff > 0) {
						xDiff *= -1;
					}
					double zDiff = (myCandleLocations[i].posZ-myCandleLocations[i1].posZ);
					if (myCandleLocations[i].posZ > myCandleLocations[i1].posZ && zDiff < 0) {
						zDiff *= -1;
					}
					if (myCandleLocations[i].posZ < myCandleLocations[i1].posZ && zDiff > 0) {
						zDiff *= -1;
					}
					float xPos = myCandleLocations[i].posX - (float)(ticks*xDiff/50);
					float zPos = myCandleLocations[i].posZ - (float)(ticks*zDiff/50);
//					if (i==3 && !this.worldObj.isRemote)
//						System.out.println(myCandleLocations[i].posX + " " + myCandleLocations[i1].posX + " SS " + zDiff + " " + (myCandleLocations[i].posZ-myCandleLocations[i1].posZ));
					Thaumcraft.proxy.sparkle(xPos+0.5F, yCoord+0.75F, zPos+0.5F, 1.0F, 0x7F0000, 0.0F);
				}
			}
		}

	}
	
	public void burnItem(EntityItem entity) {
		
	}

	@Override
	public AspectList getAspects() {
		// TODO Auto-generated method stub
		infusionRequirements.add(ThaumicExploration.fakeAspectNecro, 1);
		return infusionRequirements;
	}

	@Override
	public void setAspects(AspectList aspects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int containerContains(Aspect tag) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack,
			EntityPlayer player, int x, int y, int z, int side, int md) {
		// TODO Auto-generated method stub
		return 0;
	}
	private ChunkCoordinates[] candleLocations = {new ChunkCoordinates(-2,0,-2),new ChunkCoordinates(2,0,-2),new ChunkCoordinates(3,0,1),new ChunkCoordinates(0,0,3),new ChunkCoordinates(-3,0,1)};
	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack,
			EntityPlayer player) {
		ArrayList<ChunkCoordinates> possibleCandleLocations = new ArrayList<ChunkCoordinates>();
		outerLoop:
		for (int j = 0; j < 4; j ++) {
			possibleCandleLocations.clear();
			int x = xCoord;
			int y = yCoord;
			int z = zCoord;
			for (int k = 0; k < 5; k++) {
				int xOffset = candleLocations[k].posX;
				int zOffset = candleLocations[k].posZ;
				if (j > 1) {
					zOffset = -candleLocations[k].posZ;
				}
				if (j % 2 == 0) {
					xOffset = candleLocations[k].posZ;
					zOffset = candleLocations[k].posX;
					if (j > 1) {
						xOffset = -candleLocations[k].posZ;
					}
				}

				if (world.getBlockId(x + xOffset, y, z + zOffset) == ConfigBlocks.blockCandle.blockID) {
					possibleCandleLocations.add(new ChunkCoordinates(x + xOffset, y, z + zOffset));
					
					if (k == 4) {
						active = true;
						this.myCandleLocations = (ChunkCoordinates[]) possibleCandleLocations.toArray(new ChunkCoordinates[0]);
						break outerLoop;
					}
				}
				else
				{
					break;
				}
			}
		}
		return wandstack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player,
			int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world,
			EntityPlayer player, int count) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "necromantic altar";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return  true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Auto-generated method stub
		return slots;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory[i] != null)
		{
			ItemStack template = this.inventory[i].copy();
			template.stackSize = 0;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	      	if (this.inventory[i].stackSize <= j)
	      	{
	      		
	      		ItemStack itemstack = this.inventory[i];
	      		
	      		this.inventory[i] = template;
	        	return itemstack;
	      	}
	      	ItemStack itemstack = this.inventory[i].splitStack(j);
	      	if (this.inventory[i] == null) {
	      		this.inventory[i] = template;
	      	}
//	      	if (this.inventory[i ].stackSize == 0) {
//	    	  	this.inventory[i] = null;
//	      	}
	      	return itemstack;
	    }
		return null;
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if (this.inventory[i] != null)
		{
			ItemStack itemstack = this.inventory[i];
			this.inventory[i] = null;
			return itemstack;
		}
		return null;
	}
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		//if (itemstack != null) {
			this.inventory[i] = itemstack;
			if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
				itemstack.stackSize = getInventoryStackLimit();
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//}
	}
	@Override
	public int getSizeInventory() {
		return 1;
	}
	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory[i];
	}
	public void readInventoryNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");
			if ((b0 >= 0) && (b0 < this.inventory.length)) {
				this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}
	  
	public void writeInventoryNBT(NBTTagCompound nbttagcompound)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.inventory.length; i++) {
			if (this.inventory[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		        nbttagcompound1.setByte("Slot", (byte)i);
		        this.inventory[i].writeToNBT(nbttagcompound1);
		        nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
	}
	
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		this.readInventoryNBT(pkt.data);
	}
	
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
		
    	this.writeInventoryNBT(par1NBTTagCompound);
    }
    
	
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
		super.readFromNBT(par1NBTTagCompound);
		this.readInventoryNBT(par1NBTTagCompound);
    }

	
	public Packet getDescriptionPacket() {
		super.getDescriptionPacket();
		NBTTagCompound access = new NBTTagCompound();
		this.writeInventoryNBT(access);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, access);
	}
	

}
