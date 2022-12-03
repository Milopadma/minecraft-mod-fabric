package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.example.ModMain;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.item.ItemGroup;

@Mixin(value = KeyboardInput.class)
public class MixinKeyboard {
    // overlays over keyboard inputs.
    @Inject(at = @At("RETURN"), method = "tick(ZF)V")
    private void tick(boolean slowDown, CallbackInfo info) {
        // for the scaffold functionality
        if (ModMain.SCAFFOLD) {
            // check if the player is on the ground, there are AIR blocks below them, and if
            // they are holding blocks of the BUILDING_BLOCKS group
            if (ModMain.player.isOnGround()
                    && ModMain.clientWorld.getBlockState(ModMain.player.getBlockPos().down()).isAir()
                    && ModMain.player.getMainHandStack().getItem().getGroup().equals(ItemGroup.BUILDING_BLOCKS)) {
            }
        }
    }
}
