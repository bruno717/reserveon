package br.com.reserveon.reserveon.rest;

import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.calls.IAuthTokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 20/03/2016.
 */
public class AuthTokenService {

    public void registerUser(String email, String password, final IServiceResponse<User> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .build();

        IAuthTokenService service = retrofit.create(IAuthTokenService.class);

        Call<User> call = service.getTokenUser("password", "test10@android.com", "123aA!");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.errorBody() != null ? response.errorBody().toString() : "Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });

        /*call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.errorBody() != null ? response.errorBody().toString() : "Error");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("script", "onFailure: " + t.getLocalizedMessage());
                callback.onError(t.getLocalizedMessage());
            }
        });*/
    }
}
