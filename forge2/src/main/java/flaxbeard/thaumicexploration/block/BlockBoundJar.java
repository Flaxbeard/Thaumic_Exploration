package flaxbeard.thaumicexploration.block;

import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.ItemEssence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.tile.TileEntityBoundJar;

public class BlockBoundJar extends BlockJar {

	public BlockBoundJar(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}
	
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityBoundJar();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
    }
	
	
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ConfigBlocks.blockJar.blockID;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
    	if (!world.isRemote) {
    		System.out.println(((TileEntityBoundJar)world.getBlockTileEntity(x,  y,  z)).id);
    		System.out.println(((TileEntityBoundJar)world.getBlockTileEntity(x,  y,  z)).myJarData.getJarAmount());
    	}
    	TileEntity te = world.getBlockTileEntity(x,  y,  z);
	    if (te == null || !(te instanceof TileEntityBoundJar))
	        return false;
	            
	    TileEntityBoundJar jar = (TileEntityBoundJar)te;
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
        if (helditem != null && jar.amount <= (jar.maxAmount - 8) && (jar.aspect == null || (jar.aspect != null && jar.aspect == aspect && amount >= 8)))
        {
            player.getHeldItem().stackSize--;
            jar.addToContainer(aspect, amount);
            player.inventory.addItemStackToInventory(new ItemStack(ConfigItems.itemEssence, 1 ,0));
            world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);
        }
        else if (helditem != null && jar.amount >= 8 && aspect == null)
        {
            player.inventory.decrStackSize(player.inventory.currentItem, 1);
            ItemStack newPhial = new ItemStack(ConfigItems.itemEssence, 1, 1);
            AspectList setAspect = new AspectList().add(jar.aspect, 8);
            ((ItemEssence)newPhial.getItem()).setAspects(newPhial, setAspect);
            player.inventory.addItemStackToInventory(newPhial);
            jar.takeFromContainer(jar.aspect, 8);
            world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);
             
        }
		return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }

}
