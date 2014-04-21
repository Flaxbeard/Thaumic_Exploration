package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.items.armor.ItemRunicArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class ItemEnhancedRunicArmor extends ItemRunicArmor {

	public ItemEnhancedRunicArmor(int lvl,int i, ItemArmor.ArmorMaterial enumarmormaterial,
			int j, int k) {
		super(enumarmormaterial, j, k);
		if (lvl == 1) {
			this.setCreativeTab(ThaumicExploration.tab);
		}
	}
	
	@Override
    public int getMaxDamage(ItemStack stack)
    {
	    int md = ((ItemArmor)stack.getItem()).damageReduceAmount * 8;

	    if (getUpgrade1(stack) == 2 || getUpgrade2(stack) == 2) {
	    	if (getUpgrade1(stack) == 2 && getUpgrade2(stack) == 2) {
	    		md = (int)(md * 2.0D);
	    	}
	    	else
	    	{
	    		md = (int)(md * 1.5D);
	    	}
	    }
	    return md;
    }
	
	@Override
	public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot)
	{
	    int dra = ((ItemArmor)armor.getItem()).damageReduceAmount;
	    if (getUpgrade1(armor) == 5 || getUpgrade2(armor) == 5) {
		    if (getUpgrade1(armor) == 5 && getUpgrade2(armor) == 5) {
		    	dra *= 3;
		    }
		    else
		    {
		    	dra *= 2;
		    }
	    }
	    return new ISpecialArmor.ArmorProperties(1, dra / 25.0D, (int)damage);
	}
	  
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot)
	{
	    int dra = ((ItemArmor)armor.getItem()).damageReduceAmount;
	    if (getUpgrade1(armor) == 5 || getUpgrade2(armor) == 5) {
		    if (getUpgrade1(armor) == 5 && getUpgrade2(armor) == 5) {
		    	dra *= 3;
		    }
		    else
		    {
		    	dra *= 2;
		    }
	    }
		return dra;
	}

	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
	    list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.runic.charge") + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage());
	    int u = getUpgrade1(stack);
	    if (u > 0) {
	      list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(new StringBuilder().append("item.runic.upgrade.").append(u).toString()));
	    }
	    u = getUpgrade2(stack);
	    if (u > 0) {
	      list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(new StringBuilder().append("item.runic.upgrade.").append(u).toString()));
	    }
    	if (getVisDiscount(stack, player, null) > 0)
    		list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
//	    for (Aspect aspect : Aspect.getPrimalAspects()) {
//	    	String tag = "";
//			if (getVisDiscount(stack, player, aspect) > 0) {
//	    		tag = aspect.getTag().substring(0, 1).toUpperCase() + aspect.getTag().substring(1);
//	    	    list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + " ("+tag+"): " + getVisDiscount(stack, player, aspect) + "%");
//			}
//	    }
    }
	
	public static int getUpgrade2(ItemStack armor)
	{
	    if ((armor.hasTagCompound()) && (armor.stackTagCompound.hasKey("upgrade2"))) {
	    	return armor.stackTagCompound.getByte("upgrade2");
		}
		return 0;
	}
	public static int getUpgrade1(ItemStack armor)
	{
	    if ((armor.hasTagCompound()) && (armor.stackTagCompound.hasKey("upgrade"))) {
	    	return armor.stackTagCompound.getByte("upgrade");
		}
		return 0;
	}
	

	public static int getUpgrade(ItemStack armor)
	{
		return 0;
	}
	
	
	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.epic;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.iconHelm = ir.registerIcon("thaumicexploration:enhancedRunicHelmet");
	    this.iconChest = ir.registerIcon("thaumicexploration:enhancedRunicChest");
	    this.iconLegs = ir.registerIcon("thaumicexploration:enhancedRunicLeggings");
	    this.iconBoots = ir.registerIcon("thaumicexploration:enhancedRunicBoots");
	}
	  
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if (this.getUpgrade1(par1ItemStack) >0 && this.getUpgrade2(par1ItemStack) >0) {
    		int u1 = this.getUpgrade1(par1ItemStack);
    		int u2 = this.getUpgrade2(par1ItemStack);
    		par1ItemStack.stackTagCompound.setByte("upgrade2",(byte) u1);
    		par1ItemStack.stackTagCompound.setByte("upgrade",(byte) u2);
    		return true;
    	}
    	return false;
    }
    
    public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
    {
        return this.armorType == 3 ? 1 : 2;
    }

}
