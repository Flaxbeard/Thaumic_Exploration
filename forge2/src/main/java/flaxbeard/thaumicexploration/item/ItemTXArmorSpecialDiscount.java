package flaxbeard.thaumicexploration.item;

import java.util.List;

import flaxbeard.thaumicexploration.ThaumicExploration;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscounter;
import thaumcraft.common.config.ConfigItems;

public class ItemTXArmorSpecialDiscount extends ItemArmor implements IRepairable, IVisDiscounter{

        public ItemTXArmorSpecialDiscount(int par1, EnumArmorMaterial par2EnumArmorMaterial,
                        int par3, int par4) {
                super(par1, par2EnumArmorMaterial, par3, par4);
        }
        
        
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        	if (stack.itemID == ThaumicExploration.maskEvil.itemID)
        		return "thaumicexploration:textures/models/armor/mask.png";
        	return "";
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
        	
                return 5;
        }
        
        @Override
        public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
        	if (stack.itemID == ThaumicExploration.maskEvil.itemID)
        		list.add(StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount() + "%");
        }

}