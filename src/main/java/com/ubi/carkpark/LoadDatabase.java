package com.ubi.carkpark;

import com.ubi.carkpark.entities.ChargerHub;
import com.ubi.carkpark.repositories.ChargerHubRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ChargerHubRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new ChargerHub("CP1", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP2", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP3", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP4", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP5", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP6", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP7", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP8", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP9", Amperes.OFF)));
            log.info("Preloading " + repository.save(new ChargerHub("CP10", Amperes.OFF)));
        };
    }
}