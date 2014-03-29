//package flaxbeard.thaumicexploration.block;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockFlower;
//import net.minecraft.block.material.Material;
//import net.minecraft.client.renderer.texture.IconRegister;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.Icon;
//import net.minecraft.util.MathHelper;
//import net.minecraft.world.World;
//import net.minecraftforge.common.ForgeDirection;
//import thaumcraft.common.config.Config;
//import thaumcraft.common.config.ConfigBlocks;
//import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
//import cpw.mods.fml.relauncher.Side;
//import cpw.mods.fml.relauncher.SideOnly;
//import flaxbeard.thaumicexploration.ThaumicExploration;
//
//public class BlockTaintBerries extends BlockTaintlessFlower
//{
//    @SideOnly(Side.CLIENT)
//    private Icon[] iconArray;
//
//    public BlockTaintBerries(int par1)
//    {
//        super(par1, Material.plants);
//        this.setTickRandomly(true);
//        float f = 0.5F;
//        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
//        this.setCreativeTab((CreativeTabs)null);
//        this.setHardness(0.0F);
//        this.setStepSound(soundGrassFootstep);
//        this.disableStats();
//    }
//
//    /**
//     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
//     * blockID passed in. Args: blockID
//     */
//    @Override
//    public boolean canBlockStay(World par3World, int par2, int par3, int par4)
//    {
//        Block soil = blocksList[par3World.getBlockId(par2, par3 - 1, par4)];
//       
//        return (par3World.getFullBlockLightValue(par2, par3, par4) >= 8 || par3World.canBlockSeeTheSky(par2, par3, par4)) && 
//                (soil != null && ((soil.blockID == Block.grass.blockID && par3World.getBiomeGenForCoords(par2, par4) == ThaumcraftWorldGenerator.biomeTaint) || (soil.blockID == ConfigBlocks.blockTaint.blockID && par3World.getBlockMetadata(par2, par3-1, par4) == 1)));
//    }
//    
//    @Override
//    protected boolean canThisPlantGrowOnThisBlockID(int par1)
//    {
//        return true;
//    }
//
//    /**
//     * Ticks the block if it's been scheduled
//     */
//    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
//    {
//        super.updateTick(par1World, par2, par3, par4, par5Random);
//
//        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
//        {
//            int l = par1World.getBlockMetadata(par2, par3, par4);
//
//            if (l < 7)
//            {
//                float f = this.getGrowthRate(par1World, par2, par3, par4);
//
//                if (par5Random.nextInt((int)(25.0F / f) + 1) == 0)
//                {
//                    ++l;
//                    par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
//                }
//            }
//        }
//    }
//
//    /**
//     * Apply bonemeal to the crops.
//     */
//    public void fertilize(World par1World, int par2, int par3, int par4)
//    {
//        int l = par1World.getBlockMetadata(par2, par3, par4) + MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
//
//        if (l > 7)
//        {
//            l = 7;
//        }
//
//        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
//    }
//
//    /**
//     * Gets the growth rate for the crop. Setup to encourage rows by halving growth rate if there is diagonals, crops on
//     * different sides that aren't opposing, and by adding growth for every crop next to this one (and for crop below
//     * this one). Args: x, y, z
//     */
//    private float getGrowthRate(World par1World, int par2, int par3, int par4)
//    {
//        float f = 1.0F;
//        int l = par1World.getBlockId(par2, par3, par4 - 1);
//        int i1 = par1World.getBlockId(par2, par3, par4 + 1);
//        int j1 = par1World.getBlockId(par2 - 1, par3, par4);
//        int k1 = par1World.getBlockId(par2 + 1, par3, par4);
//        int l1 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
//        int i2 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
//        int j2 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
//        int k2 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
//        boolean flag = j1 == this.blockID || k1 == this.blockID;
//        boolean flag1 = l == this.blockID || i1 == this.blockID;
//        boolean flag2 = l1 == this.blockID || i2 == this.blockID || j2 == this.blockID || k2 == this.blockID;
//
//        for (int l2 = par2 - 1; l2 <= par2 + 1; ++l2)
//        {
//            for (int i3 = par4 - 1; i3 <= par4 + 1; ++i3)
//            {
//                int j3 = par1World.getBlockId(l2, par3 - 1, i3);
//                float f1 = 0.0F;
//
//                if (blocksList[j3] != null)
//                {
//
//                        f1 = 3.0F;
//                }
//
//                if (l2 != par2 || i3 != par4)
//                {
//                    f1 /= 4.0F;
//                }
//
//                f += f1;
//            }
//        }
//
//        if (flag2 || flag && flag1)
//        {
//            f /= 2.0F;
//        }
//
//        return f;
//    }
//
//    @SideOnly(Side.CLIENT)
//
//    /**
//     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
//     */
//    public Icon getIcon(int par1, int par2)
//    {
//        if (par2 < 7)
//        {
//            if (par2 == 6)
//            {
//                par2 = 5;
//            }
//
//            return this.iconArray[par2 >> 1];
//        }
//        else
//        {
//            return this.iconArray[3];
//        }
//    }
//
//    /**
//     * The type of render function that is called for this block
//     */
//    public int getRenderType()
//    {
//        return 6;
//    }
//
//    /**
//     * Generate a seed ItemStack for this crop.
//     */
//    protected int getSeedItem()
//    {
//        return ThaumicExploration.taintBerry.itemID;
//    }
//
//    /**
//     * Generate a crop produce ItemStack for this crop.
//     */
//    protected int getCropItem()
//    {
//        return ThaumicExploration.taintBerry.itemID;
//    }
//
//    /**
//     * Drops the block items with a specified chance of dropping the specified items
//     */
//    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
//    {
//        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
//    }
//
//    @Override 
//    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
//    {
//        ArrayList<ItemStack> ret = super.getBlockDropped(world, x, y, z, metadata, fortune);
//
//        if (metadata >= 7)
//        {
//            for (int n = 0; n < 3 + fortune; n++)
//            {
//                if (world.rand.nextInt(15) <= metadata)
//                {
//                    ret.add(new ItemStack(this.getSeedItem(), 1, 0));
//                }
//            }
//        }
//
//        return ret;
//    }
//
//    /**
//     * Returns the ID of the items to drop on destruction.
//     */
//    public int idDropped(int par1, Random par2Random, int par3)
//    {
//        return par1 == 7 ? this.getCropItem() : this.getSeedItem();
//    }
//
//    /**
//     * Returns the quantity of items to drop on block destruction.
//     */
//    public int quantityDropped(Random par1Random)
//    {
//        return 1;
//    }
//
//    @SideOnly(Side.CLIENT)
//
//    /**
//     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
//     */
//    public int idPicked(World par1World, int par2, int par3, int par4)
//    {
//        return this.getSeedItem();
//    }
//
//    @SideOnly(Side.CLIENT)
//
//    /**
//     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
//     * is the only chance you get to register icons.
//     */
//
//    public void registerIcons(IconRegister par1IconRegister)
//    {
//        this.iconArray = new Icon[4];
//
//        for (int i = 0; i < this.iconArray.length; ++i)
//        {
//            this.iconArray[i] = par1IconRegister.registerIcon(this.getTextureName() + "_" + i);
//        }
//    }
//}
//
//
//
//
//
//
