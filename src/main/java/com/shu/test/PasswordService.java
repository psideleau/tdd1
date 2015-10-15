package com.shu.test;

/**
 * Created by paul_sideleau on 10/8/15.
 */
public class PasswordService {
    private final PasswordGateway passwordGateway;

    public PasswordService(PasswordGateway passwordGateway) {
        this.passwordGateway = passwordGateway;
    }

    public boolean authenticate(Credentials credentials) {
        String password = passwordGateway.findPassword(credentials.getUsername());

        return password != null && credentials.getPassword().equals(password);
    }
}
