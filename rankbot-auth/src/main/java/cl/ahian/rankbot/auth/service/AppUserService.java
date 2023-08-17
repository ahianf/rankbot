package cl.ahian.rankbot.auth.service;

import cl.ahian.rankbot.auth.dto.CreateAppUserDto;
import cl.ahian.rankbot.auth.dto.MessageDto;
import cl.ahian.rankbot.auth.entity.AppUser;
import cl.ahian.rankbot.auth.entity.Role;
import cl.ahian.rankbot.auth.enums.RoleName;
import cl.ahian.rankbot.auth.repository.AppUserRepository;
import cl.ahian.rankbot.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto createUser(CreateAppUserDto dto){
        AppUser appUser = AppUser.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        dto.roles().forEach(r -> {
            Role role = repository.findByRole(RoleName.valueOf(r))
                    .orElseThrow(()-> new RuntimeException("role not found"));
            roles.add(role);
        });
        appUser.setRoles(roles);
        appUserRepository.save(appUser);
        return new MessageDto("user " + appUser.getUsername() + " saved");
    }
}
