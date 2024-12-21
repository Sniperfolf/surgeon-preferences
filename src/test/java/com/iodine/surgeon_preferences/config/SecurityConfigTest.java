package com.iodine.surgeon_preferences.config;

import com.iodine.surgeon_preferences.model.User;
import com.iodine.surgeon_preferences.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_PASSWORD = "testPassword";
    private static final String WRONG_PASSWORD = "wrongPassword";

    @BeforeEach
    void setUp() {

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();


        User regularUser = new User();
        regularUser.setUsername(TEST_USERNAME);

        when(userDetailsService.loadUserByUsername(TEST_USERNAME)).thenReturn(userDetails);
        when(userService.getUserByUsername(TEST_USERNAME)).thenReturn(regularUser);
        when(passwordEncoder.matches(eq(TEST_PASSWORD), any())).thenReturn(true);
        when(passwordEncoder.matches(eq(WRONG_PASSWORD), any())).thenReturn(false);
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        ResultActions result = mockMvc.perform(formLogin()
                .user(TEST_USERNAME)
                .password(TEST_PASSWORD));

        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/surgeons"))
                .andExpect(authenticated().withUsername(TEST_USERNAME));

        verify(userService).getUserByUsername(TEST_USERNAME);
        verify(userService).save(any(User.class));
    }

    @Test
    void testFailedLogin() throws Exception {
        ResultActions result = mockMvc.perform(formLogin()
                .user(TEST_USERNAME)
                .password(WRONG_PASSWORD));

        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());

        verify(userService, never()).save(any(User.class));
    }
}