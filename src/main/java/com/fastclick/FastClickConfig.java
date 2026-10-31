package com.fastclick;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class FastClickConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("fastclick.json");

    public boolean enabled = true;

    public boolean attack = true;
    public int attacksPerTick = 5;

    public boolean place = true;
    public int placesPerTick = 5;

    public static FastClickConfig load() {
        if (Files.exists(FILE)) {
            try (Reader r = Files.newBufferedReader(FILE)) {
                FastClickConfig c = GSON.fromJson(r, FastClickConfig.class);
                if (c != null) {
                    c.clamp();
                    return c;
                }
            } catch (Exception ignored) {
            }
        }
        FastClickConfig c = new FastClickConfig();
        c.save();
        return c;
    }

    public void clamp() {
        attacksPerTick = Math.max(1, Math.min(50, attacksPerTick));
        placesPerTick = Math.max(1, Math.min(50, placesPerTick));
    }

    public void save() {
        clamp();
        try (Writer w = Files.newBufferedWriter(FILE)) {
            GSON.toJson(this, w);
        } catch (IOException ignored) {
        }
    }
}
