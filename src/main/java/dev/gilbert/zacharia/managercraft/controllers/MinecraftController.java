package dev.gilbert.zacharia.managercraft.controllers;

import dev.gilbert.zacharia.managercraft.models.requests.MinecraftStartServerRequest;
import dev.gilbert.zacharia.managercraft.models.responses.GenericResponseMessage;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server.ServerProcessManager;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconCommand;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/minecraft")
public class MinecraftController {

    //TODO, replace with enum
    private static final String START_SERVER = "Start Server";
    private static final String STOP_SERVER = "Stop Server";

    private final ServerProcessManager serverProcessManager;
    private final RconHandler rconHandler;

    @Autowired
    public MinecraftController(ServerProcessManager serverProcessManager,
                               RconHandler rconHandler) {
        this.serverProcessManager = serverProcessManager;
        this.rconHandler = rconHandler;
    }

    /* TODO:
        - Improve GenericResponseMessages
            - Test by failing some operations
        - Change some things to POST
    */

    @PostMapping("/startServer")
    private ResponseEntity<GenericResponseMessage> startServer(@RequestBody MinecraftStartServerRequest request) {
        GenericResponseMessage genericResponseMessage;

        log.info("Received a '{}' request", START_SERVER);

        genericResponseMessage = serverProcessManager.startServer(request);
        genericResponseMessage.setCommand(START_SERVER);

        return ResponseEntity.ok(genericResponseMessage);
    }

    @PostMapping("/stopServer")
    private ResponseEntity<GenericResponseMessage> stopServer() {
        GenericResponseMessage genericResponseMessage = new GenericResponseMessage();

        log.info("Received a '{}' request", STOP_SERVER);

        genericResponseMessage.setCommand(STOP_SERVER);
        genericResponseMessage.setSuccess(true);
        genericResponseMessage.setMessage("testing");

        serverProcessManager.stopServer();

        return ResponseEntity.ok(genericResponseMessage);
    }

    @PostMapping("/saveAll")
    private ResponseEntity<GenericResponseMessage> saveAll() {
        log.info("Received a '{}' request", RconCommand.SAVE_ALL);

        GenericResponseMessage genericResponseMessage = rconHandler.saveAll();
        genericResponseMessage.setCommand(RconCommand.SAVE_ALL);

        return ResponseEntity.ok(genericResponseMessage);
    }

    @GetMapping("/say")
    private ResponseEntity<GenericResponseMessage> say(@RequestParam("message") String message) {
        log.info("Received a '{}' request", RconCommand.SAY);

        GenericResponseMessage genericResponseMessage = rconHandler.say(message);
        genericResponseMessage.setCommand(RconCommand.SAY);

        return ResponseEntity.ok(genericResponseMessage);
    }

}
