package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import baubles.api.BaubleType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBaubleDiscounter extends ItemBauble implements IVisDiscountGear {

	private int percent;
	private AspectList aspects;

	public ItemBaubleDiscounter(BaubleType t, AspectList aspect, int perc) {
		super(t);
		percent = perc;
		aspects = aspect;
		if (this.aspects.size() == 0) {
			for (Aspect pAspect : Aspect.getPrimalAspects()) {
				aspects.add(pAspect, 1);
			}
		}
	}

	public ItemBaubleDiscounter(BaubleType t, Aspect aspect, int perc) {
		this(t,new AspectList().add(aspect, 1),perc);
	}
	
	@Override
	public int getVisDiscount(ItemStack arg0, EntityPlayer arg1, Aspect arg2) {
		if (this.aspects.size() == 6) {
			return percent;
		}
		if (aspects.getAmount(arg2) > 0) {
			return percent;
		}
		return 0;
	}
	
 
    
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (getVisDiscount(stack, player, null) > 0) {
			list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
		}
		else
		{
			for (Aspect pAspect : Aspect.getPrimalAspects()) {
				if (getVisDiscount(stack, player, pAspect) > 0) {
					list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + " ("+ pAspect.getName() + "): " + getVisDiscount(stack, player, pAspect) + "%");	
				}
			}
		}
		super.addInformation(stack, player, list, par4);
	}
}
