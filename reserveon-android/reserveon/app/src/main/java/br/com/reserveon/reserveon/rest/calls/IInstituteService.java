package br.com.reserveon.reserveon.rest.calls;

import java.util.List;

import br.com.reserveon.reserveon.models.Institute;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bruno on 17/04/2016.
 */
public interface IInstituteService {

    @Headers("Content-Type: application/json")
    @GET("institute/recents")
    Call<List<Institute>> getLastInstitutes(@Header("Authorization") String token);

    @GET("institute/name")
    Call<List<Institute>> getInstitutesByName(@Header("Authorization") String token, @Query("name") String name);
}
