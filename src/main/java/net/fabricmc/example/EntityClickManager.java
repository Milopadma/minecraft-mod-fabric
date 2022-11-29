package net.fabricmc.example;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class EntityClickManager implements AttackEntityCallback {

    private boolean criticals = true;

    // this is called whenever a player attacks an entity
    @Override
    public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, net.minecraft.entity.Entity entity,
            @Nullable EntityHitResult hitResult) {
        if (criticals) {
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    playerEntity.getX(), playerEntity.getY() + 0.001, playerEntity.getZ(), false));
            MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(
                    playerEntity.getX(), playerEntity.getY() + 0.0001, playerEntity.getZ(), false));
        }

        return ActionResult.PASS;
    }
}
