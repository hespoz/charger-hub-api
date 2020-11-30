package com.ubi.carkpark.entities;
import com.ubi.carkpark.Amperes;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class ChargerHub {
    private @Id @GeneratedValue Long id;
    private String identifier;

    @Enumerated(EnumType.STRING)
    private Amperes amperes;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    public ChargerHub(){}

    public ChargerHub(String identifier, Amperes amperes){
        this.identifier = identifier;
        this.amperes = amperes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Amperes getAmperes() {
        return amperes;
    }

    public void setAmperes(Amperes amperes) {
        this.amperes = amperes;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}