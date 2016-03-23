package br.com.reserveon.reserveon.calls;

import java.util.Map;

import br.com.reserveon.reserveon.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Bruno on 20/03/2016.
 */
public interface IUserService {
    @Headers("Content-Type: application/json")
    @POST("users")
    Call<User> registerUser(@Body Map<String, Object> mapUser);
}
