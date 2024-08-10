package dom.com.thesismonolitserver.security;

import dom.com.thesismonolitserver.enteties.UserDataEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {
    private final UserDataEntity userData;
    private final List<String> roles;
    private final List<String> privileges;

    public UserSecurity(UserDataEntity userData, List<String> roles, List<String> privileges) {
        this.userData = userData;
        this.roles = roles;
        this.privileges = privileges;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> listOfRolesAndPrivileges = new ArrayList<>();
        listOfRolesAndPrivileges.addAll(roles);
        listOfRolesAndPrivileges.addAll(privileges);
        return listOfRolesAndPrivileges.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userData.getValidEmail() && userData.getValidPhone();
    }

    public UserDataEntity getUserData() {
        return userData;
    }
}
