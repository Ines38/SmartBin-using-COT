package tn.smartbin.cot.repositories;

import jakarta.nosql.mapping.Repository;
import tn.smartbin.cot.models.User;

import java.util.stream.Stream;

public interface UserRepository extends Repository<User, String> {
    Stream<User> findAll();
    Stream<User> findByUserId(Long userId);
    Stream<User> findByUsername(String username);
    Stream<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
