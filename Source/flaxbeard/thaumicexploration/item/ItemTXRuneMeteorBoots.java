package flaxbeard.thaumicexploration.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.items.armor.Hover;
import thaumcraft.common.items.armor.ItemRunicArmor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import flaxbeard.thaumicexploration.ThaumicExploration;

public class ItemTXRuneMeteorBoots extends ItemRunicArmor {
	protected IIcon[] icons = new IIcon[2];
	 public IIcon icon;
	
		public ItemTXRuneMeteorBoots(int par1, ItemArmor.ArmorMaterial par2EnumArmorMaterial,
	            int par3, int par4) {
	    super(par2EnumArmorMaterial, par3, par4);
	}
		
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir)
    {
        this.icon = ir.registerIcon("thaumcraft:runicbootstraveler");
    	this.icons[0] = ir.registerIcon("thaumicexploration:runicBootsComet");
    	this.icons[1] = ir.registerIcon("thaumicexploration:runicBootsMeteor");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
      return this.icons[1];
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
	if (stack.getItem() == ThaumicExploration.runicBootsMeteor)
		return "thaumicexploration:textures/models/armor/runicBootsMeteor.png";
	return "thaumicexploration:textures/models/armor/runicBootsComet.png";
	}
	
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
	  {
	    return false;
	  }
	  
	  public EnumRarity getRarity(ItemStack itemstack)
	  {
	    return EnumRarity.rare;
	  }
	
	  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	  {
	    list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("item.runic.charge") + ": " + (stack.getMaxDamage() - stack.getItemDamage()) + "/" + stack.getMaxDamage());
	    int u = getUpgrade(stack);
	    if (u > 0) {
	      list.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(new StringBuilder().append("item.runic.upgrade.").append(u).toString()));
	    }
	  }
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
	if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsMeteor)
	{
	 if (player.fallDistance > 0.0F) {
	     player.fallDistance = 0.0F;
	   }
	}
	if ((!player.capabilities.isFlying) && (player.moveForward > 0.0F))
	{
	int haste = EnchantmentHelper.getEnchantmentLevel(Config.enchHaste.effectId, player.inventory.armorItemInSlot(0));
	if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsMeteor)
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
	else if (player.inventory.armorItemInSlot(0).getItem() == ThaumicExploration.runicBootsComet)
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
	super.onArmorTick(world, player, itemStack);
	}
}
