package com.ubi.carkpark.services;

import com.ubi.carkpark.Amperes;
import com.ubi.carkpark.entities.ChargerHub;
import com.ubi.carkpark.repositories.ChargerHubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ChargerHubService {

    private final ChargerHubRepository repository;

    ChargerHubService(ChargerHubRepository repository) {
        this.repository = repository;
    }

    public List<ChargerHub> getAll() {
        return repository.findAll();
    }

    @Transactional()
    public List<ChargerHub> plug(String identifier) {
        ChargerHub selectedChargerHub = this.repository.findByIdentifier(identifier);
        if(selectedChargerHub == null || selectedChargerHub.getAmperes().getValue() > 0){
            return null;
        }

        if (this.getCurrentConsume().equals(100)) {
            Optional<ChargerHub> c = this.repository.findAll().stream()
                    .filter(chargerHub -> chargerHub.getAmperes().equals(Amperes.MAX_AMPERES_20))
                    .sorted(Comparator.comparing(ChargerHub::getUpdateDateTime).reversed()).findFirst();

            c.ifPresent(c1 -> {
                c1.setAmperes(Amperes.MIN_AMPERES_10);
                this.repository.save(c1);
            });

            selectedChargerHub.setAmperes(Amperes.MIN_AMPERES_10);
            this.repository.save(selectedChargerHub);
        } else {
            selectedChargerHub.setAmperes(Amperes.MAX_AMPERES_20);
            this.repository.save(selectedChargerHub);
        }
        return repository.findAll();
    }

    @Transactional()
    public List<ChargerHub> unplug(String identifier) {
        ChargerHub selectedChargerHub = this.repository.findByIdentifier(identifier);
        if(selectedChargerHub == null){
            return null;
        }

        selectedChargerHub.setAmperes(Amperes.OFF);

        Optional<ChargerHub> c = this.repository.findAll().stream()
                .filter(chargerHub -> chargerHub.getAmperes().equals(Amperes.MIN_AMPERES_10))
                .sorted(Comparator.comparing(ChargerHub::getUpdateDateTime)).findFirst();

        c.ifPresent(c1 -> {
            c1.setAmperes(Amperes.MAX_AMPERES_20);
            this.repository.save(c1);
        });

        this.repository.save(selectedChargerHub);

        return repository.findAll();
    }

    private Integer getCurrentConsume(){
        return this.repository.findAll().stream().mapToInt(c -> c.getAmperes().getValue()).sum();
    }
}
