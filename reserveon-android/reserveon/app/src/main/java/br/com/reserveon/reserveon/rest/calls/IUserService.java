package br.com.reserveon.reserveon.rest.calls;

import br.com.reserveon.reserveon.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bruno on 20/03/2016.
 */
public interface IUserService {
    @Headers("Content-Type: application/json")
    @POST("users")
    Call<Void> registerUser(@Body User mapUser);

    @Headers("Content-Type: application/json")
    @GET("users/signin")
    Call<User> getUserAuthenticated(@Header("Authorization") String token);
}
