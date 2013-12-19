package flaxbeard.thaumicexploration.ai;

import java.util.Collections;
import net.minecraft.command.IEntitySelector;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;

import net.minecraft.entity.ai.attributes.AttributeInstance;

public class EntityAINearestEntity extends EntityAIBase
{
    private final Class targetClass;
    private final int targetChance;
    
    /** The entity that this task belongs to */
    protected EntityCreature taskOwner;

    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private Entity targetEntity;

    public EntityAINearestEntity(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
    {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }

    public EntityAINearestEntity(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
    {
    	this.taskOwner = par1EntityCreature;
        this.targetClass = par2Class;
        this.targetChance = par3;
    }
    
    protected double getTargetDistance()
    {
        AttributeInstance attributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
        return attributeinstance == null ? 16.0D : attributeinstance.getAttributeValue();
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    
    
    public boolean continueExecuting()
    {	
    	if (this.taskOwner.getNavigator().noPath()) {
    		this.targetEntity.attackEntityFrom(null, 5.0F);
    		return false;
    	}
    	double d0 = this.getTargetDistance();
    	List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0));
    	if (!list.isEmpty()) {
    		return (this.targetEntity == (Entity) list.get(0));
    	}
    	else
    	{
    		return false;
    	}
    	
    }
    
    public boolean shouldExecute()
    {
        double d0 = this.getTargetDistance();
        List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0));

        if (list.isEmpty())
        {
            return false;
        }
        else
        {
            this.targetEntity = (Entity) list.get(0);

            double speed = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
	        Entity pos = this.targetEntity;
	       	this.taskOwner.getNavigator().tryMoveToXYZ((double)pos.posX + 0.5, (double)pos.posY, (double)pos.posZ + 0.5, speed);

            this.taskOwner.setAttackTarget(null);
           	return true;

        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        //this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
