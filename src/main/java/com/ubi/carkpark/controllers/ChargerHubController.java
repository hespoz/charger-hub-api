package com.ubi.carkpark.controllers;

import com.ubi.carkpark.entities.ChargerHub;
import com.ubi.carkpark.services.ChargerHubService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ChargerHubController {

    private final ChargerHubService chargerHubService;

    ChargerHubController(ChargerHubService chargerHubService) {
        this.chargerHubService = chargerHubService;
    }

    @CrossOrigin
    @GetMapping("/")
    public List<ChargerHub> getAll(){
        return this.chargerHubService.getAll();
    }

    @CrossOrigin
    @PutMapping("/plug/{identifier}")
    public List<ChargerHub> plug(@PathVariable String identifier) {
        return this.chargerHubService.plug(identifier);
    }

    @CrossOrigin
    @PutMapping("/unplug/{identifier}")
    public List<ChargerHub> unplug(@PathVariable String identifier) {
        return this.chargerHubService.unplug(identifier);
    }

}
