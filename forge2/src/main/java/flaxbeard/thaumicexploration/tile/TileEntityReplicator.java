package flaxbeard.thaumicexploration.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import cpw.mods.fml.common.network.PacketDispatcher;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class TileEntityReplicator extends TileEntity implements ISidedInventory, IWandable, IAspectContainer{

	  private static final int[] slots = { 0 };
	  public boolean crafting;
	  private ItemStack[] oldInventory = new ItemStack[1];
	  private ItemStack[] inventory = new ItemStack[1];
	  public int ticksLeft;
	  public AspectList recipeEssentia = new AspectList();
	  public AspectList displayEssentia = new AspectList();
	  private ArrayList<ChunkCoordinates> sources = new ArrayList();
	  private int soundTicks;
	  private int essentiaTicks;
	  
	  public boolean redstoneState = false;
		
	
	    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	    {
			System.out.println("daffy duck the fuck");
	    	super.writeToNBT(par1NBTTagCompound);
			
	    	this.writeInventoryNBT(par1NBTTagCompound);
	    }
	    
		
	    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	    {
			super.readFromNBT(par1NBTTagCompound);
			this.readInventoryNBT(par1NBTTagCompound);
	    }
	  
	@Override
	public int getSizeInventory() {
		return 1;
	}
	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory[i];
	}
	
	public void startCrafting() {
		System.out.println("start crafting - 2");
		if (this.getStackInSlot(0) != null && this.getStackInSlot(0).stackSize == 0 && !this.worldObj.isRemote) {
			System.out.println("start crafting - 3");
			System.out.println("start crafting - 4");
			this.oldInventory = inventory;
			this.crafting = true;
			this.ticksLeft = 100;
			ItemStack example = this.getStackInSlot(0).copy();
			example.stackSize = 1;
	        AspectList ot = ThaumcraftCraftingManager.getObjectTags(example);
	        ot = ThaumcraftCraftingManager.getBonusTags(example, ot);
			this.recipeEssentia = ot;
	        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:craftstart", 0.5F, 1.0F);
	        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			this.essentiaTicks = 0;
		}
	}
	
	public void cancelCrafting() {
    	this.crafting = false;
    	this.recipeEssentia = new AspectList();
    	this.displayEssentia = new AspectList();
    	System.out.println("craftingfail - 2");
	}
	
	public void updateEntity() {
        if (this.crafting && this.worldObj.isRemote && this.ticksLeft < 100) {
			ItemStack example = this.getStackInSlot(0).copy();
			example.stackSize = 1;
			AspectList ot = ThaumcraftCraftingManager.getObjectTags(example);
	        ot = ThaumcraftCraftingManager.getBonusTags(example, ot);
			for (int i = 0; i<5; i++) {
				ThaumicExploration.proxy.spawnFragmentParticle(this.worldObj, this.xCoord + 0.5F + (2*Math.random() - 1.0F), this.yCoord  + 1.5F + (2*Math.random() - 1.0F), this.zCoord  + 0.5F + (2*Math.random() - 1.0F), this.xCoord  + 0.5F, this.yCoord + 1.5F, this.zCoord  + 0.5F, Block.blocksList[example.itemID], example.getItemDamage());

			}
			
			
			if (this.worldObj.rand.nextInt(4) == 0 && this.ticksLeft > 40) {
				ThaumicExploration.proxy.spawnEssentiaAtLocation(this.worldObj, this.xCoord + 0.5F + (2*Math.random() - 1.0F), this.yCoord  + 1.5F + (2*Math.random() - 1.0F), this.zCoord  + 0.5F + (2*Math.random() - 1.0F), this.xCoord  + 0.5F, this.yCoord + 1.5F, this.zCoord  + 0.5F, 5, ot.getAspects()[this.worldObj.rand.nextInt(ot.getAspects().length)].getColor());
			}
			if (this.worldObj.rand.nextInt(3) == 0)
			{
				ThaumicExploration.proxy.spawnBoreSparkle(this.worldObj, this.xCoord + 0.5F + (2*Math.random() - 1.0F), this.yCoord  + 1.5F + (2*Math.random() - 1.0F), this.zCoord  + 0.5F + (2*Math.random() - 1.0F), this.xCoord  + 0.5F, this.yCoord + 1.5F, this.zCoord  + 0.5F);
			}
		}

		if (this.crafting && !this.worldObj.isRemote) {
			System.out.println("crafting - 1");
			
			
	        if (true)
	        {
		        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

	        	getSurroundings();
	        	essentiaTicks++;
	            if (this.recipeEssentia.visSize() > 0)
	            {
	            	
		        	System.out.println("vs > 0");
		        if (essentiaTicks > 49) {
		        	essentiaTicks = 0;
	              for (Aspect aspect : this.recipeEssentia.getAspects()) {
	                if (this.recipeEssentia.getAmount(aspect) > 0)
	                {
	                  for (ChunkCoordinates cc : this.sources)
	                  {
	                    TileEntity te = this.worldObj.getBlockTileEntity(cc.posX, cc.posY, cc.posZ);
	                    if ((te != null) && ((te instanceof IAspectSource)))
	                    {
	                      IAspectSource as = (IAspectSource)te;
	                      if (as.doesContainerContainAmount(aspect, 1))
	                      {
	                        as.takeFromContainer(aspect, 1);
	                        this.recipeEssentia.reduce(aspect, 1);
	                        
	                        ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
	            	        DataOutputStream outputStream = new DataOutputStream(bos);
	            	
	            	        try
	            	        {
	            	            outputStream.writeByte(6);
	            	            outputStream.writeInt(this.worldObj.provider.dimensionId);
	            	            outputStream.writeInt(this.xCoord);
	            	            outputStream.writeInt(this.yCoord);
	            	            outputStream.writeInt(this.zCoord);
	            	            outputStream.writeInt(cc.posX);
	            	            outputStream.writeInt(cc.posY);
	            	            outputStream.writeInt(cc.posZ);
	            	            outputStream.writeInt(aspect.getColor());
	            	           
	            	        }
	            	        catch (Exception ex)
	            	        {
	            	            ex.printStackTrace();
	            	        }
	            	
	            	        Packet250CustomPayload packet = new Packet250CustomPayload();
	            	        packet.channel = "tExploration";
	            	        packet.data = bos.toByteArray();
	            	        packet.length = bos.size();
	            	        PacketDispatcher.sendPacketToAllPlayers(packet);

	                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	                        return;
	                      }
	                    }
	                  }
	                  Aspect[] ingEss = this.recipeEssentia.getAspects();
	                }
	              }

	            }
	            }
	            else {
		            if (this.ticksLeft > 0) {
		            	if (this.essentiaTicks > 49) {
		            		if (260-this.ticksLeft % 40 == 0) {
		            			this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "thaumcraft:rumble", 0.5F, 1.0F);
		            		}
			            	System.out.println(ticksLeft);
			            	this.ticksLeft--;

		            	}
		            }
		            else
		            {
		        		ItemStack stack = this.getStackInSlot(0).copy();
		        		stack.stackSize++;
		        		this.setInventorySlotContents(0, stack);
		        		this.crafting = false;
		        		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		        		this.recipeEssentia = new AspectList();
		            }
	            }
	        	
	        }
		}
	}
	
	public boolean validLocation()
	{
		if (this.worldObj.getBlockMaterial(this.xCoord, this.yCoord + 1, this.zCoord) != Config.airyMaterial && this.worldObj.getBlockMaterial(this.xCoord, this.yCoord + 1, this.zCoord) != Material.air && !Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord)].isBlockReplaceable(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord)) {
			return false;
		}
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		System.out.println("removal by decrStackSize");
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
		// TODO Auto-generated method stub
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
		System.out.println("set contesnts");
		//if (itemstack != null) {
			this.inventory[i] = itemstack;
			if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
				itemstack.stackSize = getInventoryStackLimit();
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		//}
	}
	private void getSurroundings()
	  {
	    ArrayList<ChunkCoordinates> stuff = new ArrayList();
	    this.sources.clear();
	    for (int xx = -12; xx <= 12; xx++) {
	      for (int zz = -12; zz <= 12; zz++)
	      {
	        boolean skip = false;
	        for (int yy = -5; yy <= 10; yy++) {
	          if ((xx != 0) || (zz != 0))
	          {
	            int x = this.xCoord + xx;
	            int y = this.yCoord - yy;
	            int z = this.zCoord + zz;
	            
	            TileEntity te = this.worldObj.getBlockTileEntity(x, y, z);
	            if ((te != null) && ((te instanceof IAspectSource)))
	            {
	              this.sources.add(new ChunkCoordinates(x, y, z));
	            }
	          }
	        }
	      }
	    }
	  }
	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "replicator";
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
		return true;
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
		return this.inventory[i].stackSize > 0;
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
		System.out.println("Getting" + nbttagcompound.getInteger("Ticks"));
		this.ticksLeft = nbttagcompound.getInteger("Ticks");
		this.crafting = nbttagcompound.getBoolean("Crafting");
		AspectList readAspects = new AspectList();
		NBTTagCompound aspects = nbttagcompound.getCompoundTag("Aspects");
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound aspect = aspects.getCompoundTag((String) next);
			int amount = aspect.getInteger("Amount");
			if (amount > 0) {
				readAspects.add(Aspect.getAspect((String) next), amount);
			}
		}
		this.recipeEssentia = readAspects;
	}
	  
	public void writeInventoryNBT(NBTTagCompound nbttagcompound)
	{
		System.out.println("save that shit");
		nbttagcompound.setBoolean("Crafting", this.crafting);
		nbttagcompound.setInteger("Ticks", this.ticksLeft);
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
		NBTTagCompound aspects = new NBTTagCompound();
    	
		Iterator iterator =  Aspect.aspects.keySet().iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Amount", this.recipeEssentia.getAmount(Aspect.getAspect((String) next)));
			aspects.setCompoundTag((String) next, tag);
		}
		nbttagcompound.setCompoundTag("Aspects", aspects);
	}
	
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		this.readInventoryNBT(pkt.data);
	}
	
	

	
	public Packet getDescriptionPacket() {
		super.getDescriptionPacket();
		NBTTagCompound access = new NBTTagCompound();
		this.writeInventoryNBT(access);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, access);
	}
	@Override
	public AspectList getAspects() {
		// TODO Auto-generated method stub
		return this.recipeEssentia;
	}
	@Override
	public void setAspects(AspectList aspects) {}
	
	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return true;
	}
	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}
	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}
	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}
	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		return false;
	}
	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
	}
	@Override
	public int containerContains(Aspect tag) {
		return 0;
	}
	@Override
	public int onWandRightClick(World world, ItemStack wandstack,
			EntityPlayer player, int x, int y, int z, int side, int md) {
		if (!this.crafting) {
			System.out.println("start crafting - 1");
			this.startCrafting();
			return 0;
		}
		return -1;
	}
	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack,
			EntityPlayer player) {
		return wandstack;
	}
	
	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player,
			int count) {}
	
	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world,
			EntityPlayer player, int count) {}

	public void updateRedstoneState(boolean flag) {
		if (flag != redstoneState && !this.crafting && flag == true) {
			this.startCrafting();
			
		}
		redstoneState = flag;
		
	}
	
	
}
