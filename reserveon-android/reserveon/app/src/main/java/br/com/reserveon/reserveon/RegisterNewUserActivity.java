package br.com.reserveon.reserveon;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.UserService;
import br.com.reserveon.reserveon.tasks.UserRegisterTask;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 17/03/2016.
 */
public class RegisterNewUserActivity extends AppCompatActivity {

    @Bind(R.id.activity_register_new_user_textinputlayout_name)
    TextInputLayout mInputName;
    @Bind(R.id.activity_register_new_user_textinputlayout_email)
    TextInputLayout mInputEmail;
    @Bind(R.id.activity_register_new_user_textinputlayout_password)
    TextInputLayout mInputPassword;
    @Bind(R.id.activity_register_new_user_button_register)
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        ButterKnife.bind(this);

        setupTextInputLayout();
    }

    private void setupTextInputLayout() {
        mInputName.setErrorEnabled(false);
        mInputEmail.setErrorEnabled(false);
        mInputPassword.setErrorEnabled(false);
    }

    private boolean validateFields() {

        boolean isValidName = false;
        boolean isValidEmail = false;
        boolean isValidPassword = false;

        mInputName.setErrorEnabled(false);
        mInputEmail.setErrorEnabled(false);
        mInputPassword.setErrorEnabled(false);

        if (mInputName.getEditText() != null && mInputEmail.getEditText() != null && mInputPassword.getEditText() != null) {
            if (mInputName.getEditText().getText().length() > 0) {
                isValidName = true;
            } else {
                mInputName.setErrorEnabled(true);
                mInputName.setError(getString(R.string.activity_register_new_user_validation_name_text));
            }

            if (mInputEmail.getEditText().getText().length() > 0) {
                isValidEmail = true;
            } else {
                mInputEmail.setErrorEnabled(true);
                mInputEmail.setError(getString(R.string.activity_register_new_user_validation_email_text));
            }

            if (mInputPassword.getEditText().getText().length() > 0) {
                isValidPassword = true;
            } else {
                mInputPassword.setErrorEnabled(true);
                mInputPassword.setError(getString(R.string.activity_register_new_user_validation_password_text));
            }
        }

        return isValidName && isValidEmail && isValidPassword;
    }

    @OnClick(R.id.activity_register_new_user_button_register)
    public void onClickRegisterUser() {
        if (validateFields()) {

            User user = new User();
            user.setName(mInputName.getEditText().getText().toString());
            user.setEmail(mInputEmail.getEditText().getText().toString());
            user.setPassword(mInputPassword.getEditText().getText().toString());
            user.setProfileId(1);

            /*new UserRegisterTask(user, new IServiceResponse<User>() {
                @Override
                public void onSuccess(User data) {

                }

                @Override
                public void onError(String error) {

                }
            }).execute();*/

            new UserService().registerUser(user, new IServiceResponse<User>() {
                @Override
                public void onSuccess(User data) {

                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }
}
