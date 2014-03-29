package flaxbeard.thaumicexploration.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.tiles.TileJarFillable;
import flaxbeard.thaumicexploration.data.BoundJarWorldData;

public class TileEntityBoundJar extends TileJarFillable {
	public BoundJarWorldData myJarData;
    public int accessTicks = 0;
    public int id = 0;
    
    public int clientColor = 0;
    
    public int getMinimumSuction()
    {
      return 40;
    }
    
    public Aspect getSuctionType(ForgeDirection loc)
    {
      return this.aspect;
    }
    
    public int getSuctionAmount(ForgeDirection loc)
    {
      if (this.amount < this.maxAmount)
      {
        return 40;
      }
      return 0;
    }
    
    public Aspect getEssentiaType(ForgeDirection loc)
    {
      return this.aspect;
    }
    
    public int getEssentiaAmount(ForgeDirection loc)
    {
      return this.amount;
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound access = new NBTTagCompound();
        access.setInteger("accessTicks", this.accessTicks);
        access.setInteger("amount", this.amount);
        if (this.aspect != null) {
        	access.setString("aspect", this.aspect.getTag());
        }
        access.setInteger("color", this.getSealColor());
        
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, access);
    }
    
	public int getAccessTicks() {
		return this.accessTicks;
		
	}
    
    public void setColor(int color) {
    	if (this.id > 0) {
    		myJarData = BoundJarWorldData.get(this.worldObj, "jar" + id, 0);
        }
        if (myJarData != null) {
        	myJarData.setSealColor(color);
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
    	NBTTagCompound access = pkt.func_148857_g();
    	this.accessTicks = access.getInteger("accessTicks");
    	this.amount = access.getInteger("amount");
    	if (access.getString(("aspect")) != null) {
    		this.aspect = Aspect.getAspect(access.getString("aspect"));
    	}
    	this.setColor(access.getInteger("color"));
    	this.clientColor = access.getInteger("color");
    	
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    public void readCustomNBT(NBTTagCompound nbttagcompound)
    {
      this.aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
      this.amount = nbttagcompound.getShort("Amount");
      this.facing = nbttagcompound.getByte("facing");
      if (nbttagcompound.hasKey("jarID"))
      {
          this.id = nbttagcompound.getInteger("jarID");
      }
    }
    
    public void writeCustomNBT(NBTTagCompound nbttagcompound)
    {
      if (this.aspect != null) {
        nbttagcompound.setString("Aspect", this.aspect.getTag());
      }
      nbttagcompound.setShort("Amount", (short)this.amount);
      nbttagcompound.setByte("facing", (byte)this.facing);
      nbttagcompound.setInteger("jarID", this.id);
    }
    
	public int getSealColor() {
		if (this.worldObj.isRemote) {
			return this.clientColor;
		}
		if (myJarData == null) {
			myJarData = BoundJarWorldData.get(this.worldObj, "jar" + id, 0);
		}
		return this.myJarData.getSealColor();
	}

	@Override
	public int addToContainer(Aspect tt, int am) {
		this.updateEntity();
		if (myJarData == null) {
			myJarData = BoundJarWorldData.get(this.worldObj, "jar" + id, 0);
		}
	    if (am == 0) {
	        return am;
	    }
		if (((this.amount < this.maxAmount) && (tt == this.aspect)) || (this.amount == 0))
	    {
	      this.aspect = tt;
	      int added = Math.min(am, this.maxAmount - this.amount);
	      this.amount += added;
	      am -= added;
	      if (!this.worldObj.isRemote) {
	    	  myJarData.updateJarContents(tt, this.amount);
	      }
	    }
    	this.accessTicks = 80;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		return am;
	}
	
	@Override
	public boolean takeFromContainer(Aspect tt, int am)
	{
		this.updateEntity();
		if ((this.amount >= am) && (tt == this.aspect))
		{
			this.amount -= am;
			if (this.amount <= 0)
			{
				this.aspect = null;
				this.amount = 0;
			}
        	this.accessTicks = 80;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			if (!this.worldObj.isRemote) {
				myJarData.updateJarContents(tt, this.amount);
			}
			return true;
	    }
	    return false;
	}
	
    public void updateEntity()
    {
    	if (!this.worldObj.isRemote) {
            if (this.accessTicks > 0 && !this.worldObj.isRemote) {
            	--this.accessTicks;
            	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
	    	if (myJarData == null) {
				myJarData = BoundJarWorldData.get(this.worldObj, "jar" + id, 0);
			}
	    	
			if (this.amount != myJarData.getJarAmount()) {
				this.amount = myJarData.getJarAmount();
            	this.accessTicks = 80;
            	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			if (this.aspect != myJarData.getJarAspect()) {
				this.aspect = myJarData.getJarAspect();
            	this.accessTicks = 80;
            	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
	
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    	}
    	super.updateEntity();
    }

	public void emptyJar() {
		if (!this.worldObj.isRemote) {
			this.myJarData.updateJarContents(null, 0);
		}
	}
	
	
}
