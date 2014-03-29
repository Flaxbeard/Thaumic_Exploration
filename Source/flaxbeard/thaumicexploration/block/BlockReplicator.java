package flaxbeard.thaumicexploration.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.tuple.MutablePair;

import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.InventoryHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityReplicator;

public class BlockReplicator extends BlockContainer {
	
	//public static ArrayList<Integer> allowedItems = new ArrayList<Integer>();
	
	
	public Icon[] icon = new Icon[3];

	public BlockReplicator(int par1) {
		super(par1, Material.iron);
	}
	
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	    TileEntity tileEntity = par1World.getBlockTileEntity(par2, par3, par4);
	    if (tileEntity != null && tileEntity instanceof TileEntityReplicator) {
	    	if (((TileEntityReplicator)tileEntity).getStackInSlot(0) != null && ((TileEntityReplicator)tileEntity).getStackInSlot(0).stackSize > 0) {
	    		InventoryHelper.dropItems(par1World, par2, par3, par4);
	    	}
	    }
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityReplicator();
	}
	
	public boolean isOpaqueCube()
    {
        return false;
    }
	
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z);

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	    if ((tileEntity != null && tileEntity instanceof TileEntityReplicator))
	    {
	    	TileEntityReplicator ped = (TileEntityReplicator)tileEntity;
	    	ped.updateRedstoneState(flag);
	    }
    }

	
	  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9)
	  {
	    if (world.isRemote) {
	      return true;
	    }
	    int metadata = world.getBlockMetadata(x, y, z);
	    TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	    if ((tileEntity instanceof TileEntityReplicator))
	    {
	      TileEntityReplicator ped = (TileEntityReplicator)tileEntity;
	      if (ped.crafting && (player.getCurrentEquippedItem() == null || !(player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting))) {
	    	  ped.cancelCrafting();
	      }

		      if (ped.getStackInSlot(0) != null  && (player.getCurrentEquippedItem() == null || !(player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting)))
		      {
		    	ItemStack itemstack = ped.getStackInSlot(0);
	
		    	if (itemstack.stackSize > 0) {
		    		float f = world.rand.nextFloat() * 0.8F + 0.1F;
	                float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
	                float f2 = world.rand.nextFloat();
	                EntityItem entityitem;
			    	int k1 = world.rand.nextInt(21) + 10;
		
		            k1 = itemstack.stackSize;
		
		            entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
		            float f3 = 0.05F;
		            entityitem.motionX = (double)((float)world.rand.nextGaussian() * f3);
		            entityitem.motionY = (double)((float)world.rand.nextGaussian() * f3 + 0.2F);
		            entityitem.motionZ = (double)((float)world.rand.nextGaussian() * f3);
		
		            if (itemstack.hasTagCompound())
		            {
		                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
		            }
		            world.spawnEntityInWorld(entityitem);
		    	}
		    	TileEntity te = world.getBlockTileEntity(x, y, z);
		    	ItemStack template = ((TileEntityReplicator) te).getStackInSlot(0).copy();
		    	if (template.stackSize > 0) {
			    	template.stackSize = template.stackSize -=1;
	
			    	((TileEntityReplicator) te).setInventorySlotContents(0, template);
		    	}
		    	else
		    	{
		    	((TileEntityReplicator) te).setInventorySlotContents(0, null);
		    	}
		        world.markBlockForUpdate(x, y, z);
		        world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.5F);
		        
	
		        return true;
		        
		      }
		      if (player.getCurrentEquippedItem() != null && (ThaumicExploration.allowedItems.contains(MutablePair.of(player.getCurrentEquippedItem().itemID,player.getCurrentEquippedItem().getItemDamage())) || ThaumicExploration.allowedItems.contains(MutablePair.of(player.getCurrentEquippedItem().itemID,OreDictionary.WILDCARD_VALUE))))
		      {
		    	//IN
		        ItemStack i = player.getCurrentEquippedItem().copy();
		        i.stackSize = 0;
		        ped.setInventorySlotContents(0, i);
		        //player.getCurrentEquippedItem().stackSize -= 1;
		        if (player.getCurrentEquippedItem().stackSize == 0) {
		          player.setCurrentItemOrArmor(0, null);
		        }
		        player.inventory.onInventoryChanged();
		        world.markBlockForUpdate(x, y, z);
		        world.playSoundEffect(x, y, z, "random.pop", 0.2F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 1.6F);
		        
		        return true;
		      
		    }
	    }
	   
	    return true;
	  }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public int getRenderType()
    {
        return ThaumicExploration.replicatorRenderID;
    }
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		super.registerIcons(ir);
		this.icon[0] = ir.registerIcon("thaumicexploration:replicatorBottom");
		this.icon[1] = ir.registerIcon("thaumicexploration:replicator");
		this.icon[2] = ir.registerIcon("thaumicexploration:replicatorTop");
	}
	
	public Icon getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int side)
	{
		if (side == 0 || side == 1) {
			return this.icon[0];
		}
		return this.icon[1];
	}

}
