package flaxbeard.thaumicexploration.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.common.items.wands.ItemWandCasting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityNecroPedestal;

public class BlockNecroPedestal extends BlockContainer{

	  public Icon[] iconPedestal = new Icon[6];
	  
	  public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, List arraylist, Entity par7Entity) {
		  float W8 = 0.5F;
		  float W4 = 0.25F;
		  float W1 = 0.0625F;
	      setBlockBounds(0.0F-W4, 0.0F, 0.0F-W4, 1.0F+W4, 1.0F, 1.0F+W4);
	      super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, par7Entity);
	  }
	  
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
	    this.iconPedestal[0] = ir.registerIcon("thaumicexploration:pedestal_side");
	    this.iconPedestal[1] = ir.registerIcon("thaumicexploration:pedestal_top");
	    this.iconPedestal[2] = ir.registerIcon("thaumicexploration:pedestal_sideAlt");
	    this.iconPedestal[3] = ir.registerIcon("thaumicexploration:pedestal_topMid");
	    this.iconPedestal[4] = ir.registerIcon("thaumicexploration:pedestal_topAlt");
	    this.iconPedestal[5] = ir.registerIcon("thaumicexploration:pedestal_sideMid");
    }
	public BlockNecroPedestal(int par1, Material par2Material) {
		super(par1, par2Material);
	    setHardness(3.0F);
	    setResistance(25.0F);
	}
	
	 public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	  {
//	    if (world.isRemote) {
//	      return true;
//	    }
	    int metadata = world.getBlockMetadata(x, y, z);
	    TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	    if (tileEntity instanceof TileEntityNecroPedestal && player.getCurrentEquippedItem() == null || (!(player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) && !(player.getCurrentEquippedItem().itemID == Item.flintAndSteel.itemID)))
	    {
	      TileEntityNecroPedestal ped = (TileEntityNecroPedestal)tileEntity;
	      	int highestFilledSlot = -1;
	      	int highestEmptySlot = -1;
			for (int i : ped.slots) {
				if (ped.getStackInSlot(i) == null) {
					highestEmptySlot = i;
					break;
				}
			}
			for (int i : ped.slots) {
				if (ped.getStackInSlot(i) != null) {
					highestFilledSlot = i;
				}
			}
			
	      if (player.isSneaking() && highestFilledSlot != -1 && !ped.crafting)
	      {
	    	ItemStack item = ped.getStackInSlot(highestFilledSlot);
	    	if (!world.isRemote){
	            float rx = world.rand.nextFloat() * 0.8F + 0.1F;
	            float ry = world.rand.nextFloat() * 0.8F + 0.1F;
	            float rz = world.rand.nextFloat() * 0.8F + 0.1F;
	            
	            EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
	            if (item.hasTagCompound()) {
	              entityItem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
	            }
	            float factor = 0.05F;
	            entityItem.motionX = (world.rand.nextGaussian() * factor);
	            entityItem.motionY = (world.rand.nextGaussian() * factor + 0.2000000029802322D);
	            entityItem.motionZ = (world.rand.nextGaussian() * factor);
	            world.spawnEntityInWorld(entityItem);
	            ped.setInventorySlotContents(highestFilledSlot, null);
	    	}
	        world.markBlockForUpdate(x, y, z);
	        world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
	        

	        return true;
	      }
	      else if (player.getCurrentEquippedItem() != null && !ped.crafting && ped.active && highestEmptySlot != -1)
	      {
	        ItemStack i = player.getCurrentEquippedItem().copy();
	        i.stackSize = 1;
	        ped.setInventorySlotContents(highestEmptySlot, i);
	        player.getCurrentEquippedItem().stackSize -= 1;
	        if (player.getCurrentEquippedItem().stackSize == 0) {
	          player.setCurrentItemOrArmor(0, null);
	        }
	        player.inventory.onInventoryChanged();
	        world.markBlockForUpdate(x, y, z);
	        world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
	        
	        return true;
	      }
	    }

	   
	    return super.onBlockActivated(world, x, y, z, player, side, par7, par8, par9);
	  }
	
	 public int getRenderType()
	  {
	    return ThaumicExploration.necroPedestalRenderID;
	  }
	  
	  public boolean isOpaqueCube()
	  {
	    return false;
	  }
	  
	  public boolean renderAsNormalBlock()
	  {
	    return false;
	  }
	  
	public Icon getIcon(int side, int md)
	{
	
		if (side <= 1) {
			return this.iconPedestal[1];
		}
		if (side > 1) {
			return this.iconPedestal[0];
		}
		return this.iconPedestal[0];
	}
	
	  public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k)
	  {
		  
	      setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	    }
	  
	  public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	  {
	    if (side == ForgeDirection.UP) {
	      return true;
	    }
	    return super.isBlockSolidOnSide(world, x, y, z, side);
	  }
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return new TileEntityNecroPedestal();
	}
	

}
