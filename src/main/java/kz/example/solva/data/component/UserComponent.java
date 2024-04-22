package kz.example.solva.data.component;

import kz.example.solva.data.entity.User;

public interface UserComponent {
    User findById(int id);
    User create(User user);
    User update(User user);
}
