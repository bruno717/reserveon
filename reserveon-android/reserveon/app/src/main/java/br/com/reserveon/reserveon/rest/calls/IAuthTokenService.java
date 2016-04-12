package br.com.reserveon.reserveon.rest.calls;

import br.com.reserveon.reserveon.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bruno on 20/03/2016.
 */
public interface IAuthTokenService {

    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("Token")
    Call<User> getTokenUser(@Field("grant_type") String grantType, @Field("username") String email, @Field("password") String password);
}
