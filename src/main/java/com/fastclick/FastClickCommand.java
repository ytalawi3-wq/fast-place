package com.fastclick;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class FastClickCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        FastClickConfig cfg = FastClickMod.CONFIG;

        dispatcher.register(ClientCommandManager.literal("fastclick")
                .executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.literal(
                            "FastClick: " + (cfg.enabled ? "ON" : "OFF")
                                    + " | attack " + (cfg.attack ? "ON" : "OFF") + " x" + cfg.attacksPerTick
                                    + " | place " + (cfg.place ? "ON" : "OFF") + " x" + cfg.placesPerTick
                                    + "  (/fastclick toggle | attack <1-50> | place <1-50> | attack on/off | place on/off)"));
                    return 1;
                })
                .then(ClientCommandManager.literal("toggle").executes(ctx -> {
                    cfg.enabled = !cfg.enabled;
                    cfg.save();
                    ctx.getSource().sendFeedback(Text.literal("FastClick: " + (cfg.enabled ? "ON" : "OFF")));
                    return 1;
                }))
                .then(ClientCommandManager.literal("attack")
                        .then(ClientCommandManager.argument("perTick", IntegerArgumentType.integer(1, 50))
                                .executes(ctx -> {
                                    cfg.attacksPerTick = IntegerArgumentType.getInteger(ctx, "perTick");
                                    cfg.save();
                                    ctx.getSource().sendFeedback(Text.literal("Attacks per tick: " + cfg.attacksPerTick));
                                    return 1;
                                }))
                        .then(ClientCommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    cfg.attack = BoolArgumentType.getBool(ctx, "enabled");
                                    cfg.save();
                                    ctx.getSource().sendFeedback(Text.literal("Fast attack: " + (cfg.attack ? "ON" : "OFF")));
                                    return 1;
                                })))
                .then(ClientCommandManager.literal("place")
                        .then(ClientCommandManager.argument("perTick", IntegerArgumentType.integer(1, 50))
                                .executes(ctx -> {
                                    cfg.placesPerTick = IntegerArgumentType.getInteger(ctx, "perTick");
                                    cfg.save();
                                    ctx.getSource().sendFeedback(Text.literal("Places per tick: " + cfg.placesPerTick));
                                    return 1;
                                }))
                        .then(ClientCommandManager.argument("enabled", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    cfg.place = BoolArgumentType.getBool(ctx, "enabled");
                                    cfg.save();
                                    ctx.getSource().sendFeedback(Text.literal("Fast place: " + (cfg.place ? "ON" : "OFF")));
                                    return 1;
                                }))));
    }
}
