package br.com.reserveon.reserveon.database;

import br.com.reserveon.reserveon.models.User;

/**
 * Created by Bruno on 15/04/2016.
 */
public class UserDbHelper {

    public User getUserAuth(){
        return User.listAll(User.class).get(0);
    }

}
