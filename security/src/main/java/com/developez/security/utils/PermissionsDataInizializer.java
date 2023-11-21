package com.developez.security.utils;

import com.developez.security.entity.Permission;
import com.developez.security.enumerated.PermissionList;
import com.developez.security.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionsDataInizializer {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionsDataInizializer(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void initPermissions() {
        for ( PermissionList perm : PermissionList.values()) {
            permissionRepository.findByName(perm)
                    .orElseGet(() -> {
                        Permission newPerm = new Permission();
                        newPerm.setName(perm);
                        System.out.println("Permesso " + perm + " Creato");
                        return permissionRepository.save(newPerm);
                    });
        }
        System.out.println("|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*");
        System.out.println("|*|*|*|*| PERMESSI INIZIALIZZATI CORRETTAMENTE |*|*|*|*|");
        System.out.println("|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*|*");
    }
}
