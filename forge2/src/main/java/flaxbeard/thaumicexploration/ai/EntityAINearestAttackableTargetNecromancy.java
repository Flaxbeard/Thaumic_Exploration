package flaxbeard.thaumicexploration.ai;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import flaxbeard.thaumicexploration.data.NecromancyMobProperties;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityOwnable;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTargetSorter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EntityAINearestAttackableTargetNecromancy extends EntityAINearestAttackableTarget
{
    private final Class targetClass;
    private final int targetChance;

    /** Instance of EntityAINearestAttackableTargetSorter. */
    private final EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;

    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    private final IEntitySelector targetEntitySelector;
    private EntityLivingBase targetEntity;

    public EntityAINearestAttackableTargetNecromancy(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
    {
        this(par1EntityCreature, par2Class, par3, par4, false);
    }

    public EntityAINearestAttackableTargetNecromancy(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
    {
        this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector)null);
    }

    public EntityAINearestAttackableTargetNecromancy(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector)
    {
        super(par1EntityCreature, par2Class, par3, par4, par5, par6IEntitySelector);
        this.targetClass = par2Class;
        this.targetChance = par3;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(par1EntityCreature);
        this.setMutexBits(1);
        this.targetEntitySelector = new EntityAINearestAttackableTargetSelectorReplacement(this, par6IEntitySelector);
    }
    
    public boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2)
    {
        return super.isSuitableTarget(par1EntityLivingBase, par2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass, this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
            	System.out.println("checkbox");
            	for (Object item : list) {
            		if(NecromancyMobProperties.get((EntityLiving) item) == null)
            		{
            			System.out.println("there is at least one zombie in this hizzle with no prop");
            			this.targetEntity = (EntityLivingBase) item;
            			this.taskOwner.setAttackTarget(this.targetEntity);
            			NecromancyMobProperties prop = NecromancyMobProperties.get(this.taskOwner);
            			prop.setTarget(this.targetEntity);
            			break;
            		}
            	}
            	return this.targetEntity != null;
            	
            	//return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	System.out.println("motha fukin bass");
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    

}
