package flaxbeard.thaumicexploration.item;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBrain extends Item{

	public ItemBrain(int par1) {
		super(par1);

	}
	
	 @Override
     public Entity createEntity(World world, Entity location, ItemStack itemstack) {
             
             return new EntityItemBrain(world, location.posX, location.posY, location.posZ, itemstack);
     }
     
     @Override
     public boolean hasCustomEntity(ItemStack stack) {
             return true;
     }
     
     
     

}
