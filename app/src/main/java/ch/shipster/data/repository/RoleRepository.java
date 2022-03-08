package ch.shipster.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ch.shipster.data.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
