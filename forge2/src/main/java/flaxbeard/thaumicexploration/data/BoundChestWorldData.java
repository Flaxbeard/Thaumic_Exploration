package flaxbeard.thaumicexploration.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;


public class BoundChestWorldData extends WorldSavedData {

        private static final String IDENTIFIER = "teBoundChest";
        
        private ItemStack[] chestContents =new ItemStack[36];
        private int myColor = 0;
        
        public BoundChestWorldData() {
                super(IDENTIFIER);
        }
        
        public BoundChestWorldData(String identifier) {
                super(identifier);
        }

        @Override
        public void readFromNBT(NBTTagCompound nbt) {
        	System.out.println("looking for dem KEEZ");
        	if (nbt.hasKey("myColor")) {
        		myColor = nbt.getInteger("myColor");
        	}
        	NBTTagList nbttaglist = nbt.getTagList("Items");
            this.chestContents = new ItemStack[36];

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 255;

                if (j >= 0 && j < this.chestContents.length)
                {
                    this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                }
            }
        }

        @Override
        public void writeToNBT(NBTTagCompound nbt) {
        	NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.chestContents.length; ++i)
            {
                if (this.chestContents[i] != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte)i);
                    this.chestContents[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }

            nbt.setTag("Items", nbttaglist);
            nbt.setInteger("myColor", myColor);
        }
        
        public void updateChestContents(ItemStack[] chestItems) {
        	this.chestContents = chestItems;
            markDirty();               
        }
        
        public ItemStack[] getChestContents() {
    		return this.chestContents;             
        }
        
        public int getSealColor() {
        	return myColor;
        }
        
        public void setSealColor(int color) {
        	myColor = color;
        }
        
        
        
        
        public static BoundChestWorldData get(World world, String ident, int color) {
        		BoundChestWorldData data = (BoundChestWorldData) world.mapStorage.loadData(BoundChestWorldData.class, ident);
                
                if (data == null) {
                		System.out.println("making new itemdata for " + ident);
                        data = new BoundChestWorldData(ident);
                        data.setSealColor(color);
                        data.markDirty();
                        world.mapStorage.setData(ident, data);
                }
                return data;
        }
}