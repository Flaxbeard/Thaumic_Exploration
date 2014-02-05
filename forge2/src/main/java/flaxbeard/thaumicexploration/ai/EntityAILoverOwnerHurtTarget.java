package flaxbeard.thaumicexploration.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAILoverOwnerHurtTarget extends EntityAITarget
{
    EntityLivingBase theEntityTameable;
    EntityLivingBase theTarget;
    private int field_142050_e;

    public EntityAILoverOwnerHurtTarget(EntityCreature par1EntityTameable)
    {
        super(par1EntityTameable, false);
        this.theEntityTameable = par1EntityTameable;
        this.setMutexBits(1);
    }
    
    @Override
    protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2)
    {
        if (par1EntityLivingBase == null)
        {
            return false;
        }
        else if (par1EntityLivingBase == this.taskOwner)
        {
            return false;
        }
        else if (!par1EntityLivingBase.isEntityAlive())
        {
            return false;
        }
        return true;
        
//        else if (!this.taskOwner.canAttackClass(par1EntityLivingBase.getClass()))
//        {
//            return false;

    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (false)
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase) this.theEntityTameable.worldObj.getEntityByID(this.theEntityTameable.getEntityData().getInteger("loveOwnerID"));

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
                this.theTarget = entitylivingbase.getLastAttacker();
                int i = entitylivingbase.getLastAttackerTime();
                return i != this.field_142050_e && this.isSuitableTarget(this.theTarget, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.theTarget);
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.theEntityTameable.worldObj.getEntityByID(this.theEntityTameable.getEntityData().getInteger("loveOwnerID"));

        if (entitylivingbase != null)
        {
            this.field_142050_e = entitylivingbase.getLastAttackerTime();
        }

        super.startExecuting();
    }
}
