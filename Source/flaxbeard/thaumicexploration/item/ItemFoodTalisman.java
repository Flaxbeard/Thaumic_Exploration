package flaxbeard.thaumicexploration.item;

import java.util.List;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import flaxbeard.thaumicexploration.misc.FakePlayerPotion;

public class ItemFoodTalisman extends Item {

	public ItemFoodTalisman(int par1) {
		super();
		this.maxStackSize = 1;
		//this.setMaxDamage(100);
	}


	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	if (!par1ItemStack.hasTagCompound()) {
			par1ItemStack.setTagCompound(new NBTTagCompound());
		}
		if (!par1ItemStack.stackTagCompound.hasKey("saturation")) {
			par1ItemStack.stackTagCompound.setFloat("saturation", 0);
		}
		if (!par1ItemStack.stackTagCompound.hasKey("food")) {
			par1ItemStack.stackTagCompound.setFloat("food", 0);
		}
    	par3List.add("Currently holds " + (int)par1ItemStack.stackTagCompound.getFloat("food") + " food points and " + (int)par1ItemStack.stackTagCompound.getFloat("saturation") + " saturation points.");
    	//super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {

		if (par3Entity instanceof EntityPlayer && !par2World.isRemote && par3Entity.ticksExisted % 20 == 0) {
			EntityPlayer player = (EntityPlayer)par3Entity;

			if (!par1ItemStack.hasTagCompound()) {
				par1ItemStack.setTagCompound(new NBTTagCompound());
			}
			if (!par1ItemStack.stackTagCompound.hasKey("saturation")) {
				par1ItemStack.stackTagCompound.setFloat("saturation", 0);
			}
			if (!par1ItemStack.stackTagCompound.hasKey("food")) {
				par1ItemStack.stackTagCompound.setFloat("food", 0);
			}
			for (int i = 0; i<10; i++) {
				if (player.inventory.getStackInSlot(i) != null) {
					ItemStack food = player.inventory.getStackInSlot(i);
					if (isEdible(food, player)) {
						float sat = ((ItemFood)food.getItem()).func_150906_h(food) * 2;

						float heal = ((ItemFood)food.getItem()).func_150905_g(food);
						if (par1ItemStack.stackTagCompound.getFloat("food")+(int)heal < 100) {
							if (par1ItemStack.stackTagCompound.getFloat("saturation") + sat <= 100) {
								par1ItemStack.stackTagCompound.setFloat("saturation", par1ItemStack.stackTagCompound.getFloat("saturation") + sat);
							}
							else
							{
								par1ItemStack.stackTagCompound.setFloat("saturation", 100);
							}
							if (food.stackSize <= 1) {
								player.inventory.setInventorySlotContents(i, null);
							}
							player.inventory.decrStackSize(i, 1);

							player.playSound("random.eat", 0.5F + 0.5F * (float)player.worldObj.rand.nextInt(2), (player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
							par1ItemStack.stackTagCompound.setFloat("food", par1ItemStack.stackTagCompound.getFloat("food")+(int)heal);
						}
					}
				}
			}
			if ((player.getFoodStats().getFoodLevel() < 20) && (100-par1ItemStack.stackTagCompound.getFloat("food")) > 0) {
				float sat = par1ItemStack.stackTagCompound.getFloat("food");
				float finalSat = 0;
				if (20 - player.getFoodStats().getFoodLevel() < sat) {
					finalSat = sat - (20 - player.getFoodStats().getFoodLevel());
					sat = 20 - player.getFoodStats().getFoodLevel();
				}
				ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(),  (int) (player.getFoodStats().getFoodLevel() + sat), "field_75127_a");
				par1ItemStack.stackTagCompound.setFloat("food", finalSat);
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage());
			}
			if ((player.getFoodStats().getSaturationLevel() < player.getFoodStats().getFoodLevel()) && par1ItemStack.stackTagCompound.getFloat("saturation") > 0) {
				float sat = par1ItemStack.stackTagCompound.getFloat("saturation");
				float finalSat = 0;
				if (player.getFoodStats().getFoodLevel() - player.getFoodStats().getSaturationLevel() < sat) {
					finalSat = sat - (player.getFoodStats().getFoodLevel() - player.getFoodStats().getSaturationLevel());
					sat = player.getFoodStats().getFoodLevel() - player.getFoodStats().getSaturationLevel();	
				}
				ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, player.getFoodStats(),(player.getFoodStats().getFoodLevel() + sat), "field_75125_b");
				par1ItemStack.stackTagCompound.setFloat("saturation", finalSat);
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage());
			}
		}
	}

	private boolean isEdible(ItemStack food, EntityPlayer player) {
		if (food.getItem() instanceof ItemFood && food.getItem() != ConfigItems.itemManaBean) {

			for (int i = 1; i < 25; i++) {
				EntityPlayer fakePlayer = new FakePlayerPotion(player.worldObj, new GameProfile(null, "foodTabletPlayer"));
				fakePlayer.setPosition(0.0F, 999.0F, 0.0F);
				((ItemFood)food.getItem()).onEaten(food.copy(), player.worldObj, fakePlayer);
				if (fakePlayer.getActivePotionEffects().size() > 0) {
					return false;
				}
			}

			return true;
		}
		return false;
	}

}