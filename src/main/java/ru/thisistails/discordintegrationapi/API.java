package ru.thisistails.discordintegrationapi;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class API {
    /**
     * Returns JDA
     * @return {@link JDA}
     */
    public static JDA getJDA() {
        return BotLoader.getJDA();
    }

    /**
     * Returns public log channel.
     * @return {@link TextChannel} | Public log channel
     */
    public static TextChannel getPublicChannel() {
        return BotLoader.publicChannel;
    }

    /**
     * Returns private log channel for moderators.
     * @return {@link TextChannel} | Moderator log channel
     */
    public static TextChannel getPrivateChannel() {
        return BotLoader.privateChannel;
    }

}
