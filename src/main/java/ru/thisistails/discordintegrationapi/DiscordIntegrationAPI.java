package ru.thisistails.discordintegrationapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DiscordIntegrationAPI extends JavaPlugin {

    BotLoader loader = null;

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("Checking files for missing and create missing");

        File config = new File(getDataFolder() + "/config.yml");
        if(!config.exists()) {
            config.mkdir();
            //saveResource(getDataFolder() + "/config.yml", false);
            saveDefaultConfig();
            getLogger().info("Config created");
        }

        getLogger().info("Loading bot...");
        loader = new BotLoader(this);
        loader.load();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutdown bot...");
        try {
            loader.bot.shutdown();
        } catch(Exception e) {e.printStackTrace();};
    }
}
