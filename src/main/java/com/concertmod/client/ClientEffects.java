package com.concertmod.client;

import com.concertmod.ConcertMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/** Client-only particle spawning for the effect machines. */
@SideOnly(Side.CLIENT)
public final class ClientEffects {
    private static final Random R = new Random();
    private static final double MAX_DIST = 96.0D;

    private ClientEffects() {
    }

    private static boolean tooFar(Vec3d o) {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.player == null || mc.player.getDistanceSq(o.x, o.y, o.z) > MAX_DIST * MAX_DIST;
    }

    private static TextureAtlasSprite sprite(String name) {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(ConcertMod.MODID + ":blocks/" + name);
    }

    private static double rnd() {
        return R.nextDouble() - 0.5D;
    }

    public static void confetti(World world, Vec3d o, Vec3d dir, boolean burst, int color, boolean multi) {
        if (tooFar(o)) {
            return;
        }
        TextureAtlasSprite sp = sprite("confetti");
        int n = burst ? 110 : 4;
        double base = burst ? 0.95D : 0.6D;
        double spread = burst ? 0.5D : 0.3D;
        for (int i = 0; i < n; i++) {
            double v = base * (0.55D + R.nextDouble() * 0.7D);
            double vx = (dir.x + rnd() * spread) * v;
            double vy = (dir.y + rnd() * spread) * v;
            double vz = (dir.z + rnd() * spread) * v;
            float r;
            float g;
            float b;
            if (multi) {
                int c = MathHelper.hsvToRGB(R.nextFloat(), 0.85F, 1.0F);
                r = ((c >> 16) & 255) / 255.0F;
                g = ((c >> 8) & 255) / 255.0F;
                b = (c & 255) / 255.0F;
            } else {
                float k = 0.75F + R.nextFloat() * 0.25F;
                r = ((color >> 16) & 255) / 255.0F * k;
                g = ((color >> 8) & 255) / 255.0F * k;
                b = (color & 255) / 255.0F * k;
            }
            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleStage(world, o.x, o.y, o.z, vx, vy, vz, false, sp, r, g, b));
        }
    }

    public static void fog(World world, Vec3d o, Vec3d dir, boolean burst, int rgb) {
        if (tooFar(o)) {
            return;
        }
        TextureAtlasSprite sp = sprite("fog");
        float r = ((rgb >> 16) & 255) / 255.0F;
        float g = ((rgb >> 8) & 255) / 255.0F;
        float b = (rgb & 255) / 255.0F;
        int n = burst ? 3 : 2;
        for (int i = 0; i < n; i++) {
            double v = (burst ? 0.28D : 0.16D) * (0.6D + R.nextDouble() * 0.8D);
            double vx = (dir.x + rnd() * 0.35D) * v;
            double vy = (dir.y + rnd() * 0.35D) * v;
            double vz = (dir.z + rnd() * 0.35D) * v;
            float shade = 0.9F + R.nextFloat() * 0.1F;
            Minecraft.getMinecraft().effectRenderer.addEffect(
                    new ParticleStage(world, o.x + rnd() * 0.2D, o.y + rnd() * 0.2D, o.z + rnd() * 0.2D, vx, vy, vz, true, sp, r * shade, g * shade, b * shade));
        }
    }

    public static void flames(World world, Vec3d o, Vec3d dir, boolean burst) {
        if (tooFar(o)) {
            return;
        }
        int n = burst ? 7 : 4;
        for (int i = 0; i < n; i++) {
            double v = 0.35D + R.nextDouble() * 0.25D;
            double vx = (dir.x + rnd() * 0.12D) * v;
            double vy = (dir.y + rnd() * 0.12D) * v;
            double vz = (dir.z + rnd() * 0.12D) * v;
            world.spawnParticle(EnumParticleTypes.FLAME, o.x, o.y, o.z, vx, vy, vz);
        }
        if (R.nextInt(3) == 0) {
            world.spawnParticle(EnumParticleTypes.LAVA, o.x + dir.x * 2.0D, o.y + dir.y * 2.0D, o.z + dir.z * 2.0D, 0.0D, 0.0D, 0.0D);
        }
        if (R.nextInt(2) == 0) {
            world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, o.x + dir.x * 4.0D, o.y + dir.y * 4.0D, o.z + dir.z * 4.0D, 0.0D, 0.03D, 0.0D);
        }
    }
}
