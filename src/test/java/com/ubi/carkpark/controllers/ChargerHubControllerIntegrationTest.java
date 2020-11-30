package com.ubi.carkpark.controllers;

import com.ubi.carkpark.Amperes;
import com.ubi.carkpark.entities.ChargerHub;
import com.ubi.carkpark.repositories.ChargerHubRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChargerHubControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChargerHubRepository chargerHubRepository;

    public void resetDB() {
        List<ChargerHub> chargerHubListInitialState = chargerHubRepository.findAll();
        chargerHubListInitialState.stream().limit(10).forEach(c -> {
            c.setAmperes(Amperes.OFF);
            chargerHubRepository.save(c);
        });
    }

    @Test
    public void whenGetAll() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")).andReturn();
        System.out.println(mvcResult.getResponse());
    }

    @Test
    public void whenPlug() throws Exception {
        this.resetDB();
        mockMvc.perform(MockMvcRequestBuilders.put("/plug/CP1")).andReturn();
        ChargerHub chargerHub = chargerHubRepository.findAll().stream().filter(c -> c.getIdentifier().equals("CP1")).findFirst().get();

        assertEquals(chargerHub.getIdentifier(), "CP1");
        assertEquals(chargerHub.getAmperes(), Amperes.MAX_AMPERES_20);
    }

    @Test
    public void whenUnPlug() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/unplug/CP1")).andReturn();
        ChargerHub chargerHub = chargerHubRepository.findAll().stream().filter(c -> c.getIdentifier().equals("CP1")).findFirst().get();

        assertEquals(chargerHub.getIdentifier(), "CP1");
        assertEquals(chargerHub.getAmperes(), Amperes.OFF);
    }


    @Test
    public void whenPlugAtLimit() throws Exception {

        List<ChargerHub> chargerHubListInitialState = chargerHubRepository.findAll();
        chargerHubListInitialState.stream().limit(5).forEach(c -> {
            c.setAmperes(Amperes.MAX_AMPERES_20);
            chargerHubRepository.save(c);
        });

        mockMvc.perform(MockMvcRequestBuilders.put("/plug/CP6")).andReturn();

        List<ChargerHub> chargerHubListLastState = chargerHubRepository.findAll();
        ChargerHub cp4 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP4")).findFirst().get();
        ChargerHub cp5 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP5")).findFirst().get();
        ChargerHub cp6 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP6")).findFirst().get();

        assertEquals(cp4.getIdentifier(), "CP4");
        assertEquals(cp4.getAmperes(), Amperes.MAX_AMPERES_20);

        assertEquals(cp5.getIdentifier(), "CP5");
        assertEquals(cp5.getAmperes(), Amperes.MAX_AMPERES_20);

        assertEquals(cp6.getIdentifier(), "CP6");
        assertEquals(cp6.getAmperes(), Amperes.MIN_AMPERES_10);

    }

    @Test
    public void whenUnPlugReorganize() throws Exception {
        List<ChargerHub> chargerHubListInitialState = chargerHubRepository.findAll();
        chargerHubListInitialState.stream().limit(4).forEach(c -> {
            c.setAmperes(Amperes.MAX_AMPERES_20);
            chargerHubRepository.save(c);
        });
        ChargerHub cp5Initial = chargerHubListInitialState.get(4);
        cp5Initial.setAmperes(Amperes.MIN_AMPERES_10);
        chargerHubRepository.save(cp5Initial);

        ChargerHub cp6Initial = chargerHubListInitialState.get(5);
        cp6Initial.setAmperes(Amperes.MIN_AMPERES_10);
        chargerHubRepository.save(cp6Initial);

        mockMvc.perform(MockMvcRequestBuilders.put("/unplug/CP6")).andReturn();

        List<ChargerHub> chargerHubListLastState = chargerHubRepository.findAll();
        ChargerHub cp4 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP4")).findFirst().get();
        ChargerHub cp5 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP5")).findFirst().get();
        ChargerHub cp6 = chargerHubListLastState.stream().filter(c -> c.getIdentifier().equals("CP6")).findFirst().get();

        assertEquals(cp4.getIdentifier(), "CP4");
        assertEquals(cp4.getAmperes(), Amperes.MAX_AMPERES_20);

        assertEquals(cp5.getIdentifier(), "CP5");
        assertEquals(cp5.getAmperes(), Amperes.MAX_AMPERES_20);

        assertEquals(cp6.getIdentifier(), "CP6");
        assertEquals(cp6.getAmperes(), Amperes.OFF);

    }

}
