package flaxbeard.thaumicexploration.data;

import thaumcraft.api.aspects.Aspect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;


public class TXWorldDataInfoWorldData extends WorldSavedData {

        private static final String IDENTIFIER = "teWorldData";
        
        private int nextBoundChestID = 1;
        private int nextBoundJarID = 1;
        
        public TXWorldDataInfoWorldData() {
                super(IDENTIFIER);
        }
        
        public TXWorldDataInfoWorldData(String identifier) {
                super(identifier);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
        		nextBoundChestID = nbt.getInteger("nextChestID");
        		if (nbt.hasKey("nextJarID")) {
        			nextBoundJarID = nbt.getInteger("nextJarID");
        		}
        }

        @Override
        public void writeToNBT(NBTTagCompound nbt) {
                nbt.setInteger("nextChestID", nextBoundChestID);
            	
                nbt.setInteger("nextJarID", nextBoundJarID);
        }
        
        public int getNextBoundChestID() {
                markDirty();
                return nextBoundChestID++;
        }
        
        public int getNextBoundJarID() {
            markDirty();
            return nextBoundJarID++;
    }
        
        public static TXWorldDataInfoWorldData get(World world) {
                TXWorldDataInfoWorldData data = (TXWorldDataInfoWorldData)world.loadItemData(TXWorldDataInfoWorldData.class, IDENTIFIER);
                if (data == null) {
                        data = new TXWorldDataInfoWorldData();
                        world.setItemData(IDENTIFIER, data);
                }
                return data;
        }
}