package br.com.reserveon.reserveon.models.managers;

/**
 * Created by Bruno on 12/04/2016.
 */
public class ValidateFieldsManager {

    public static Boolean validatePassword(String password) {

        boolean isCharacterUpper = false;
        boolean isCharacterLower = false;
        boolean isCharacterNumber = false;
        boolean isCharacterSpecial = false;
        boolean isSizeMinDefault = false;


        if (password.length() > 5) {
            isSizeMinDefault = true;
        }

        for (int i = 0; i < password.length(); i++) {
            Character character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                isCharacterUpper = true;
            } else if (Character.isLowerCase(character)) {
                isCharacterLower = true;
            } else if (character.toString().matches("[0-9]")) {
                isCharacterNumber = true;
            } else if (character.toString().matches("\\W")) {
                isCharacterSpecial = true;
            }
        }

        return isCharacterUpper && isCharacterLower && isCharacterNumber && isCharacterSpecial && isSizeMinDefault;

    }
}
