package com.concertmod.block;

import com.concertmod.tile.TileConfetti;
import com.concertmod.tile.TileFlame;
import com.concertmod.tile.TileSmoke;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Confetti cannon / smoke machine / flame machine. The barrel is drawn by the TESR and can be aimed. */
public class BlockEffectMachine extends BlockDevice {
    public static final int CONFETTI = 0;
    public static final int SMOKE = 1;
    public static final int FLAME = 2;

    private static final AxisAlignedBB BOX = new AxisAlignedBB(0.15D, 0.0D, 0.15D, 0.85D, 0.75D, 0.85D);

    private final int kind;

    public BlockEffectMachine(int kind) {
        super(Material.IRON);
        this.kind = kind;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        switch (kind) {
            case SMOKE:
                return new TileSmoke();
            case FLAME:
                return new TileFlame();
            default:
                return new TileConfetti();
        }
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
