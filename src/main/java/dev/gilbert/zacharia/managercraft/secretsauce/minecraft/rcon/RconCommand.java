package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon;

/**
 * The RconCommand contains constants for common RCON commands in a Minecraft server.
 */
public final class RconCommand {

    /**
     * Represents the 'list' command to list all players.
     */
    public static final String LIST = "list";

    /**
     * Represents the 'say' command to send a message to all players.
     */
    public static final String SAY = "say";

    /**
     * Represents the 'save-all' command to save the game.
     */
    public static final String SAVE_ALL = "save-all";

    /**
     * Represents the 'setmaxplayers' command to set the maximum number of players.
     */
    public static final String SET_MAX_PLAYERS = "setmaxplayers";

    /**
     * Represents the 'teleport' command to teleport a player.
     */
    public static final String TELEPORT = "teleport";

    /**
     * Represents the 'whitelist add' command to add a player to the whitelist.
     */
    public static final String WHITELIST_ADD = "whitelist add";

    /**
     * Represents the 'whitelist remove' command to remove a player from the whitelist.
     */
    public static final String WHITELIST_REMOVE = "whitelist remove";

    /**
     * Private Constructor
     */
    private RconCommand() {

    }
}

