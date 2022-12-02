package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.example.ModMain;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(PlayerEntity.class)
public class MixinPlayerEvents {
    // this class listens for player events, and then calls the mixin class to
    // modify

    // when a player joins a world, send a message to the console
    @Inject(at = @At("HEAD"), method = "onSpawn") // ! it did not call!
    public void onSpawn(CallbackInfo info) {
        ModMain.log.info("Player joined world");
    }
}
