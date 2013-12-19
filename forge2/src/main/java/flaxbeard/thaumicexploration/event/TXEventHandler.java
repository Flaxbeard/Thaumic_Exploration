package flaxbeard.thaumicexploration.event;

import flaxbeard.thaumicexploration.ai.EntityAINearestEntity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class TXEventHandler {
	public TXEventHandler() {
		System.out.println("TEST123");
	}
	
	@ForgeSubscribe
	public void handler(EntityJoinWorldEvent event) {
		if( event.entity instanceof EntityZombie )
        {
                EntityZombie zombie = (EntityZombie)event.entity;
                //if (hasTask(new EntityAINearestAttackableTarget(zombie, EntityItemBrain.class, 0, false), zombie)) {
                	//zombie.targetTasks.addTask(1, new EntityAINearestAttackableTarget(zombie, EntityItem.class, 0, false));
                	//zombie.tasks.addTask(3, new EntityAIAttackOnCollide(zombie, EntityItem.class, 1.0D, true));
                	zombie.tasks.addTask(0, new EntityAINearestEntity(zombie, EntityItem.class, 0, true));
                	//}
                	
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
