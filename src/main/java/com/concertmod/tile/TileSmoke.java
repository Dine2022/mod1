package com.concertmod.tile;

import com.concertmod.client.ClientEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;

/** Smoke / fog machine. Stream by default; the color tints the fog. */
public class TileSmoke extends TileEffect {

    public TileSmoke() {
        this.burstMode = false;
    }

    @Override
    protected int burstDuration() {
        return 20;
    }

    @Override
    protected void emit(Vec3d origin, Vec3d dir, boolean burst) {
        int rgb = displayColor((float) (world.getTotalWorldTime() % 1000000L));
        if (!rainbow && (rgb & 0xFFFFFF) == 0xFFFFFF) {
            rgb = 0xE6E6EE;
        }
        ClientEffects.fog(world, origin, dir, burst, rgb);
    }

    @Override
    protected SoundEvent shotSound() {
        return SoundEvents.BLOCK_FIRE_EXTINGUISH;
    }

    @Override
    protected SoundEvent streamSound() {
        return null;
    }

    @Override
    public int barrelColor() {
        return 0x7A7F8A;
    }
}
