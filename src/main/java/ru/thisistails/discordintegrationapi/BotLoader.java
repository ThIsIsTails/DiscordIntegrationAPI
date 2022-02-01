package ru.thisistails.discordintegrationapi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.thisistails.discordintegrationapi.Commands.CommandEvent;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Date;
import java.util.EnumSet;
import java.util.Objects;

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

 protected void load() {
  try {
   bot = JDABuilder
           .createDefault(config.getString("token"), EnumSet.allOf(GatewayIntent.class))
           .build()
           .awaitReady();
   bot.addEventListener(new CommandEvent(main));
  } catch (LoginException | InterruptedException e) {
   e.printStackTrace();
   if (config.getBoolean("utils.shutdown-on-error"))
    main.getServer().shutdown();
   else main.getPluginLoader().disablePlugin(main);
  }




  // Checking for startup messgae
  if (config.getBoolean("utils.on-ready.enabled")) {

   EmbedBuilder embed = new EmbedBuilder();
   embed.setTitle("Enabled DIA 1.18.1");
   embed.setColor(Color.GREEN);
   embed.addField("SDK Version", Runtime.version().toString(), false);
   embed.addField("Discord sdk", "JDA", false);
   embed.addField("API Version", "${project.version}", false);
   embed.setTimestamp(new Date().toInstant());

   if (config.getString("utils.on-ready.channel").equals("all")) {

    if(config.getBoolean("utils.on-ready.enable-private-logs")) {
     try {
      TextChannel private_log = bot.getTextChannelById(config.getString("utils.on-ready.private-channel"));
      privateChannel = private_log;
      private_log.sendMessageEmbeds(embed.build()).queue();
     } catch (Exception error) {
      error.printStackTrace();
     }

    }

    try {
     TextChannel channel = bot.getTextChannelById(config.getString("utils.on-ready.public-channel"));
     channel.sendMessageEmbeds(embed.build()).queue();
     publicChannel = channel;
    }catch (Exception error) {
     error.printStackTrace();
    }

   } else {
    try {
     TextChannel channel = bot.getTextChannelById(config.getString("utils.on-ready.channel"));
     channel.sendMessageEmbeds(embed.build()).queue();
    }catch (Exception error) {
     error.printStackTrace();
    }
   }

  }
 }

 protected static JDA getJDA() {
  return bot;
 }
}