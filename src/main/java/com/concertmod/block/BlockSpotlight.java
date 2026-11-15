package com.concertmod.block;

import com.concertmod.tile.TileSpotlight;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpotlight extends BlockDevice {
    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.8D, 0.8D);

    public BlockSpotlight() {
        super(Material.IRON);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileSpotlight();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSpotlight && placer != null) {
            ((TileSpotlight) te).yaw = MathHelper.wrapDegrees(placer.rotationYaw);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOX;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
