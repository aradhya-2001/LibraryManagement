package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.library.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    // These are Derived Query Methods and method name should be predefined only, not custom. Hibernate will create the query by itself only.
    List<User> findByName(String name);
    List<User> findByNameContains(String name);
    List<User> findByEmail(String email);
    List<User> findByEmailContains(String email);
    List<User> findByPhoneNum(String phoneNum);
    List<User> findByPhoneNumContains(String phoneNum);

    // 2 other ways for queries:
    // 1. when nativeQuery = false, then hibernate will run the query in compile time.
    @Query(value = "select u from User u where name=:name", nativeQuery = false)
    List<User> fetchUserByName(String name);

    // 2. when nativeQuery = false, then mysql will run the query in run time.
    @Query(value = "select * from User where name=:name", nativeQuery = true)
    List<User> fetchUserByNameNative(String name);
}
