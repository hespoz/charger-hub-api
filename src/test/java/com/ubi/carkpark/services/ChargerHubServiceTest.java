package com.ubi.carkpark.services;

import com.ubi.carkpark.Amperes;
import com.ubi.carkpark.entities.ChargerHub;
import com.ubi.carkpark.repositories.ChargerHubRepository;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ChargerHubServiceTest {

    @InjectMocks
    private ChargerHubService mockChargerHubService;

    @Mock
    private ChargerHubRepository mockChargerHubRepository;

    private List<ChargerHub> allPorts = null;

    @Before
    public void init() {

        ChargerHub CP1 = new ChargerHub("CP1", Amperes.OFF);
        ChargerHub CP2 = new ChargerHub("CP2", Amperes.OFF);
        ChargerHub CP3 = new ChargerHub("CP3", Amperes.OFF);
        ChargerHub CP4 = new ChargerHub("CP4", Amperes.OFF);
        ChargerHub CP5 = new ChargerHub("CP5", Amperes.OFF);
        ChargerHub CP6 = new ChargerHub("CP6", Amperes.OFF);
        ChargerHub CP7 = new ChargerHub("CP7", Amperes.OFF);
        ChargerHub CP8 = new ChargerHub("CP8", Amperes.OFF);
        ChargerHub CP9 = new ChargerHub("CP9", Amperes.OFF);
        ChargerHub CP10 = new ChargerHub("CP10", Amperes.OFF);

        when(mockChargerHubRepository.findByIdentifier("CP1")).thenReturn(CP1);
        when(mockChargerHubRepository.findByIdentifier("CP2")).thenReturn(CP2);
        when(mockChargerHubRepository.findByIdentifier("CP3")).thenReturn(CP3);
        when(mockChargerHubRepository.findByIdentifier("CP4")).thenReturn(CP4);
        when(mockChargerHubRepository.findByIdentifier("CP5")).thenReturn(CP5);
        when(mockChargerHubRepository.findByIdentifier("CP6")).thenReturn(CP6);
        when(mockChargerHubRepository.findByIdentifier("CP7")).thenReturn(CP7);
        when(mockChargerHubRepository.findByIdentifier("CP8")).thenReturn(CP8);
        when(mockChargerHubRepository.findByIdentifier("CP9")).thenReturn(CP9);
        when(mockChargerHubRepository.findByIdentifier("CP10")).thenReturn(CP10);

        allPorts = Arrays.asList(CP1, CP2, CP3, CP4, CP5, CP6, CP7, CP8, CP9, CP10);

        when(mockChargerHubRepository.findAll()).thenReturn(allPorts);

    }

    @Test
    public void testGetAll() {
        List result = mockChargerHubService.getAll();
        assertArrayEquals(result.toArray(), allPorts.toArray());
    }

    @Test
    public void testPlugAndUnPlug() throws Exception {
        List<ChargerHub> chargerListAfterPlug = mockChargerHubService.plug("CP1");
        ChargerHub cp1 = chargerListAfterPlug.stream().filter(c -> c.getIdentifier().equals("CP1")).findFirst().get();
        assertEquals(cp1.getIdentifier(), "CP1");
        assertEquals(cp1.getAmperes(), Amperes.MAX_AMPERES_20);

        List<ChargerHub> chargerListAfterUnPlug =  mockChargerHubService.unplug("CP1");
        ChargerHub cp1Off = chargerListAfterUnPlug.stream().filter(c -> c.getIdentifier().equals("CP1")).findFirst().get();
        assertEquals(cp1Off.getIdentifier(), "CP1");
        assertEquals(cp1Off.getAmperes(), Amperes.OFF);
    }

}
