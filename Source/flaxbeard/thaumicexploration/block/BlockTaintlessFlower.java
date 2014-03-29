//package flaxbeard.thaumicexploration.block;
//
//import static net.minecraftforge.common.EnumPlantType.Cave;
//import static net.minecraftforge.common.EnumPlantType.Crop;
//import static net.minecraftforge.common.EnumPlantType.Desert;
//import static net.minecraftforge.common.EnumPlantType.Nether;
//import static net.minecraftforge.common.EnumPlantType.Plains;
//import static net.minecraftforge.common.EnumPlantType.Water;
//
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.util.AxisAlignedBB;
//import net.minecraft.world.World;
//import net.minecraftforge.common.EnumPlantType;
//import net.minecraftforge.common.ForgeDirection;
//import net.minecraftforge.common.IPlantable;
//
//public class BlockTaintlessFlower extends Block implements IPlantable
//{
//    protected BlockTaintlessFlower(int par1, Material par2Material)
//    {
//        super(par1, par2Material);
//        this.setTickRandomly(true);
//        float f = 0.2F;
//        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 3.0F, 0.5F + f);
//        this.setCreativeTab(CreativeTabs.tabDecorations);
//    }
//
//    protected BlockTaintlessFlower(int par1)
//    {
//        this(par1, Material.plants);
//    }
//
//    /**
//     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
//     */
//    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
//    {
//        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
//    }
//
//    /**
//     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
//     * blockID passed in. Args: blockID
//     */
//    protected boolean canThisPlantGrowOnThisBlockID(int par1)
//    {
//        return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID;
//    }
//
//    /**
//     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
//     * their own) Args: x, y, z, neighbor blockID
//     */
//    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
//    {
//        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
//        this.checkFlowerChange(par1World, par2, par3, par4);
//    }
//
//    /**
//     * Ticks the block if it's been scheduled
//     */
//    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
//    {
//        this.checkFlowerChange(par1World, par2, par3, par4);
//    }
//
//    protected final void checkFlowerChange(World par1World, int par2, int par3, int par4)
//    {
//        if (!this.canBlockStay(par1World, par2, par3, par4))
//        {
//            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
//            par1World.setBlock(par2, par3, par4, 0, 0, 2);
//        }
//    }
//
//    /**
//     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
//     */
//    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
//    {
//        Block soil = blocksList[par1World.getBlockId(par2, par3 - 1, par4)];
//        return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && 
//                (soil != null && soil.canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this));
//    }
//
//    /**
//     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
//     * cleared to be reused)
//     */
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
//    {
//        return null;
//    }
//
//    /**
//     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
//     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
//     */
//    public boolean isOpaqueCube()
//    {
//        return false;
//    }
//
//    /**
//     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
//     */
//    public boolean renderAsNormalBlock()
//    {
//        return false;
//    }
//
//    /**
//     * The type of render function that is called for this block
//     */
//    public int getRenderType()
//    {
//        return 1;
//    }
//
//    @Override
//    public EnumPlantType getPlantType(World world, int x, int y, int z)
//    {
//        if (blockID == crops.blockID        ) return Crop;
//        if (blockID == deadBush.blockID     ) return Desert;
//        if (blockID == waterlily.blockID    ) return Water;
//        if (blockID == mushroomRed.blockID  ) return Cave;
//        if (blockID == mushroomBrown.blockID) return Cave;
//        if (blockID == netherStalk.blockID  ) return Nether;
//        if (blockID == sapling.blockID      ) return Plains;
//        if (blockID == melonStem.blockID    ) return Crop;
//        if (blockID == pumpkinStem.blockID  ) return Crop;
//        if (blockID == tallGrass.blockID    ) return Plains;
//        return Plains;
//    }
//
//    @Override
//    public int getPlantID(World world, int x, int y, int z)
//    {
//        return blockID;
//    }
//
//    @Override
//    public int getPlantMetadata(World world, int x, int y, int z)
//    {
//        return world.getBlockMetadata(x, y, z);
//    }
//}