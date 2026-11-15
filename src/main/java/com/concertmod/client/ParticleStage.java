package com.concertmod.client;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/** Confetti flake or fog puff. Uses a sprite from the block atlas (registered in ClientEvents), always full-bright. */
@SideOnly(Side.CLIENT)
public class ParticleStage extends Particle {
    private static final Random R = new Random();

    private final boolean fog;
    private final float baseScale;
    private final float baseAlpha;
    private final float spin;

    public ParticleStage(World world, double x, double y, double z, double vx, double vy, double vz,
                         boolean fog, TextureAtlasSprite sprite, float r, float g, float b) {
        super(world, x, y, z);
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.fog = fog;
        setParticleTexture(sprite);
        setRBGColorF(r, g, b);
        if (fog) {
            this.particleMaxAge = 70 + R.nextInt(50);
            this.particleScale = 8.0F + R.nextFloat() * 6.0F;
            this.particleGravity = -0.02F; // slowly rises
            this.baseAlpha = 0.45F;
            this.spin = (R.nextFloat() - 0.5F) * 0.04F;
        } else {
            this.particleMaxAge = 70 + R.nextInt(60);
            this.particleScale = 0.9F + R.nextFloat() * 0.4F;
            this.particleGravity = 0.5F;
            this.baseAlpha = 1.0F;
            this.spin = (R.nextFloat() - 0.5F) * 0.6F;
        }
        this.baseScale = this.particleScale;
        this.particleAngle = R.nextFloat() * 6.2831855F;
        this.prevParticleAngle = this.particleAngle;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float life = (float) this.particleAge / (float) this.particleMaxAge;
        this.prevParticleAngle = this.particleAngle;
        this.particleAngle += this.spin;
        if (fog) {
            this.particleScale = baseScale * (1.0F + life * 0.8F);
            float fadeIn = Math.min(1.0F, this.particleAge / 6.0F);
            setAlphaF(baseAlpha * (1.0F - life) * fadeIn);
        } else {
            this.motionX += (R.nextFloat() - 0.5F) * 0.012F;
            this.motionY += (R.nextFloat() - 0.5F) * 0.006F;
            this.motionZ += (R.nextFloat() - 0.5F) * 0.012F;
            if (this.onGround) {
                this.particleAge += 4; // flakes that landed disappear quickly
            }
            if (life > 0.85F) {
                setAlphaF((1.0F - life) / 0.15F);
            }
        }
    }

    @Override
    public int getBrightnessForRender(float partialTick) {
        return 15728880; // full bright
    }

    @Override
    public int getFXLayer() {
        return 1; // block atlas
    }
}
