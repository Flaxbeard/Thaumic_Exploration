package flaxbeard.thaumicexploration.misc;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class FakePlayerPotion extends EntityPlayer {

	public FakePlayerPotion(World world, GameProfile name) {
		super(world, name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    protected void onNewPotionEffect(PotionEffect par1PotionEffect)
    {
        if (!this.worldObj.isRemote)
        {
            Potion.potionTypes[par1PotionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), par1PotionEffect.getAmplifier());
        }
    }

	@Override
	public void addChatMessage(IChatComponent var1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canCommandSenderUseCommand(int var1, String var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

}
