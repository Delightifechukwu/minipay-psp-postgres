
package com.minipay.repository;

import com.minipay.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String admin);
    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.users WHERE r.name = :name")
    Optional<Role> findByNameWithUsers(@Param("name") String name);

}
