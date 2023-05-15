package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon;

public enum RconCommand {

    LIST("list"), // List all players
    SAY("say"), // say <message>
    SAVE_ALL("save-all"), // Saves the game
    SET_MAX_PLAYERS("setmaxplayers"), // setmaxplayers <maxPlayers: int>
    TELEPORT("teleport"),
    WHITELIST_add("whitelist add"),
    WHITELIST_remove("whitelist remove");

    private final String command;

    RconCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
