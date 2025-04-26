package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kate.ebook.ebookserv.controllers.dtos.ProfileDto;
import ru.kate.ebook.ebookserv.entity.UserEntity;
import ru.kate.ebook.ebookserv.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Пользователь")
public class UserController {
    private final UserService userService;

    @GetMapping("/role")
    public ResponseEntity<String> getRole() {
        UserEntity currentUser = userService.getCurrentUser();
        return ResponseEntity.of(Optional.of(currentUser.getRole().toString()));
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {
        UserEntity currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(userService.getProfile(currentUser.getId()));
    }
}
