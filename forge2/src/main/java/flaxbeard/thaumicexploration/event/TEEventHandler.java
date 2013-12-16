package flaxbeard.thaumicexploration.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import flaxbeard.thaumicexploration.item.EntityItemBrain;

public class TEEventHandler {
	
	@ForgeSubscribe
	void handler(EntityJoinWorldEvent event) {
		if( event.entity instanceof EntityZombie )
        {
                EntityZombie zombie = (EntityZombie)event.entity;
                //if (hasTask(new EntityAINearestAttackableTarget(zombie, EntityItemBrain.class, 0, false), zombie)) {
                	zombie.targetTasks.addTask(0, new EntityAINearestAttackableTarget(zombie, EntityItemBrain.class, 0, false));
                //}
                	System.out.println("works");
        }
	}
	
	boolean hasTask(EntityAIBase task, EntityZombie zombie)
	{
		 for(Object taskEntryObj : zombie.targetTasks.taskEntries) { 
			 EntityAITaskEntry taskEntry = (EntityAITaskEntry)taskEntryObj; 
			 /* Blame the deobfuscation not supporting generics for this one */ 
//			 if( taskEntry.action instanceof task) { 
//				 return true; 
//			 } 
		} return false;
		
	}

}
