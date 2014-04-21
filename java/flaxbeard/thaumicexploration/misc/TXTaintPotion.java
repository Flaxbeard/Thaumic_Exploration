package flaxbeard.thaumicexploration.misc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.PotionFluxTaint;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;
import flaxbeard.thaumicexploration.event.DamageSourceTX;

public class TXTaintPotion extends PotionFluxTaint {

	public TXTaintPotion(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
	    setIconIndex(0, 0);
	}
	
	  @SideOnly(Side.CLIENT)
	  public int getStatusIconIndex()
	  {
	    UtilsFX.bindTexture("textures/misc/potions.png");
	    return super.getStatusIconIndex();
	  }
	
	@Override
	public void performEffect(EntityLivingBase target, int par2)
	{
		if (target.worldObj.getBiomeGenForCoords((int)target.posX, (int)target.posZ) == ThaumcraftWorldGenerator.biomeTaint) {
			target.removePotionEffect(ThaumicExploration.potionTaintWithdrawl.id);
		}
		if ((!target.isEntityUndead()) && ((target.getMaxHealth() > 1.0F) || ((target instanceof EntityPlayer)))) {
			target.attackEntityFrom(DamageSourceTX.noTaint, 1.0F);
		}
  }
	

}
