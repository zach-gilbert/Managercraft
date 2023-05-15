package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console;

public enum MinecraftThreads {

    MAIN_THREAD("[minecraft/MainThread]"),
    DEDICATED_SERVER("[minecraft/DedicatedServer]");

    private final String threadName;

    MinecraftThreads(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadName() {
        return threadName;
    }
}

