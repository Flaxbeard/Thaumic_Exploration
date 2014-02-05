package flaxbeard.thaumicexploration.ai;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EntityAILoveOwnerHurtByTarget extends EntityAITarget
{
    EntityLivingBase theDefendingTameable;
    EntityLivingBase theOwnerAttacker;
    private int field_142051_e;

    public EntityAILoveOwnerHurtByTarget(EntityCreature par1EntityTameable)
    {
        super((EntityCreature) par1EntityTameable, false);
        this.theDefendingTameable = par1EntityTameable;
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
            EntityLivingBase entitylivingbase = (EntityLivingBase) this.theDefendingTameable.worldObj.getEntityByID(this.theDefendingTameable.getEntityData().getInteger("loveOwnerID"));

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
            	
                this.theOwnerAttacker = entitylivingbase.getAITarget();
                int i = entitylivingbase.func_142015_aE();
               
                return i != this.field_142051_e && this.isSuitableTarget(this.theOwnerAttacker, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.theOwnerAttacker);
        EntityLivingBase entitylivingbase = (EntityLivingBase) this.theDefendingTameable.worldObj.getEntityByID(this.theDefendingTameable.getEntityData().getInteger("loveOwnerID"));


        if (entitylivingbase != null)
        {
            this.field_142051_e = entitylivingbase.func_142015_aE();
        }

        super.startExecuting();
    }
}
