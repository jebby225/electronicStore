package com.bullish.electronicStore.repository;

import com.bullish.electronicStore.entity.User;
import com.bullish.electronicStore.enums.Status;
import com.bullish.electronicStore.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    User findByEmail(String email);


    User findById(int id);

    List<User> findByRole(UserRoles role);

    void deleteByEmail(String email);

}
