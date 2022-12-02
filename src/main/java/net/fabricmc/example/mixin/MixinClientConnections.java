package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.example.ModMain;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.Vec3d;

@Mixin(ClientConnection.class)
public class MixinClientConnections {
    // class fields
    @ModifyVariable(at = @At("HEAD"), method = "sendInternal", ordinal = 0)
    // this modifies the packet sent from the client to the server
    public Packet<?> sendInternal(Packet<?> packet) {
        // this is a mixin for the client connection class, overlays over the actual
        // class to add our own functionality to it
        // check if the ANTIFALL option is enabled from ModMain first
        if (ModMain.getANTIFALL()) {
            // if it is enabled, get every packet of instance PlayerMoveC2SPacket.Full OR
            // PlayerMoveC2SPacket.PositionAndOnGround
            if (packet instanceof PlayerMoveC2SPacket.Full
                    || packet instanceof PlayerMoveC2SPacket.PositionAndOnGround) {
                // log this into the console
                ModMain.log.info("PlayerMoveC2SPacket packet sent to server");
                // cant really use deltas here, so just find the velocity.
                // this is the yvelocity of the player
                Double yVelocity = ModMain.player.getVelocity().y;
                // if the yvelocity is less than -0.7, then the player is falling
                if (yVelocity < -0.7) {
                    // set a new velocity to cancel the fall
                    ModMain.player.setVelocity(new Vec3d(0, 0, 0));
                    // log this into the console
                    ModMain.log.info("PlayerMoveC2SPacket packet cancelled");
                }
            }
        }
        return packet;
    }
}
