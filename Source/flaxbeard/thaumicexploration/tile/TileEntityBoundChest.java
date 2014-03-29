package flaxbeard.thaumicexploration.tile;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.block.BlockBoundChest;
import flaxbeard.thaumicexploration.data.BoundChestWorldData;

public class TileEntityBoundChest extends TileEntity implements IInventory
{
    private ItemStack[] chestContents = new ItemStack[36];
    
    public BoundChestWorldData myChest;
    
    public int accessTicks = 0;
    public int id = 0;
    public int clientColor = 0;

    /** Determines if the check for adjacent chests has taken place. */
    public boolean adjacentChestChecked;

    /** Contains the chest tile located adjacent to this one (if any) */
    
    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;

    /** The angle of the lid last tick */
    public float prevLidAngle;

    /** The number of players currently using this chest */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    private int cachedChestType;
    private String customName;

    public TileEntityBoundChest()
    {
        this.cachedChestType = -1;
    }

    @SideOnly(Side.CLIENT)
    public TileEntityBoundChest(int par1)
    {
        this.cachedChestType = par1;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 27;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.chestContents[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.chestContents[par1] != null)
        {
            ItemStack itemstack;

            if (this.chestContents[par1].stackSize <= par2)
            {
                itemstack = this.chestContents[par1];
                this.chestContents[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.chestContents[par1].splitStack(par2);

                if (this.chestContents[par1].stackSize == 0)
                {
                    this.chestContents[par1] = null;
                }

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.chestContents[par1] != null)
        {
            ItemStack itemstack = this.chestContents[par1];
            this.chestContents[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }
    
    @Override
    public void onInventoryChanged() {
    	if (myChest != null) {
    		myChest.updateChestContents(this.chestContents);
    	}
    	this.accessTicks = 80;
    	this.updateAccessTicks();
    	super.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.customName : "container.chest";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    /**
     * Sets the custom display name to use when opening a GUI for this specific TileEntityChest.
     */
    public void setChestGuiName(String par1Str)
    {
        this.customName = par1Str;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];

        if (par1NBTTagCompound.hasKey("chestID"))
        {
            this.id = par1NBTTagCompound.getInteger("chestID");
        }
        
        if (par1NBTTagCompound.hasKey("myAccessTick"))
        {
            this.accessTicks = par1NBTTagCompound.getInteger("myAccessTick");
        }
        
        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.chestContents.length)
            {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }
        par1NBTTagCompound.setInteger("myAccessTick", this.accessTicks);
        par1NBTTagCompound.setInteger("chestID", this.id);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    /**
     * Causes the TileEntity to reset all it's cached values for it's container block, blockID, metaData and in the case
     * of chests, the adjcacent chest check
     */
    public void updateContainingBlockInfo()
    {
        super.updateContainingBlockInfo();
        this.adjacentChestChecked = true;
    }

    

    /**
     * Performs the check for adjacent chests to determine if this chest is double or not.
     */
   

    private boolean func_94044_a(int par1, int par2, int par3)
    {
        Block block = Block.blocksList[this.worldObj.getBlockId(par1, par2, par3)];
        return false;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        super.updateEntity();
        ++this.ticksSinceSync;
        if (this.accessTicks > 0 && !this.worldObj.isRemote) {
        	--this.accessTicks;
        	this.updateAccessTicks();
        }
        float f;
        if (this.id > 0) {
        	myChest = BoundChestWorldData.get(this.worldObj, "id" + this.id, 0);
        }
        if (myChest != null) {
        	if (this.chestContents != myChest.getChestContents()) {
        		this.accessTicks = 80;
        		this.updateAccessTicks();
        	}
        	this.chestContents = myChest.getChestContents();
        	
        }
        if (!this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
        {
            this.numUsingPlayers = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double)((float)this.xCoord - f), (double)((float)this.yCoord - f), (double)((float)this.zCoord - f), (double)((float)(this.xCoord + 1) + f), (double)((float)(this.yCoord + 1) + f), (double)((float)(this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer.openContainer instanceof ContainerChest)
                {
                    IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this))
                    {
                        ++this.numUsingPlayers;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d0;

        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0F)
        {
            double d1 = (double)this.xCoord + 0.5D;
            d0 = (double)this.zCoord + 0.5D;



            this.worldObj.playSoundEffect(d1, (double)this.yCoord + 0.5D, d0, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numUsingPlayers == 0 && this.lidAngle > 0.0F || this.numUsingPlayers > 0 && this.lidAngle < 1.0F)
        {
            float f1 = this.lidAngle;

            if (this.numUsingPlayers > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2)
            {
                d0 = (double)this.xCoord + 0.5D;
                double d2 = (double)this.zCoord + 0.5D;

                

                this.worldObj.playSoundEffect(d0, (double)this.yCoord + 0.5D, d2, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound access = new NBTTagCompound();
        access.setInteger("accessTicks", this.accessTicks);
        access.setInteger("color", this.getSealColor());
        
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, access);
    }
    
    public void setColor(int color) {
    	if (this.id > 0) {
        	myChest = BoundChestWorldData.get(this.worldObj, "id" + this.id, 0);
        }
        if (myChest != null) {
        	myChest.setSealColor(color);
        }
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
    	NBTTagCompound access = pkt.data;
    	this.accessTicks = access.getInteger("accessTicks");
    	this.setColor(access.getInteger("color"));
    	this.clientColor = access.getInteger("color");
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    private void updateAccessTicks() {
    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    	//this.mar
//		if (!this.worldObj.isRemote) {
//			ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
//	        DataOutputStream outputStream = new DataOutputStream(bos);
//	
//	        try
//	        {
//	            outputStream.writeByte(1);
//	            outputStream.writeInt(this.worldObj.provider.dimensionId);
//	            outputStream.writeInt(this.xCoord);
//	            outputStream.writeInt(this.yCoord);
//	            outputStream.writeInt(this.zCoord);
//	            outputStream.writeInt(this.accessTicks);
//	        }
//	        catch (Exception ex)
//	        {
//	            ex.printStackTrace();
//	        }
//	
//	        Packet250CustomPayload packet = new Packet250CustomPayload();
//	        packet.channel = "tExploration";
//	        packet.data = bos.toByteArray();
//	        packet.length = bos.size();
//	        PacketDispatcher.sendPacketToAllPlayers(packet);
//	        System.out.println("sent");
//		}
	}

	/**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
            return true;
        }
        else
        {
            return super.receiveClientEvent(par1, par2);
        }
    }
    


    public void openChest()
    {
        if (this.numUsingPlayers < 0)
        {
            this.numUsingPlayers = 0;
        }
        
        

        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
    }

    public void closeChest()
    {
        if (this.getBlockType() != null && this.getBlockType() instanceof BlockBoundChest)
        {
        	
            --this.numUsingPlayers;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType().blockID);
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
    }

    public int getChestType()
    {
        if (this.cachedChestType == -1)
        {
            if (this.worldObj == null || !(this.getBlockType() instanceof BlockBoundChest))
            {
                return 0;
            }

            this.cachedChestType = ((BlockBoundChest)this.getBlockType()).chestType;
        }

        return this.cachedChestType;
    }

	public int getSealColor() {
		// TODO Auto-generated method stub
		if (this.myChest != null) {
			return this.myChest.getSealColor();
		}
		else
		{
			return 0;
		}
	}
	
	public int getAccessTicks() {
		// TODO Auto-generated method stub
		if (this.worldObj.isRemote) {
		}
		return this.accessTicks;
		
	}
	
	public void setAccessTicks(int tick) {
		// TODO Auto-generated method stub
		this.accessTicks = tick;
	}
}
