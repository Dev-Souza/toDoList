package com.project.ToDoList.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ToDoList.models.categories.CategoriesModel;
import com.project.ToDoList.models.tasks.TasksModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Table(name = "users")
@Entity()
public class UsersModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column
    private String fotoPerfil;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UsersRole role;

    public UsersModel(String username, String password, String email, String phone, String fotoPerfil, UsersRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.fotoPerfil = fotoPerfil;
        this.role = role;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TasksModel> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CategoriesModel> categories = new ArrayList<>();

    public UsersModel() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UsersRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}
