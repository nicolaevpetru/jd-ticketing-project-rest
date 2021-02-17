package com.ticketing.implementation;

import com.ticketing.entity.ConfirmationToken;
import com.ticketing.exception.TicketingProjectException;
import com.ticketing.repository.ConfirmationTokenRepository;
import com.ticketing.service.ConfirmationTokenService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;
    private JavaMailSender javaMailSender;

    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, JavaMailSender javaMailSender) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Override
    public ConfirmationToken readByToken(String token) throws TicketingProjectException {

        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).orElse(null);

        if (confirmationToken == null) {
            throw new TicketingProjectException("This token does not exists");
        }

        if (!confirmationToken.isTokenValid(confirmationToken.getExpireDate())) {
            throw new TicketingProjectException("This token has been expired");
        }

        return confirmationToken;
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {

        confirmationToken.setIsDeleted(true);
        confirmationTokenRepository.save(confirmationToken);

    }
}
