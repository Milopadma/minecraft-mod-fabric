package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.example.ModMain;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.*;

@Mixin(ClientConnection.class)
public class MixinClientConnections {
    @ModifyVariable(at = @At("HEAD"), method = "sendInternal", ordinal = 0)
    // this modifies the packet sent from the client to the server
    public Packet<?> sendInternal(Packet<?> packet) {
        // this is a mixin for the client connection class, overlays over the actual
        // class to add our own functionality to it
        // check if the ANTIFALL option is enabled from ModMain first
        if (ModMain.getANTIFALL()) {
            // if it is enabled, get every packet of instance PlayerMoveC2SPacket.Full OR
            // PlayerMoveC2SPacket.PositionAndOnGround
            if (packet instanceof PlayerActionC2SPacket) {
                // log this into the console 
                ModMain.log.info("PlayerActionC2SPacket packet sent to server");
            }
        }
        return packet;
    }
}
