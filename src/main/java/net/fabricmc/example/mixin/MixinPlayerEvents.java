package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.example.ModMain;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class MixinPlayerEvents {
    // this class listens for player events, and then calls the mixin class to
    // modify

    // when the client loads up a world, log to console

}
