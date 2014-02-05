package flaxbeard.thaumicexploration.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class ItemChestSealLinked extends Item {
	public Icon theIcon;
	public static final String[] itemNames = {"Pale", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Dark"} ;
	public ItemChestSealLinked(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
	}
	
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
//    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
//    {
//        if (!player.canPlayerEdit(x, y, z, side, stack))
//        {
//            return false;
//        }
//        else
//        {
//        	int var11 = world.getBlockId(x, y, z);
//        	if (var11 == Block.chest.blockID) {
//        		stack = new ItemStack(this.itemID, 1, stack.getItemDamage());
//        		return true;
//        		
//        	}
//        	else
//        	{
//        		return false;
//        	}
//        }
//    }
	
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	if (par1ItemStack.getItemDamage() <= 15) {
	    	if (par2 == 0) {
	    		int j = 15 - par1ItemStack.getItemDamage();
		    	Color c = new Color(EntitySheep.fleeceColorTable[j][0],EntitySheep.fleeceColorTable[j][1],EntitySheep.fleeceColorTable[j][2]);
		        return ( c.getRGB() & 0x00ffffff);
	    	}
	    	else
	    	{
	    		int j = 15 - par1ItemStack.getItemDamage();
		    	Color c = new Color(EntitySheep.fleeceColorTable[j][0],EntitySheep.fleeceColorTable[j][1],EntitySheep.fleeceColorTable[j][2]);
		    	Color c2 = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());;
		    	return ( c2.getRGB() & 0x00ffffff);
	    	}
    	}
    	return 0;
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.theIcon = par1IconRegister.registerIcon(this.iconString + "Inset");
    }
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }
        else
        {
        	int var11 = world.getBlockId(x, y, z);
        	if (var11 == Block.chest.blockID) {
        		stack = new ItemStack(ThaumicExploration.chestSealLinked.itemID, 1, stack.getItemDamage());
        		return true;
        		
        	}
        	else
        	{
        		return false;
        	}
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs tab,
                    List itemList) {
            for(int i = 0; i < itemNames.length; i++){
                    itemList.add(new ItemStack(itemID,1,i));
            }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack item) {
    	return this.getUnlocalizedName() + ":" + itemNames[15-item.getItemDamage()];
    }
    
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	String dimension = "dimension " + par1ItemStack.stackTagCompound.getInteger("dim");
    	if (par1ItemStack.stackTagCompound.getInteger("dim") == 0) {
    		dimension = "Overworld";
    	}
    	if (par1ItemStack.stackTagCompound.getInteger("dim") == -1) {
    		dimension = "Nether";
    	}
    	if (par1ItemStack.stackTagCompound.getInteger("dim") == 1) {
    		dimension = "End";
    	}
    	par3List.add("Linked to " + par1ItemStack.stackTagCompound.getInteger("x") + "," + par1ItemStack.stackTagCompound.getInteger("y") + "," + par1ItemStack.stackTagCompound.getInteger("z") + " in " + dimension + " (Network " + par1ItemStack.stackTagCompound.getInteger("ID") + ")");
    }
    
    
    public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }

}
