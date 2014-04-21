package flaxbeard.thaumicexploration.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAICreeperDummy extends EntityAIBase
{
    /** The creeper that is swelling. */
    EntityCreeper swellingCreeper;

    /**
     * The creeper's attack target. This is used for the changing of the creeper's state.
     */
    EntityLivingBase creeperAttackTarget;

    public EntityAICreeperDummy(EntityCreeper par1EntityCreeper)
    {
        this.swellingCreeper = par1EntityCreeper;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.swellingCreeper.getAttackTarget();
        return this.swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && this.swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.swellingCreeper.getNavigator().clearPathEntity();
        this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.creeperAttackTarget = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
            this.swellingCreeper.setCreeperState(-1);
        
    }
}
