package ru.thisistails.discordintegrationapi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.thisistails.discordintegrationapi.Commands.CommandEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Date;
import java.util.EnumSet;

class BotLoader {
 private final DiscordIntegrationAPI main;
 YamlConfiguration config;
 protected static JDA bot;

 protected BotLoader(DiscordIntegrationAPI main) {
  this.main = main;
  config = (YamlConfiguration) main.getConfig();
 }

 protected static TextChannel publicChannel = null;
 protected static TextChannel privateChannel = null;

 private static final java.util.logging.Logger logger = Bukkit.getLogger();

 protected void load() {
        logger.info("Starting loading...");
  try {
   bot = JDABuilder
           .createDefault(config.getString("token"),EnumSet.allOf(GatewayIntent.class))
           .build()
           .awaitReady();
        logger.info("Bot successfully logged!");
   bot.addEventListener(new CommandEvent(main));
   logger.info("Event handler added successfully...");
  } catch (LoginException | InterruptedException e) {
        logger.severe("Bot cant keep up! Look at this first:");
   e.printStackTrace();
   if (config.getBoolean("utils.shutdown-on-error")) main.getServer().shutdown();
   else main.getPluginLoader().disablePlugin(main);
  }




  // Checking for startup messgae
  if (config.getBoolean("utils.on-ready.enabled")) {

   EmbedBuilder embed = new EmbedBuilder();
   embed.setTitle("Активация DIApi");
   embed.setColor(Color.GREEN);
   embed.addField("Версия SDK", Runtime.version().toString(), false);
   embed.addField("Discord API", "JDA", false);
   embed.addField("Версия API", "${project.version}", false);
   embed.setTimestamp(new Date().toInstant());

   if (config.getString("utils.on-ready.channel").equals("all")) {

    if(config.getBoolean("logs.enable-private-logs")) {
     try {
      TextChannel private_log = bot.getTextChannelById(config.getString("logs.private-channel"));
      privateChannel = private_log;
      private_log.sendMessageEmbeds(embed.build()).queue();
     } catch (IllegalArgumentException error) {
      logger.severe("ID cant be null! Try check ur ID.");
      logger.severe("Your private channel ID is: " + config.getString("logs.private-channel"));
     }

    }

    try {
     TextChannel channel = bot.getTextChannelById(config.getString("logs.public-channel"));
     channel.sendMessageEmbeds(embed.build()).queue();
     publicChannel = channel;
    }catch (IllegalArgumentException error) {
        logger.severe("ID cant be null! Try check ur ID.");
        logger.severe("Your public channel ID is: " + config.getString("logs.public-channel"));
    }

   } else {
    try {
     TextChannel channel = bot.getTextChannelById(config.getString("utils.on-ready.channel"));
     channel.sendMessageEmbeds(embed.build()).queue();
    }catch (IllegalArgumentException error) {
        logger.severe("ID cant be null! Try check ur ID.");
        logger.severe("Your channel ID is: " + config.getString("utils.on-ready.channel"));
    }
   }

  }
  logger.fine("Bot successfully started!");
 }

 protected static JDA getJDA() {
  return bot;
 }
}