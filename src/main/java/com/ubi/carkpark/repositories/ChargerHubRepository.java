package com.ubi.carkpark.repositories;

import com.ubi.carkpark.entities.ChargerHub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargerHubRepository extends JpaRepository<ChargerHub, Long> {
    ChargerHub findByIdentifier(String identifier);
}