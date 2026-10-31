package com.fastclick;

import com.fastclick.mixin.MinecraftClientAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.HitResult;

public class FastClickMod implements ClientModInitializer {

    public static final FastClickConfig CONFIG = FastClickConfig.load();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(FastClickMod::onTick);
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                FastClickCommand.register(dispatcher));
    }

    private static void onTick(MinecraftClient mc) {
        if (!CONFIG.enabled) return;
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;
        if (mc.currentScreen != null || !mc.mouse.isCursorLocked()) return;

        MinecraftClientAccessor acc = (MinecraftClientAccessor) mc;

        if (CONFIG.attack && mc.options.attackKey.isPressed()) {
            for (int i = 1; i < CONFIG.attacksPerTick; i++) {
                HitResult hit = mc.crosshairTarget;
                if (hit == null || hit.getType() != HitResult.Type.ENTITY) break;
                acc.fastclick$doAttack();
            }
        }

        if (CONFIG.place && mc.options.useKey.isPressed() && isPlaceable(mc.player)) {
            for (int i = 0; i < CONFIG.placesPerTick; i++) {
                acc.fastclick$setItemUseCooldown(0);
                mc.gameRenderer.updateCrosshairTarget(1.0F);
                acc.fastclick$doItemUse();
            }
        }
    }

    private static boolean isPlaceable(PlayerEntity player) {
        return isPlaceable(player.getMainHandStack()) || isPlaceable(player.getOffHandStack());
    }

    private static boolean isPlaceable(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() instanceof BlockItem || stack.isOf(Items.END_CRYSTAL));
    }
}
