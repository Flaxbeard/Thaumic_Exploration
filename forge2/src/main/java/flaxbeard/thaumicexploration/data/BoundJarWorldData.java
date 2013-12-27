package flaxbeard.thaumicexploration.data;

import thaumcraft.api.aspects.Aspect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;


public class BoundJarWorldData extends WorldSavedData {

        private static final String IDENTIFIER = "teBoundChest";
        
        private Aspect myAspect;
        private int aspectAmount;
        private int myColor = 0;
        
        public BoundJarWorldData() {
                super(IDENTIFIER);
        }
        
        public BoundJarWorldData(String identifier) {
                super(identifier);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
        	System.out.println("looking for dem KEEZ");
        	if (nbt.hasKey("myColor")) {
        		myColor = nbt.getInteger("myColor");
        	}
        	if (nbt.hasKey("myAspect")) {
        		myAspect = Aspect.getAspect(nbt.getString("myAspect"));
        	}
        	if (nbt.hasKey("aspectAmount")) {
        		aspectAmount = nbt.getInteger("aspectAmount");
        	}
            
        }

        @Override
        public void writeToNBT(NBTTagCompound nbt) {
            nbt.setInteger("myColor", myColor);
            nbt.setInteger("aspectAmount", aspectAmount);
            nbt.setString("myAspect", myAspect.getTag());
        }
        
        public void updateJarContents(Aspect aspect, int amount) {
        	this.myAspect = aspect;
        	this.aspectAmount = amount;
            markDirty();               
        }
        
        public Aspect getJarAspect() {
    		return this.myAspect;            
        }
        
        public int getJarAmount() {
    		return this.aspectAmount;            
        }
        
        public int getSealColor() {
        	return myColor;
        }
        
        public void setSealColor(int color) {
        	myColor = color;
        }
        
        
        
        
        public static BoundJarWorldData get(World world, String ident, int color) {
        		BoundJarWorldData data = (BoundJarWorldData) world.mapStorage.loadData(BoundJarWorldData.class, ident);
                
                if (data == null) {
                		System.out.println("making new itemdata for " + ident);
                        data = new BoundJarWorldData(ident);
                        data.setSealColor(color);
                        data.markDirty();
                        world.mapStorage.setData(ident, data);
                }
                return data;
        }
}