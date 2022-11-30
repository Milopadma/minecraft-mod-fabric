package net.fabricmc.example.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.example.ModMain;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;

@Mixin(value = LightmapTextureManager.class)
public class MixinLightmapTextureManager {

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getGamma()Lnet/minecraft/client/option/SimpleOption;", opcode = Opcodes.INVOKEVIRTUAL), method = "update(F)V")
	// this passes whatever the option is to the method as everytime
	// Lightmaptexturemanager is called, the modified brightness value is passed in
	private SimpleOption<Double> getFieldValue(GameOptions options) {
		ModMain.log.info("Brightness value: " + ModMain.getBrightnessOption().getValue());
		return ModMain.getBrightnessOption();
	}
}