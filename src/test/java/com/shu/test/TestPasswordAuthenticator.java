package com.shu.test;


import org.junit.Assert;
import org.junit.Test;

public class TestPasswordAuthenticator {
    Credentials credentials;
    PasswordGatewaySpy passwordGateway;
    PasswordService service;

    private void initWithInputPasswordAndFoundPassword(String password, String foundPassword) {
        credentials = new Credentials("username", password);
        passwordGateway = new PasswordGatewaySpy(foundPassword);
        service = new PasswordService(passwordGateway);
    }

    @Test
    public void user_is_not_authenticated_if_passwords_do_not_match() {
        initWithInputPasswordAndFoundPassword("password", "password1");

        boolean authenticated = service.authenticate(credentials);

        Assert.assertEquals(credentials.getUsername(), passwordGateway.username);
        Assert.assertFalse(authenticated);
    }

    @Test
    public void user_is_not_authenticated_if_passwords_do_not_match_case() {
        initWithInputPasswordAndFoundPassword("password", "PASSWORD");

        boolean authenticated = service.authenticate(credentials);

        Assert.assertEquals(credentials.getUsername(), passwordGateway.username);
        Assert.assertFalse(authenticated);
    }

    @Test
    public void user_is_not_authenticated_if_he_or_she_is_not_in_the_database() {
        initWithInputPasswordAndFoundPassword("password", null);

        boolean authenticated = service.authenticate(credentials);

        Assert.assertEquals(credentials.getUsername(), passwordGateway.username);
        Assert.assertFalse(authenticated);
    }

    @org.junit.Test
    public void testSuccessfulAuthentication() {
        initWithInputPasswordAndFoundPassword("password", "password");
        boolean authenticated = service.authenticate(credentials);

        Assert.assertTrue(authenticated);
        Assert.assertEquals(credentials.getUsername(), passwordGateway.username);
    }

    public static class PasswordGatewaySpy implements PasswordGateway {
        private final String password;

        public PasswordGatewaySpy(String password) {
            this.password = password;
        }

        public String username;

        @Override
        public String findPassword(String username) {
            this.username = username;
            return password;
        }
    }
}
