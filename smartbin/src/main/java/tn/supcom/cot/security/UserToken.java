package tn.supcom.cot.security;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;
import tn.supcom.cot.repositories.UserTokenRepository;

import java.util.*;

@Entity
public class UserToken {
    @Id
    private String email;

    @Column
    private Set<RefreshToken> tokens;

    UserToken(String email) {
        this.email = email;
    }

    @Deprecated
    UserToken() {
    }

    public String getEmail() {
        return email;
    }

    public Set<RefreshToken> getTokens() {
        if (tokens == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(tokens);
    }

    void add(RefreshToken refreshToken) {
        initiateTokens();
        this.tokens.add(refreshToken);
    }

    RefreshToken update(AccessToken accessToken, String refreshTokenText, UserTokenRepository repository) {
        initiateTokens();
        this.tokens.removeIf(r -> refreshTokenText.equals(r.getToken()));
        RefreshToken refreshToken = new RefreshToken(Token.generate(), accessToken);
        this.tokens.add(refreshToken);
        repository.save(this);
        return refreshToken;
    }

    void remove(String token) {
        initiateTokens();
        this.tokens.removeIf(r -> token.equals(r.getToken()));
    }

    Optional<AccessToken> findAccessToken(String accessToken) {
        initiateTokens();
        return this.tokens.stream().map(RefreshToken::getAccessToken)
                .filter(a -> a.getToken().equals(accessToken))
                .filter(AccessToken::isValid)
                .findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserToken userToken = (UserToken) o;
        return Objects.equals(email, userToken.email);
    }

    private void initiateTokens() {
        if (this.tokens == null) {
            this.tokens = new HashSet<>();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "username='" + email + '\'' +
                ", tokens=" + tokens +
                '}';
    }

}
