package com.movebuddy.backend.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import java.util.Map;
import java.util.HashMap;

@Service
public class NotificationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";

    /**
     * Šalje push obavijest pojedinačnom uređaju preko Expo Push API-ja.
     * * @param expoPushToken Jedinstveni token uređaja (mora početi s ExponentPushToken)
     * @param title Naslov obavijesti koji iskače na ekranu
     * @param body Sadržaj / tekst poruke
     */
    public void sendPushNotification(String expoPushToken, String title, String body) {
        // Provjera ima li korisnik uopće registriran token za obavijesti
        if (expoPushToken == null || !expoPushToken.startsWith("ExponentPushToken")) {
            System.out.println("Preskačem slanje: Korisnik nema valjan Expo Push Token.");
            return;
        }

        // Priprema JSON tijela zahtjeva prema službenom Expo Push standardu
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("to", expoPushToken);
        requestBody.put("title", title);
        requestBody.put("body", body);
        requestBody.put("sound", "default"); // Omogućuje nativni zvuk obavijesti na telefonu

        // Postavljanje HTTP zaglavlja (Headers)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Pakiranje tijela i zaglavlja u jedan HTTP entitet
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Slanje sinkronog POST zahtjeva prema Expo serveru
            restTemplate.postForEntity(EXPO_PUSH_URL, entity, String.class);
            System.out.println("Push obavijest uspješno odaslana na token: " + expoPushToken);
        } catch (Exception e) {
            System.err.println("Greška prilikom komunikacije s Expo API-jem: " + e.getMessage());
        }
    }
}