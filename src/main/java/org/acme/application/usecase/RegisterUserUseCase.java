package org.acme.application.usecase;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.application.dto.RegisterUserDto;
import org.acme.domain.models.User;
import org.acme.domain.repository.UserRepository;
import org.acme.infrastructure.firebase.FirebaseUserCreator;

import java.util.UUID;

@ApplicationScoped
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final FirebaseUserCreator firebaseUserCreator;

    @Inject
    public RegisterUserUseCase(UserRepository userRepository, FirebaseUserCreator firebaseUserCreator) {
        this.userRepository = userRepository;
        this.firebaseUserCreator = firebaseUserCreator;
    }

    public User execute(RegisterUserDto registerUserDto) throws FirebaseAuthException {
        if (!registerUserDto.getPassword().equals(registerUserDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setFullName(registerUserDto.getFullName());
        user.setRole("USER");
        user.setActive(true);
        user.setId(UUID.randomUUID());
        UserRecord firebaseUserRecord = firebaseUserCreator.create(user.getEmail(), registerUserDto.getPassword());
        user.setFirebaseUuid(firebaseUserRecord.getUid());
        return userRepository.create(user);
    }
}
