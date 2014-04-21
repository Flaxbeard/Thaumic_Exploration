package flaxbeard.thaumicexploration.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlankSeal extends Item {

	public static final String[] itemNames = {"Pale", "Orange", "Magenta", "Light Blue", "Yellow", "Lime", "Pink", "Gray", "Light Gray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Dark"} ;
	public ItemBlankSeal(int par1) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
    	if (par1ItemStack.getItemDamage() <= 15) {
    	int j = 15 - par1ItemStack.getItemDamage();
    	Color c = new Color(EntitySheep.fleeceColorTable[j][0],EntitySheep.fleeceColorTable[j][1],EntitySheep.fleeceColorTable[j][2]);
        return ( c.getRGB() & 0x00ffffff);
    	}
    	return 0;
    }
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemID, CreativeTabs tab,
                    List itemList) {
            for(int i = 0; i < itemNames.length; i++){
                    itemList.add(new ItemStack(itemID,1,i));
            }
    }
    
    @Override
    public String getUnlocalizedName(ItemStack item) {
    	if (item.getItemDamage() <= 15) {
            return "item.thaumicexploration:blankSeal:" + itemNames[15-item.getItemDamage()];
    	}
    	return "";
    }

}
