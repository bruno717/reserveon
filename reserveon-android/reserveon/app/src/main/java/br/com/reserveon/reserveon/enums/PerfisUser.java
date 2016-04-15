package br.com.reserveon.reserveon.enums;

/**
 * Created by Bruno on 14/04/2016.
 */
public enum PerfisUser {

    USER_CLIENT(1);

    private Integer perfilCod;

    PerfisUser(Integer perfilCod) {
        this.perfilCod = perfilCod;
    }

    public Integer getPerfilCod() {
        return perfilCod;
    }
}
