package br.com.reserveon.reserveon.models.managers;

/**
 * Created by Bruno on 12/04/2016.
 */
public class ValidateFieldsManager {

    public static Boolean validatePassword(String password) {

        boolean isCaracterUpper = false;
        boolean isCaracterLower = false;
        boolean isCaracterNumber = false;
        boolean isCaracterSpecial = false;
        boolean isSizeMinDefault = false;


        if (password.length() > 5) {
            isSizeMinDefault = true;
        }

        for (int i = 0; i < password.length(); i++) {
            Character caractere = password.charAt(i);

            if (Character.isUpperCase(caractere)) {
                isCaracterUpper = true;
            } else if (Character.isLowerCase(caractere)) {
                isCaracterLower = true;
            } else if (caractere.toString().matches("[0-9]")) {
                isCaracterNumber = true;
            } else if (caractere.toString().matches("\\W")) {
                isCaracterSpecial = true;
            }
        }

        return isCaracterUpper && isCaracterLower && isCaracterNumber && isCaracterSpecial && isSizeMinDefault;

    }
}
