package br.com.reserveon.reserveon.models.managers;

import br.com.reserveon.reserveon.models.User;

/**
 * Created by Bruno on 11/04/2016.
 */
public class AuthManager {

    public static Boolean hasUserAuthenticated() {
        return User.listAll(User.class).size() > 0;
    }

    public static User getUser() {
        if (hasUserAuthenticated()) {
            return User.listAll(User.class).get(0);
        }
        return null;
    }


}
