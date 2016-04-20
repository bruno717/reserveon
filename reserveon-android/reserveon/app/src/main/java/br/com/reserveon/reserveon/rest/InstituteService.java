package br.com.reserveon.reserveon.rest;

import android.util.Log;

import java.util.List;
import java.util.Locale;

import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.database.UserDbHelper;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.Institute;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.calls.IInstituteService;
import br.com.reserveon.reserveon.rest.utils.ClientTimeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 19/04/2016.
 */
public class InstituteService {

    public void getInstitutes(final IServiceResponse<List<Institute>> callback) {

        User user = new UserDbHelper().getUserAuth();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .client(ClientTimeout.getClientTimeout())
                .build();

        IInstituteService service = retrofit.create(IInstituteService.class);

        Call<List<Institute>> call = service.getLastInstitutes(String.format(Locale.US, "%s %s", user.getTokenType(), user.getAccessToken()));

        call.enqueue(new Callback<List<Institute>>() {
            @Override
            public void onResponse(Call<List<Institute>> call, Response<List<Institute>> response) {
                if (response.code() == 200) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.errorBody() != null ? response.errorBody().toString() : "Error");
                }
            }

            @Override
            public void onFailure(Call<List<Institute>> call, Throwable t) {
                callback.onError(t.getLocalizedMessage());
            }
        });

    }
}
