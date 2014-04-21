package flaxbeard.thaumicexploration.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigItems;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.block.BlockThinkTank;

public class TileEntityThinkTank extends TileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[] {0};
    private static final int[] slots_bottom = new int[] {2, 1};
    private static final int[] slots_sides = new int[] {1};
    

    long lastsigh = System.currentTimeMillis() + 1500L;
    protected static Random rand = new Random();
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime = 55;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int currentItemBurnTime;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
    private String field_94130_e = "thaumicExploration.test";
	private boolean canSpaz;
	public float pageFlipPrev;
	public float pageFlip;
	public float field_70374_e;
	public int space = 1;
	public int warmedUpNumber;
	public int rotationTicks;

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }
    
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
	        return AxisAlignedBB.getAABBPool().getAABB(this.xCoord - 1 - this.space, this.yCoord - 1, this.zCoord - 1 - this.space, this.xCoord + 1 + this.space, this.yCoord + 1, this.zCoord + 1 + this.space);
	}

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0)
                {
                    this.furnaceItemStacks[par1] = null;
                }

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
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
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
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.field_94130_e : "thaumicExploration:guiThinkTank";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    /**
     * Sets the custom display name to use when opening a GUI linked to this tile entity.
     */
    public void setGuiDisplayName(String par1Str)
    {
        this.field_94130_e = par1Str;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = (NBTTagList) par1NBTTagCompound.getTag("Items");
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.field_94130_e = par1NBTTagCompound.getString("CustomName");
        }
        
        if (par1NBTTagCompound.hasKey("WarmUpTicks"))
        {
            this.warmedUpNumber = par1NBTTagCompound.getShort("WarmUpTicks");
        }
        
        
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
    	super.getDescriptionPacket();
        NBTTagCompound access = new NBTTagCompound();
        access.setInteger("warmedUpNumber", this.warmedUpNumber);
        access.setInteger("rotationTicks", this.rotationTicks);
        access.setInteger("space", this.space);
        
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, access);
    }
    

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	super.onDataPacket(net, pkt);
    	NBTTagCompound access = pkt.func_148857_g();
    	this.warmedUpNumber = access.getInteger("warmedUpNumber");
    	this.rotationTicks = access.getInteger("rotationTicks");
    	this.space = access.getInteger("space");
    	
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("WarmUpTicks", (short)this.warmedUpNumber);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.field_94130_e);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.furnaceCookTime * par1 / 200;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return true;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;
        if (!this.worldObj.isRemote) {
        	if (this.canSmelt())
			{

	        	if (this.warmedUpNumber < 40) {
	        		this.warmedUpNumber++;
	        		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	        	}
	
			}
			if (!this.canSmelt() && this.warmedUpNumber > 0)
			{
				this.warmedUpNumber--;
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
			
			
        }
        if (this.warmedUpNumber > 0) {
        	this.rotationTicks++;
        }
        if (!this.worldObj.isRemote)
        {

            if (this.isBurning() && this.canSmelt())
            {
                ++this.furnaceCookTime;

                if (this.furnaceCookTime == 200)
                {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                this.furnaceCookTime = 0;
            }

            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                BlockThinkTank.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (this.worldObj.isRemote)
        {

			
    	
          this.canSpaz = true;
          Entity entity = null;
          if (entity == null)
          {
            entity = this.worldObj.getClosestPlayer(this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, 6.0D);
            if ((entity != null) && (this.lastsigh < System.currentTimeMillis()))
            {
              this.worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "thaumcraft:brain", 0.15F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F, false);
              this.lastsigh = (System.currentTimeMillis() + 5000L + this.worldObj.rand.nextInt(25000));
            }
          }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
    	if (!this.enoughSpace())
    	{
    		return false;
    	}
        if (this.furnaceItemStacks[0] == null)
        {
            return false;
        }
        else
        {
        	if (this.furnaceItemStacks[0].getItem() == Items.book) {
	        	ItemStack itemstack = new ItemStack(ConfigItems.itemResource, 1, 9);
	        	//itemstack = new ItemStack(ConfigItems.itemResearchNotes, 1, 42);
	            if (this.furnaceItemStacks[1] == null) return true;
	            if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
	            int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
	            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        	}
        	else if (this.furnaceItemStacks[0].getItem() == Items.enchanted_book) {
        		ItemStack itemstack = new ItemStack(ConfigItems.itemResource, 2, 9);
        		//itemstack = new ItemStack(ConfigItems.itemResearchNotes, 1, 42);
	            if (this.furnaceItemStacks[1] == null) return true;
	            if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) return false;
	            int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
	            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        	}
        	else
        	{
        		return false;
        	}
        }
    }
    
    public boolean enoughSpace() {
		boolean enoughSpace = true;
		boolean muchSpace = true;
		for (int x = -2; x<3;x++) {
			for (int z = -2; z<3;z++) {
				for (int y = -1; y<2;y++) {
					if (this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z).getMaterial() != Config.airyMaterial && this.worldObj.getBlock(this.xCoord + x, this.yCoord + y, this.zCoord + z).getMaterial() != Material.air && !this.worldObj.getBlock(this.xCoord+x, this.yCoord+y, this.zCoord+z).isReplaceable(this.worldObj, this.xCoord+x, this.yCoord+y, this.zCoord+z)) {
						if (!(this.xCoord + x == this.xCoord && this.zCoord +z ==  this.zCoord && (this.yCoord+y == this.yCoord || this.yCoord+y == this.yCoord - 1))) {
							if (Math.abs(x) > 1 || Math.abs(z) > 1) {
								muchSpace = false;
							}
							else
							{
								enoughSpace = false;
							}
						
						}
					}
				}
			}
		}
		if (this.worldObj.getBlock(this.xCoord, this.yCoord-1, this.zCoord) != Blocks.bookshelf) {
			enoughSpace = false;
		}
		int oldSpace = this.space;
		this.space = muchSpace ? 1 : 0;
		if (this.space != oldSpace) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
    	return enoughSpace;
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        
        if (this.canSmelt())
        {
        	double rand = Math.random();
        	boolean genFrag = false;
        	int numberFrags = 0;
        	if (this.furnaceItemStacks[0].getItem() == Items.enchanted_book) {
        		if (rand > 0.33) {
        			genFrag = true;
        			numberFrags = (rand > 0.66) ? 1 : 2;
        		}
        	}
        	if (this.furnaceItemStacks[0].getItem() == Items.book) {
        		if (rand <= 0.1) {
        			genFrag = true;
        			numberFrags = 1;
        		}
        	}
        	if (genFrag) {

	            ItemStack itemstack = new ItemStack(ConfigItems.itemResource, numberFrags, 9);
	            //itemstack = new ItemStack(ConfigItems.itemResearchNotes, 1, 42);
	            if (this.furnaceItemStacks[1] == null)
	            {
	                this.furnaceItemStacks[1] = itemstack.copy();
	            }
	            else if (this.furnaceItemStacks[1].isItemEqual(itemstack))
	            {
	                furnaceItemStacks[1].stackSize += itemstack.stackSize;
	            }
	

        	}
            --this.furnaceItemStacks[0].stackSize;
        	
            if (this.furnaceItemStacks[0].stackSize <= 0)
            {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
   

    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */


    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : (par1 == 1 ? false : true);
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return par3 != 0 || par1 != 1 || par2ItemStack.getItem() == Items.bucket;
    }

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}
}