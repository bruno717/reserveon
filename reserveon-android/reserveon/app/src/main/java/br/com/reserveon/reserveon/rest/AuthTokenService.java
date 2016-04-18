package br.com.reserveon.reserveon.rest;

import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.calls.IAuthTokenService;
import br.com.reserveon.reserveon.rest.utils.ClientTimeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 20/03/2016.
 */
public class AuthTokenService {

    private static final String GRANT_TYPE = "password";

    public void authUser(final String email, final String password, final IServiceResponse<User> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .client(ClientTimeout.getClientTimeout())
                .build();

        IAuthTokenService service = retrofit.create(IAuthTokenService.class);

        Call<User> call = service.getTokenUser(GRANT_TYPE, email, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.save();

                    callback.onSuccess(user);
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
