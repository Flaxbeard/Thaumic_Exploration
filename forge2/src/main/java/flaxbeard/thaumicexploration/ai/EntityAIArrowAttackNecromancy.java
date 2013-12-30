package flaxbeard.thaumicexploration.ai;

import flaxbeard.thaumicexploration.data.NecromancyMobProperties;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttackNecromancy extends EntityAIBase

{
    /** The entity the AI instance has been applied to */
    private final EntityLiving entityHost;

    /**
     * The entity (as a RangedAttackMob) the AI instance has been applied to.
     */
    private final EntityLiving rangedAttackEntityHost;
    private EntityLivingBase attackTarget;

    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime;
    private double entityMoveSpeed;
    private int field_75318_f;
    private int field_96561_g;

    /**
     * The maximum time the AI has to wait before peforming another ranged attack.
     */
    private int maxRangedAttackTime;
    private float field_96562_i;
    private float field_82642_h;

    public EntityAIArrowAttackNecromancy(EntityLiving par1IRangedAttackMob, double par2, int par4, float par5)
    {
        this(par1IRangedAttackMob, par2, par4, par4, par5);
    }

    public EntityAIArrowAttackNecromancy(EntityLiving par1IRangedAttackMob, double par2, int par4, int par5, float par6)
    {

        this.rangedAttackTime = -1;

        if (!(par1IRangedAttackMob instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = par1IRangedAttackMob;
            this.entityHost = (EntityLiving)par1IRangedAttackMob;
            this.entityMoveSpeed = par2;
            this.field_96561_g = par4;
            this.maxRangedAttackTime = par5;
            this.field_96562_i = par6;
            this.field_82642_h = par6 * par6;
            this.setMutexBits(3);
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();
        NecromancyMobProperties prop = NecromancyMobProperties.get(this.entityHost);
        entitylivingbase = prop.getTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.attackTarget = null;
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);
        boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (flag)
        {
            ++this.field_75318_f;
        }
        else
        {
            this.field_75318_f = 0;
        }

        if (d0 <= (double)this.field_82642_h && this.field_75318_f >= 20)
        {
            this.entityHost.getNavigator().clearPathEntity();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        float f;

        if (--this.rangedAttackTime == 0)
        {
            if (d0 > (double)this.field_82642_h || !flag)
            {
                return;
            }

            f = MathHelper.sqrt_double(d0) / this.field_96562_i;
            float f1 = f;

            if (f < 0.1F)
            {
                f1 = 0.1F;
            }

            if (f1 > 1.0F)
            {
                f1 = 1.0F;
            }
            NecromancyMobProperties prop = NecromancyMobProperties.get(this.entityHost);
            EntityLiving entitylivingbase = prop.getTarget();
            if (this.rangedAttackEntityHost instanceof IRangedAttackMob) {
            	((IRangedAttackMob) this.rangedAttackEntityHost).attackEntityWithRangedAttack(entitylivingbase, f1);
            }
            else
            {
            	this.attackEntityWithRangedAttack(entitylivingbase, f1);
            }
            this.rangedAttackTime = MathHelper.floor_float(f * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
        }
        else if (this.rangedAttackTime < 0)
        {
            f = MathHelper.sqrt_double(d0) / this.field_96562_i;
            this.rangedAttackTime = MathHelper.floor_float(f * (float)(this.maxRangedAttackTime - this.field_96561_g) + (float)this.field_96561_g);
        }
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
    {
        EntityArrow entityarrow = new EntityArrow(this.rangedAttackEntityHost.worldObj, this.rangedAttackEntityHost, par1EntityLivingBase, 1.6F, (float)(14 - this.rangedAttackEntityHost.worldObj.difficultySetting * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.rangedAttackEntityHost.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.rangedAttackEntityHost.getHeldItem());
        entityarrow.setDamage((double)(par2 * 2.0F) + this.rangedAttackEntityHost.getRNG().nextGaussian() * 0.25D + (double)((float)this.rangedAttackEntityHost.worldObj.difficultySetting * 0.11F));

        if (i > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.rangedAttackEntityHost.getHeldItem()) > 0 || (this.rangedAttackEntityHost instanceof EntitySkeleton && ((EntitySkeleton)this.rangedAttackEntityHost).getSkeletonType() == 1))
        {
            entityarrow.setFire(100);
        }

        this.rangedAttackEntityHost.playSound("random.bow", 1.0F, 1.0F / (this.rangedAttackEntityHost.getRNG().nextFloat() * 0.4F + 0.8F));
        this.rangedAttackEntityHost.worldObj.spawnEntityInWorld(entityarrow);
    }
}
