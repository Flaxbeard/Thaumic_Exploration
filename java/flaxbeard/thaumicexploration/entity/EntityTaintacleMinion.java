package flaxbeard.thaumicexploration.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import thaumcraft.common.entities.ITaintedMob;
import thaumcraft.common.entities.monster.EntityTaintacleSmall;
import thaumcraft.common.lib.world.DamageSourceThaumcraft;

public class EntityTaintacleMinion extends EntityTaintacleSmall {

	public EntityTaintacleMinion(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}
	
	public void playSpawnSound() {
	      playSound("thaumcraft:tentacle", getSoundVolume(), getSoundPitch());
	}
	
	@Override
	protected Entity findPlayerToAttack()
	  {
	    double d0 = 16.0D;
	    Entity entity = null;
	    
	    List ents = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(16.0D, 8.0D, 16.0D));
	    double distance;
	    if (ents.size() > 0)
	    {
	      distance = 1.7976931348623157E+308D;
	      for (Object ent : ents) {
	        if (ent != null)
	        {
	          EntityLivingBase el = (EntityLivingBase)ent;
	          double d = el.getDistanceSqToEntity(this);
	          if (!(el instanceof EntityTaintacleMinion) && (el instanceof EntityMob) && (d < distance))
	          {
	            distance = d;
	            entity = el;
	          }
	        }
	      }
	    }
	    return entity;
	  }

}
