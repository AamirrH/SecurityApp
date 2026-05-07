package com.code.security.app.securitywebapp.services;


import com.code.security.app.securitywebapp.entities.SessionEntity;
import com.code.security.app.securitywebapp.entities.UserEntity;
import com.code.security.app.securitywebapp.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int sessionLimit = 4;


    public void generateNewSession(UserEntity user, String refreshToken) {

        // Get all sessions related to the user
        List<SessionEntity> sessions = sessionRepository.findAllByUser(user);
        if(sessions.size() == sessionLimit) {
            // Session limit has been reached, sort according to lastUsedAt
            sessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity lastUsedSession = sessions.getFirst();
            sessionRepository.delete(lastUsedSession);
        }
        // Create a new Session and save it in the database
        SessionEntity session = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(session);

    }





}
