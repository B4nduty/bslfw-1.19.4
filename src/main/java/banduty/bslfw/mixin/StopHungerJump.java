package banduty.bslfw.mixin;

import banduty.bslfw.BsLFW;
import banduty.bslfw.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InGameHud.class)
public class StopHungerJump {
    @Shadow private int scaledHeight;

    @ModifyArg(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), index = 2)
    private int bslfw$limitFood(int z) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return z;
        boolean shouldLimitHunger = ((IEntityDataSaver) player).bslfw$isLimitedHunger();
        boolean shouldLimitReinforced = ((IEntityDataSaver) player).bslfw$isLimitedReinforced();
        if (BsLFW.CONFIG.common.modifyHungerLimitHeartOfHunger || shouldLimitHunger || shouldLimitReinforced) {
            return this.scaledHeight-39;
        }
        return z;
    }
}
