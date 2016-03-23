package br.com.reserveon.reserveon.rest;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.reserveon.reserveon.calls.IUserService;
import br.com.reserveon.reserveon.configurations.ApplicationConfig;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 20/03/2016.
 */
public class UserService {

    public void registerUser(User user, final IServiceResponse<User> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(ApplicationConfig.BASE_URL)
                .build();

        IUserService service = retrofit.create(IUserService.class);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", user.getEmail());
        map.put("password", user.getPassword());
        map.put("profileId", 1);

        Call<User> call = service.registerUser(map);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("script", "onResponse");
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("script", "onFailure");
                callback.onError(t.getLocalizedMessage());
            }
        });
    }

    public User registerUser(User user) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String uri = String.format(Locale.US, "%s%s", ApplicationConfig.BASE_URL, "users");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", "test3@gmail.com");
        map.put("password", "123");
        map.put("profileId", 1);

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseEntity.getBody(), User.class);
    }
}
