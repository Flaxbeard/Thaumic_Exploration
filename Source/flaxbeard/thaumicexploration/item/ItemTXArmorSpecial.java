package flaxbeard.thaumicexploration.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.armor.Hover;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class ItemTXArmorSpecial extends ItemArmor implements IRepairable{

        public ItemTXArmorSpecial(int par1, ItemArmor.ArmorMaterial par2EnumArmorMaterial,
                        int par3, int par4) {
                super(par2EnumArmorMaterial, par3, par4);
        }
        
        
        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
        	if (stack.getItem() == ThaumicExploration.bootsMeteor)
        		return "thaumicexploration:textures/models/armor/bootsMeteor.png";
        	return "thaumicexploration:textures/models/armor/bootsComet.png";
        }
        
        @Override
        public EnumRarity getRarity(ItemStack par1ItemStack) {
                return EnumRarity.rare;
        }


        @Override
        public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
        {
    	 if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.bootsMeteor)
         {
             if (player.fallDistance > 0.0F) {
                 player.fallDistance = 0.0F;
               }
         }
          if ((!player.capabilities.isFlying) && (player.moveForward > 0.0F))
          {
            int haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, player.inventory.armorItemInSlot(0));
            if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.bootsMeteor)
            {
              if (player.worldObj.isRemote)
              {
                if (!Thaumcraft.instance.entityEventHandler.prevStep.containsKey(Integer.valueOf(player.getEntityId()))) {
                	Thaumcraft.instance.entityEventHandler.prevStep.put(Integer.valueOf(player.getEntityId()), Float.valueOf(player.stepHeight));
                }
                player.stepHeight = 1.0F;
              }
              float bonus = 0.055F;
              if (player.isInWater()) {
                bonus /= 4.0F;
              }
              if (player.onGround)
              {
                player.moveFlying(0.0F, 1.0F, bonus);
              }
              else if (Hover.getHover(player.getEntityId()))
              {
                player.jumpMovementFactor = 0.03F;
              }
              else
              {
                player.jumpMovementFactor = 0.05F;
              }
              if (player.fallDistance > 0.0F) {
                player.fallDistance = 0.0F;
              }
            }
            else if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.bootsComet)
            {
                if (player.worldObj.isRemote)
                {
                  if (!Thaumcraft.instance.entityEventHandler.prevStep.containsKey(Integer.valueOf(player.getEntityId()))) {
                	  Thaumcraft.instance.entityEventHandler.prevStep.put(Integer.valueOf(player.getEntityId()), Float.valueOf(player.stepHeight));
                  }
                  player.stepHeight = 1.0F;
                }
                if (!player.inventory.armorItemInSlot(0).hasTagCompound()) {
          			NBTTagCompound par1NBTTagCompound = new NBTTagCompound();
          			player.inventory.armorItemInSlot(0).setTagCompound(par1NBTTagCompound );
          			player.inventory.armorItemInSlot(0).stackTagCompound.setInteger("runTicks",0);
                }
                int ticks = player.inventory.armorItemInSlot(0).stackTagCompound.getInteger("runTicks");
                float bonus = 0.110F;
                bonus = bonus+((ticks/5)*0.003F);
                if (player.isInWater()) {
                  bonus /= 4.0F;
                }
                if (player.onGround)
                {
                  player.moveFlying(0.0F, 1.0F, bonus);
                }
                else if (Hover.getHover(player.getEntityId()))
                {
                  player.jumpMovementFactor = 0.03F;
                }
                else
                {
                  player.jumpMovementFactor = 0.05F;
                }
                if (player.fallDistance > 0.25F) {
                  player.fallDistance -= 0.25F;
                }
              }
            
          }
        }
        

}