package flaxbeard.thaumicexploration.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class EntityAINearestAttackableTargetSelectorReplacement implements IEntitySelector
{
    final IEntitySelector field_111103_c;

    final EntityAINearestAttackablePureTarget field_111102_d;

    EntityAINearestAttackableTargetSelectorReplacement(EntityAINearestAttackablePureTarget par1EntityAINearestAttackableTargetNecromancy, IEntitySelector par2IEntitySelector)
    {
        this.field_111102_d = par1EntityAINearestAttackableTargetNecromancy;
        this.field_111103_c = par2IEntitySelector;
    }

    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity par1Entity)
    {	
        return !(par1Entity instanceof EntityLivingBase) ? false : (this.field_111103_c != null && !this.field_111103_c.isEntityApplicable(par1Entity) ? false : this.field_111102_d.isSuitableTarget((EntityLivingBase)par1Entity, false));
    }
}
