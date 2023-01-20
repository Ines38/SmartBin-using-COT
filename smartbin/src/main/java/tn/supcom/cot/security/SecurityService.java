package tn.supcom.cot.security;

import jakarta.nosql.mapping.Database;
import jakarta.nosql.mapping.DatabaseType;
import tn.supcom.cot.enumer.UserRoles;
import tn.supcom.cot.exceptions.EntityNotFoundException;
import tn.supcom.cot.exceptions.NotAuthorizedException;
import tn.supcom.cot.exceptions.UserAlreadyExistsException;
import tn.supcom.cot.models.RoleDTO;
import tn.supcom.cot.models.User;
import tn.supcom.cot.repositories.UserRepository;
import tn.supcom.cot.repositories.UserTokenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.Validator;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

@ApplicationScoped
public class SecurityService {
    @Inject
    @Database(DatabaseType.DOCUMENT)
    private UserRepository repository;

    @Inject
    private UserTokenRepository token_repository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private Validator validator;

    @Inject
    private SecurityContext securityContext;



    @Inject
    private Event<RemoveToken> removeTokenEvent;


    public void create(User userDTO) {
        if (repository.existsById(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("There is an user with this id: " + userDTO.getEmail());
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            User user = User.builder()
                    .withUserName(userDTO.getUsername())
                    .withPassword(userDTO.getPassword())
                    .withEmail(userDTO.getEmail())
                    .withTelephone(userDTO.getTelephone())
                    .withRoles(getRole())
                    .build();
            repository.save(user);
        }
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public void updatePassword(String id, User dto) {

        final Principal principal = securityContext.getCallerPrincipal();
        if (isForbidden(id, securityContext, principal)) {
            throw new NotAuthorizedException();
        }

        final User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        user.updatePassword(dto.getPassword(), passwordHash);
        repository.save(user);
    }


    public void addRole(String id, RoleDTO dto) {
        final User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));

        user.add(dto.getRoles());
        repository.save(user);

    }


    public void removeRole(String id, RoleDTO dto) {
        final User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        user.remove(dto.getRoles());
        repository.save(user);
    }

    public User getUser() {
        final User user = getLoggedUser();
        User dto = toDTO(user);
        return dto;
    }



    public User findBy(String username, String password) {
        final User user = repository.findById(username)
                .orElseThrow(() -> new NotAuthorizedException("User not authorized"));

        if (passwordHash.verify(password.toCharArray(), user.getPassword())) {
            return user;
        }
        throw new NotAuthorizedException("User not authorized");

    }

    public User findBy(String username) {
        return repository.findById(username)
                .orElseThrow(() -> new NotAuthorizedException("User not authorized"));
    }

    public void removeUser(String userId) {
        final User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId));
        repository.deleteById(user.getEmail());

    }

    public void removeToken(String token) {
        final User loggedUser = getLoggedUser();
        RemoveToken removeToken = new RemoveToken(loggedUser, token);
        removeTokenEvent.fire(removeToken);
    }

    private User getLoggedUser() {
        final Principal principal = securityContext.getCallerPrincipal();
        if (principal == null) {
            throw new NotAuthorizedException("User not authorized");
        }
        return repository.findById(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException(principal.getName()));
    }

    private User toDTO(User user) {
        User dto = new User();
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        return dto;
    }

    private Set<UserRoles> getRole() {
        if (repository.count() == 0) {
            return Collections.singleton(UserRoles.Administrateur);
        } else {
            return Collections.singleton(UserRoles.Client);
        }
    }

    private boolean isForbidden(String id, SecurityContext context, Principal principal) {
        return !(context.isCallerInRole(UserRoles.Administrateur.name()) || id.equals(principal.getName()));
    }

}
