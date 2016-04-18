package br.com.reserveon.reserveon.rest;

import java.util.Locale;

import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.database.UserDbHelper;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.calls.IUserService;
import br.com.reserveon.reserveon.rest.utils.ClientTimeout;
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
                .client(ClientTimeout.getClientTimeout())
                .build();

        IUserService service = retrofit.create(IUserService.class);

        Call<Void> call = service.registerUser(user);

        call.enqueue(new Callback<Void>() {
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
                callback.onError(t.getLocalizedMessage());
            }
        });
    }

    public void getUserAuth(String token, final IServiceResponse<User> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .client(ClientTimeout.getClientTimeout())
                .build();

        IUserService service = retrofit.create(IUserService.class);

        Call<User> call = service.getUserAuthenticated(String.format(Locale.US, ApplicationConfig.FORMAT_STRING_CALL_WITH_BEARER, ApplicationConfig.BEARER_AUTH, token));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = new UserDbHelper().getUserAuth();
                    user.save(response.body());
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
    }
}
