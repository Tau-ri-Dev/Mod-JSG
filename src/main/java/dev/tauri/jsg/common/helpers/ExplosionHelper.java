package dev.tauri.jsg.common.helpers;

import dev.tauri.jsg.JSG;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = JSG.MOD_ID)
public class ExplosionHelper {
    public static final int CRATER_BLOCKS_PER_TICK = 16000;
    private static final List<Crater> CRATERS = new ArrayList<>();

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (!CRATERS.isEmpty()) {
            var level = event.level;
            if (level instanceof ServerLevel serverLevel) {
                int perTick = CRATER_BLOCKS_PER_TICK;
                var it = CRATERS.iterator();
                while (it.hasNext() && perTick > 0) {
                    var crater = it.next();
                    if (crater.level == serverLevel) {
                        int gone = crater.dig(perTick);
                        if (gone < 0) {
                            it.remove();
                        } else {
                            perTick -= Math.max(1, gone);
                        }
                    }
                }
            }
        }
    }

    public static class Crater {
        public final ServerLevel level;
        public final Vec3 centre;
        public final double radius;
        public final long seed;
        public final long[] columns;
        public final BlockStateProvider scorchedBlocksProvider;
        public int next;

        public Crater(ServerLevel level, Vec3 explosionCenter, Vec3 explosionDirection, double radius, long seed, BlockStateProvider scorchedBlocksProvider) {
            this.level = level;
            this.centre = explosionCenter.add(explosionDirection.scale(radius * 0.3));
            this.radius = radius;
            this.seed = seed;
            this.scorchedBlocksProvider = scorchedBlocksProvider;
            int r = (int) Math.ceil(radius * 1.15);
            List<long[]> list = new ArrayList<>();

            for (int dx = -r; dx <= r; ++dx) {
                for (int dz = -r; dz <= r; ++dz) {
                    long d2 = (long) dx * (long) dx + (long) dz * (long) dz;
                    if (d2 <= (long) r * (long) r) {
                        list.add(new long[]{d2, (long) dx, (long) dz});
                    }
                }
            }

            list.sort(Comparator.comparingLong(a -> a[0]));
            this.columns = new long[list.size()];

            for (int i = 0; i < this.columns.length; ++i) {
                this.columns[i] = ((long[]) list.get(i))[1] << 32 | ((long[]) list.get(i))[2] & 0xFFFFFFFFL;
            }

        }

        public void create() {
            synchronized (CRATERS) {
                CRATERS.add(this);
            }
        }

        public double edge(double nx, double ny, double nz) {
            double n = Math.sin(nx * 5.1 + (double) (this.seed % 7L)) * Math.cos(nz * 4.3 + ny * 3.7) + (double) 0.5F * Math.sin((nx + nz) * 9.7 + (double) ((this.seed >> 3) % 5L));
            return this.radius * ((double) 1.0F + 0.08 * n);
        }

        public int dig(int budget) {
            int gone = 0;
            int looked = 0;
            RandomSource random = this.level.getRandom();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            int top = this.level.getMaxBuildHeight() - 1;
            int bottom = this.level.getMinBuildHeight();
            int reachUp = (int) Math.ceil(this.radius * 1.1);
            int reachDown = (int) Math.ceil(this.radius * 0.55 * 1.1);

            while (this.next < this.columns.length && gone + looked / 8 < budget) {
                int dx = (int) (this.columns[this.next] >> 32);
                int dz = (int) this.columns[this.next];
                ++this.next;
                int x = Mth.floor(this.centre.x) + dx;
                int z = Mth.floor(this.centre.z) + dz;
                if (this.level.hasChunk(x >> 4, z >> 4) && this.level.getWorldBorder().isWithinBounds(x, z)) {
                    int floor = Integer.MIN_VALUE;

                    for (int y = Math.min(top, Mth.floor(this.centre.y) + reachUp); y >= Math.max(bottom, Mth.floor(this.centre.y) - reachDown); --y) {
                        ++looked;
                        if (this.within(x, y, z)) {
                            pos.set(x, y, z);
                            BlockState state = this.level.getBlockState(pos);
                            if (state.isAir()) {
                                floor = y;
                            } else if (!(state.getDestroySpeed(this.level, pos) < 0.0F)) {
                                this.level.setBlock(pos, Blocks.AIR.defaultBlockState(), 50);
                                this.shell(pos);
                                ++gone;
                                floor = y;
                            }
                        }
                    }

                    if (floor != Integer.MIN_VALUE && floor - 1 >= bottom) {
                        pos.set(x, floor - 1, z);
                        BlockState under = this.level.getBlockState(pos);
                        if (!under.isAir() && under.getDestroySpeed(this.level, pos) >= 0.0F && under.isSolidRender(this.level, pos) && !under.hasBlockEntity() && random.nextFloat() < 0.5F) {
                            this.level.setBlock(pos, this.scorch(random, pos), 3);
                        }
                    }
                }
            }

            return this.next >= this.columns.length ? -1 : gone + looked / 8;
        }

        public BlockState scorch(RandomSource random, BlockPos pos) {
            return scorchedBlocksProvider.getState(random, pos);
        }

        public boolean within(int x, int y, int z) {
            double ox = (double) x + (double) 0.5F - this.centre.x;
            double oy = (double) y + (double) 0.5F - this.centre.y;
            double oz = (double) z + (double) 0.5F - this.centre.z;
            double sy = oy < (double) 0.0F ? oy / 0.55 : oy;
            double length = Math.sqrt(ox * ox + sy * sy + oz * oz);
            return length <= (length < 1.0E-6 ? this.radius : this.edge(ox / length, sy / length, oz / length));
        }

        public void shell(BlockPos at) {
            BlockState air = Blocks.AIR.defaultBlockState();

            for (Direction side : Direction.values()) {
                BlockPos beside = at.relative(side);
                if (this.level.isLoaded(beside)) {
                    BlockState was = this.level.getBlockState(beside);
                    if (!was.isAir() && (!this.within(beside.getX(), beside.getY(), beside.getZ()) || !(was.getDestroySpeed(this.level, beside) >= 0.0F))) {
                        this.level.neighborChanged(beside, Blocks.AIR, at);
                        BlockState shaped = was.updateShape(side.getOpposite(), air, this.level, beside, at);
                        Block.updateOrDestroy(was, shaped, this.level, beside, 2, 511);
                    }
                }
            }
        }
    }
}
