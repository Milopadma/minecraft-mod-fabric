package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.gui.screen.TitleScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		ExampleMod.LOGGER.info("This line is printed by an example mod mixin!");
	}
}

// Mixin the LightmapTextureManager class
// @Mixin(value = LightmapTextureManager.class)
// public class MixinLightMap {
// // to set all lightmap values to 1, ergo, full brightness
// @Inject(at = @At("HEAD"), method = "update(II)V", cancellable = true)
// private void update(int skyLight, int blockLight, CallbackInfo info) {
// ModMain.LOGGER.info("LightmapTextureManager.update() was called!");
// // set all lightmap values to 1, ergo, full brightness
// skyLight = 1;
// blockLight = 1;
// info.cancel();
// }
// }