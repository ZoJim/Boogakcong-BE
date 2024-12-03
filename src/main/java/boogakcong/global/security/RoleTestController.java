package boogakcong.global.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleTestController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<?> adminOnly(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok("Hello, " + userDetails.getUsername());
    }

    @GetMapping("/check-role")
    public ResponseEntity<?> check(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok("Hello, " + userDetails.getUsername() + userDetails.getAuthorities());
    }
}
