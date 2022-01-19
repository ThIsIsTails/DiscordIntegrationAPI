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
  /*if(config.getBoolean("utils.enable-startup-message:")) {
   TextChannel channel = (TextChannel) bot.getGuildChannelById(config.getString("utils.startup-message-channel"));
   EmbedBuilder embed = new EmbedBuilder();

   if(!Objects.equals(config.getString("utils.startup-message.title"), "null"))
    embed.setTitle(config.getString("utils.startup-message.title"));
   if(!Objects.equals(config.getString("utils.startup-message.description"), "null"))
    embed.setTitle(config.getString("utils.startup-message.description"));
   if(config.getBoolean("utils.startup-message.timestamp"))
    embed.setTimestamp(new Date().toInstant());

   embed.setColor(config.getInt("utils.startup-messgae.color"));

   if (channel == null) main.getLogger().severe("Log channel is null! Skipping step.");
   else channel.sendMessageEmbeds(embed.build()).queue();*/
  }

  public static JDA getJDA() { return bot; }
 }
