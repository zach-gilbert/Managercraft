package dev.gilbert.zacharia.managercraft.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.gilbert.zacharia.managercraft.models.DataToObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * A DTO for starting a Minecraft Server
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinecraftStartServerRequest extends DataToObject {

    /**
     * The specific Minecraft server directory within
     * the minecraft.servers.directory property of application.properties
     * Example: Academy
     */
    @JsonProperty("server_directory")
    private String server;

    /**
     * The name of the jar to launch the server
     * Example: server.jar
     */
    @JsonProperty("launch_server_jar")
    private String launchJarName;

    /**
     * The path to the JRE for executing the server, if null uses provided JRE
     * Example: C:\workspace\Managercraft Servers\servers\Academy\jre\jdk8u312-b07-jre
     */
    @JsonProperty("java_runtime_env")
    private String javaRuntimeEnv;

    /**
     * The amount of memory at minimum that will be allocated for the server
     * Example: 1024
     */
    @JsonProperty("minimum_memory")
    private String minimumMemory;

    /**
     * The amount of memory at maximum that will be allocated for the server
     * Example: 6114
     */
    @JsonProperty("maximum_memory")
    private String maximumMemory;

    /**
     * Additional arguments for launching the servers not required for vanilla
     * Example: ["-javaagent:log4jfix/Log4jPatcher-1.0.0.jar", "arg2", "arg3"]
     */
    @JsonProperty("additional_server_args")
    private List<String> additionalServerArgs;

    /**
     * Automatically restarts the server at provided internal
     * Optional, by default true
     */
    @JsonProperty("auto_restart")
    private boolean autoRestart = true;

    /**
     * Interval to restart the server
     * Optional, default 12 HOURS
     */
    @JsonProperty("auto_restart_interval")
    private int autoRestartInterval = 12;

}
