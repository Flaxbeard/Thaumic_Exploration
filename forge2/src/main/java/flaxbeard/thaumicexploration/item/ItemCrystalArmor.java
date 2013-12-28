package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscounter;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.client.lib.LibResources;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystalArmor extends ItemArmor implements IRepairable, IVisDiscounter{

	public ItemCrystalArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial,
			int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return super.getArmorModel(entityLiving, itemStack, armorSlot);
		//return this.armorType == 1 ? new ModelCrystalChest() : super.getArmorModel(entityLiving, itemStack, armorSlot);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return slot == 2 ? LibResources.MODEL_ARMOR_ICHOR_2 : LibResources.MODEL_ARMOR_ICHOR_1;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public int getVisDiscount() {
		// TODO Auto-generated method stub
		return this.armorType == 3 ? 1 : 2;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount() + "%");
	}

}
