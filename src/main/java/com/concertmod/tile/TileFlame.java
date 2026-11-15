package com.concertmod.tile;

import com.concertmod.client.ClientEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

/** Flame machine. Visual only: it does not hurt entities and does not set blocks on fire. */
public class TileFlame extends TileEffect {

    public TileFlame() {
        this.burstMode = true;
    }

    @Override
    protected int burstDuration() {
        return 14;
    }

    @Override
    protected void emit(Vec3d origin, Vec3d dir, boolean burst) {
        ClientEffects.flames(world, origin, dir, burst);
    }

    @Override
    protected SoundEvent shotSound() {
        return SoundEvents.ITEM_FIRECHARGE_USE;
    }

    @Override
    protected SoundEvent streamSound() {
        return SoundEvents.BLOCK_FIRE_AMBIENT;
    }

    @Override
    public int barrelColor() {
        return 0xC03020;
    }
}
