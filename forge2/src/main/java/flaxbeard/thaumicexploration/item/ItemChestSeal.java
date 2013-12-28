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

public class ItemChestSeal extends Item {
	public static final String[] itemNames = {"Pale", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Dark"} ;
	public ItemChestSeal(int par1) {
		super(par1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
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
	
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	int j = 15 - par1ItemStack.getItemDamage();
    	Color c = new Color(EntitySheep.fleeceColorTable[j][0],EntitySheep.fleeceColorTable[j][1],EntitySheep.fleeceColorTable[j][2]);
        return ( c.getRGB() & 0x00ffffff);
    	
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

}
