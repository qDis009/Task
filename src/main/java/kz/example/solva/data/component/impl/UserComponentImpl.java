package kz.example.solva.data.component.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.example.solva.data.component.UserComponent;
import kz.example.solva.data.entity.User;
import kz.example.solva.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserComponentImpl implements UserComponent {
    private final UserRepository userRepository;

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
