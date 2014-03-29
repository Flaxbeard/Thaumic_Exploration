package flaxbeard.thaumicexploration.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class NecromancyMobProperties implements IExtendedEntityProperties
{
	
	public static final void register(EntityLiving entity)
	{
		entity.registerExtendedProperties("thaumicExplorationNecromancyProperties", new NecromancyMobProperties());
	}
	
	public static final NecromancyMobProperties get(EntityLiving entity)
	{
		return (NecromancyMobProperties)entity.getExtendedProperties("thaumicExplorationNecromancyProperties");
	}

	private EntityLiving owner;
	private EntityLiving target;
	
	@Override
	public void saveNBTData(NBTTagCompound data)
	{

		
	}

	@Override
	public void loadNBTData(NBTTagCompound data)
	{

	}
	
	public void setTarget(EntityLivingBase targetEntity) {
		this.target = (EntityLiving) targetEntity;
	}
	
	public EntityLiving getTarget() {
		return this.target;
	}

	@Override
	public void init(Entity entity, World world)
	{

		if( entity instanceof EntityLiving )
		{
			owner = (EntityLiving)entity;
		}

	}

}
