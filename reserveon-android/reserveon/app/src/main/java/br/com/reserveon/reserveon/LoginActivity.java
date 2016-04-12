package br.com.reserveon.reserveon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.AuthTokenService;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 11/04/2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_login_button_enter)
    public void onClickAuthUser(View v) {

        new AuthTokenService().registerUser("", "", new IServiceResponse<User>() {
            @Override
            public void onSuccess(User data) {
                Log.i("script", "onSuccess");
                Toast.makeText(LoginActivity.this, "Logado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.i("script", "onError");
                Toast.makeText(LoginActivity.this, "Erro ao logar!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick(R.id.activity_login_button_register)
    public void onClickShowRegisterNewUserActivity(View v) {
        startActivity(new Intent(this, RegisterNewUserActivity.class));
    }
}
