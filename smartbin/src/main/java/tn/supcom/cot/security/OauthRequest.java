package tn.supcom.cot.security;

import tn.supcom.cot.FieldPropertyVisibilityStrategy;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbVisibility;
import javax.validation.constraints.NotBlank;

@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
public class OauthRequest {
    @JsonbProperty("grand_type")
    @NotBlank
    public String grandType;
    // @FormParam("email")
    @JsonbProperty("email")
    @NotBlank(groups = {GenerateToken.class})
    private String email;
    // @FormParam("password")
    @JsonbProperty("password")
    @NotBlank(groups = {GenerateToken.class})
    private String password;
    // @FormParam("refresh_token")
    @JsonbProperty("refreshToken")
    @NotBlank(groups = {RefreshToken.class})
    private String refreshToken;

    public void setGrandType(GrantType grandType) {
        if(grandType != null) {
            this.grandType = grandType.get();
        }
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public GrantType getGrandType() {
        if(grandType != null) {
            return GrantType.parse(grandType);
        }
        return null;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }


    public @interface  GenerateToken{}

    public @interface  RefreshToken{}
}
