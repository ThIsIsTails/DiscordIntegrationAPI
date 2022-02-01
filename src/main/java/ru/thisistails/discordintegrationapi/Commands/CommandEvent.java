package ru.thisistails.discordintegrationapi.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.thisistails.discordintegrationapi.DiscordIntegrationAPI;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.util.Date;

public class CommandEvent extends ListenerAdapter {

    private final DiscordIntegrationAPI pl;
    private YamlConfiguration config = null;

    public CommandEvent(DiscordIntegrationAPI pl) {
        this.pl = pl;
        config = (YamlConfiguration) pl.getConfig();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //pl.getLogger().info("Message");

        long start = System.currentTimeMillis();

        String[] command = event.getMessage().getContentRaw().split(" ");

        if(!command[0].startsWith("!")) return;

        if(command[0].equals("!server")) {

            long uptime = ManagementFactory.getRuntimeMXBean().getUptime();

            long seconds = (uptime / 1000) % 60;
            long minutes = (uptime / (1000 * 60)) % 60;
            long hours = (uptime / (1000 * 60 * 60));

            EmbedBuilder embed = new EmbedBuilder()
                    .setTimestamp(new Date().toInstant())
                    .setColor(Color.CYAN)
                    .setTitle("Статус " + config.getString("server-name"))
                    .addField("Игороков", pl.getServer().getOnlinePlayers().size() + " из " + pl.getServer().getMaxPlayers(), false)
                    .addField("Uptime (Время работы)", hours + "h:" + minutes + "m:" + seconds + "s", false);

            event.getMessage().replyEmbeds(embed.setFooter("Обработка заняла " + (System.currentTimeMillis() - start) + "ms").build()).queue();
        }
    }

}
