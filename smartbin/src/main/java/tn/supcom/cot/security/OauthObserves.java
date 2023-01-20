package tn.supcom.cot.security;

import tn.supcom.cot.models.User;
import tn.supcom.cot.repositories.UserTokenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class OauthObserves {
    @Inject
    private UserTokenRepository repository;

    public void observe(@Observes RemoveToken removeToken) {
        final User user = removeToken.getUser();
        final String token = removeToken.getToken();
        UserToken userToken = repository.findById(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User was not found: " + user.getEmail()));
        userToken.remove(token);
        repository.save(userToken);
    }
}
