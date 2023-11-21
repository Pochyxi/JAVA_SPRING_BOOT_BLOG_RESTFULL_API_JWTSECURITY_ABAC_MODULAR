package com.developez.security.repository;

import com.developez.security.entity.Permission;
import com.developez.security.enumerated.PermissionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName( PermissionList name );

    List<Permission> findByNameIn( List<PermissionList> permissions );
}
