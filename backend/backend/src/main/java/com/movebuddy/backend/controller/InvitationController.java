package com.movebuddy.backend.controller;

import com.movebuddy.backend.model.Invitation;
import com.movebuddy.backend.model.User;
import com.movebuddy.backend.repository.InvitationRepository;
import com.movebuddy.backend.repository.UserRepository;
import com.movebuddy.backend.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Endpoint za slanje poziva suputniku u blizini.
     * Pristup: Zaštićen (zahtijeva valjan Bearer JWT token u zaglavlju)
     * * @param payload JSON objekt koji sadrži "receiverId" (ID korisnika kojeg zovemo)
     * @param principal Automatski ubrizgan ulogirani korisnik iz Spring Security konteksta
     */
    @PostMapping("/send")
    public ResponseEntity<?> sendInvitation(@RequestBody Map<String, Long> payload, Principal principal) {
        
        // 1. Sigurnosna provjera: Ako payload ne sadrži receiverId, vrati loš zahtjev
        if (!payload.containsKey("receiverId")) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Nedostaje parametar 'receiverId' unutar JSON tijela.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Long receiverId = payload.get("receiverId");

        // 2. Izvuci pošiljatelja (Ulogiranog korisnika) preko njegovog emaila iz JWT-a
        String senderEmail = principal.getName();
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Ulogirani korisnik nije pronađen u bazi podataka."));

        // 3. Pronađi primatelja poziva u bazi
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Korisnik kojeg pokušavate pozvati ne postoji."));

        // 4. Kreiraj novi zapis o pozivu i spremi ga u bazu podataka
        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setStatus("PENDING"); // Postavlja se na čekanje dok se primatelj ne očituje
        invitation.setTimestamp(LocalDateTime.now());
        
        invitationRepository.save(invitation);

        // 5. Pokreni push obavijest za primatelja preko spremljenog Expo tokena
        String notificationTitle = "Novi suputnik u blizini! 🏃‍♂️";
        String notificationBody = sender.getName() + " te poziva na zajedničko kretanje. Pridruži se!";
        
        notificationService.sendPushNotification(receiver.getPushToken(), notificationTitle, notificationBody);

        // 6. Vrati uspješan odgovor klijentu
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Poziv je uspješno kreiran, a push obavijest je poslana.");
        successResponse.put("status", "PENDING");
        
        return ResponseEntity.ok(successResponse);
    }
}