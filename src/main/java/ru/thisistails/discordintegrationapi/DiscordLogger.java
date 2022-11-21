package ru.thisistails.discordintegrationapi;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
//import net.dv8tion.jda.api.JDA;

public class DiscordLogger {
    
    //private JDA jda = API.getJDA();

    public void sendInfo(String message) {
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle("Инфо")
            .setDescription(message)
            .setColor(Color.gray);

        API.getPrivateChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public void sendWarn(String message) {
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle("Обратите внимание!")
            .setDescription(message)
            .setColor(Color.yellow);

        API.getPrivateChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public void sendErr(String message) {
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle("ОШИБКА")
            .setDescription(message)
            .setColor(Color.red);

        API.getPrivateChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public void sendPrivate(String title, String message) {
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle(title)
            .setDescription(message)
            .setColor(Color.yellow);

        API.getPrivateChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public void sendPublic(String title, String message) {
        EmbedBuilder embed = new EmbedBuilder()
            .setTitle(title)
            .setDescription(message)
            .setColor(Color.yellow);

        API.getPublicChannel().sendMessageEmbeds(embed.build()).queue();
    }

}
