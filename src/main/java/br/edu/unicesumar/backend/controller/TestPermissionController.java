package br.edu.unicesumar.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestPermissionController {

    @GetMapping("/any-user")
    public ResponseEntity<String> testPermissionAnyUser() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/only-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> testPermissionOnlyInternalRole() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/only-company")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<String> testPermissionOnlyCompanyRole() {
        return ResponseEntity.ok("OK");
    }
    
    @GetMapping("/only-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> testPermissionOnlyAdminRole() {
        return ResponseEntity.ok("OK");
    }

}
