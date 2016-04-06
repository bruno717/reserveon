package br.com.reserveon.reserveon.rest;

import android.util.Log;

import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.calls.IUserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 20/03/2016.
 */
public class UserService {

    public void registerUser(User user, final IServiceResponse<Void> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .build();

        IUserService service = retrofit.create(IUserService.class);

        Call<Void> call = service.registerUser(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.code() == 200) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("script", "onFailure");
                callback.onError(t.getLocalizedMessage());
            }
        });
    }
}
