package flaxbeard.thaumicexploration.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import thaumcraft.common.tiles.TileJarFillable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.tile.TileEntityTrashJar;


public class BlockTrashJar extends BlockJar {

	private Random random;

	public BlockTrashJar() {
		super();
	}
	
	  public int getRenderType()
	  {
	    return ThaumicExploration.trashJarRenderID;
	  }
	
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityTrashJar();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
	
	@Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(ThaumicExploration.trashJar);
    }
    
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
	
	}
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
      ArrayList<ItemStack> drops = new ArrayList();

      return drops;
      
    }
    
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
    	if (!world.isRemote) {
    		world.markBlockForUpdate(x,  y,  z);
    	}
    	TileEntity te = world.getTileEntity(x,  y,  z);
	    if (te == null || !(te instanceof TileEntityTrashJar))
	        return false;
	            
	    TileEntityTrashJar jar = (TileEntityTrashJar)te;
	    ItemStack helditem = player.getHeldItem();
	        
	    if (helditem == null || !(helditem.getItem() instanceof ItemEssence))
	        return false;
	        
	    ItemEssence phial = (ItemEssence)helditem.getItem();
	    Aspect aspect = null;
	    int amount = 0;
	    if (helditem.getItemDamage() == 1) {
	    	Aspect[] aspects = phial.getAspects(helditem).getAspects();
		    if (aspects.length > 0) {
		        aspect = aspects[0];
		    }
		    amount = phial.getAspects(helditem).getAmount(aspect);
	    }
        if (helditem != null && jar.amount <= (jar.maxAmount - 8) && ((jar.aspect != null && jar.aspect != aspect && jar.amount == 0) || jar.aspect == null || (jar.aspect != null && jar.aspect == aspect && amount >= 8)))
        {
            player.getHeldItem().stackSize--;
            jar.aspect = aspect;
            jar.addToContainer(aspect, amount);
            if(!player.inventory.addItemStackToInventory(new ItemStack(ConfigItems.itemEssence, 1 ,0))) {
            	player.dropItem(ConfigItems.itemEssence,1);
            }
            world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);
        }
        else if (helditem != null && jar.amount >= 8 && aspect == null)
        {
            player.inventory.decrStackSize(player.inventory.currentItem, 1);
            ItemStack newPhial = new ItemStack(ConfigItems.itemEssence, 1, 1);
            AspectList setAspect = new AspectList().add(jar.aspect, 8);
            ((ItemEssence)newPhial.getItem()).setAspects(newPhial, setAspect);
            if(!player.inventory.addItemStackToInventory(newPhial)) {
            	player.dropItem(newPhial.getItem(),1);
            }
            jar.takeFromContainer(jar.aspect, 8);
            world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);
             
        }
		return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }

}
