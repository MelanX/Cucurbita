package de.melanx.cucurbita.util;

import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public final class VanillaPacketDispatcher {
    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        if (tile.getWorld() instanceof ServerWorld) {
            SUpdateTileEntityPacket packet = tile.getUpdatePacket();
            if (packet != null) {
                BlockPos pos = tile.getPos();
                ((ServerChunkProvider) tile.getWorld().getChunkProvider()).chunkManager
                        .getTrackingPlayers(new ChunkPos(pos), false)
                        .forEach(e -> e.connection.sendPacket(packet));
            }
        }
    }
}