package br.com.reserveon.reserveon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.models.managers.ValidateFieldsManager;
import br.com.reserveon.reserveon.rest.AuthTokenService;
import br.com.reserveon.reserveon.rest.UserService;
import br.com.reserveon.reserveon.utils.ConnectionUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 11/04/2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.activity_login_textinputlayout_email)
    TextInputLayout mInputEmail;
    @Bind(R.id.activity_login_textinputlayout_password)
    TextInputLayout mInputPassword;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(view);
        ButterKnife.bind(this);
    }

    private void setupTextInputLayout() {
        mInputEmail.setErrorEnabled(false);
        mInputEmail.setError(null);
        mInputPassword.setErrorEnabled(false);
        mInputPassword.setError(null);
    }

    private boolean validateFields() {

        boolean isValidEmail = false;
        boolean isValidPassword = false;

        String password = mInputPassword.getEditText().getText().toString();

        if (mInputEmail.getEditText().getText().length() > 0 && Patterns.EMAIL_ADDRESS.matcher(mInputEmail.getEditText().getText()).matches()) {
            isValidEmail = true;
        } else {
            mInputEmail.setErrorEnabled(true);
            mInputEmail.setError(getString(R.string.activity_register_new_user_validation_email_text));
        }
        if (password.length() > 0) {

            isValidPassword = ValidateFieldsManager.validatePassword(password);
            if (!isValidPassword) {
                mInputPassword.setErrorEnabled(true);
                mInputPassword.setError(getString(R.string.activity_register_new_user_validation_format_default_password_text));
            }
        } else {
            mInputPassword.setErrorEnabled(true);
            mInputPassword.setError(getString(R.string.activity_register_new_user_validation_password_text));
        }

        return isValidEmail && isValidPassword;
    }

    @OnClick(R.id.activity_login_button_enter)
    public void onClickAuthUser(View v) {

        setupTextInputLayout();

        if (ConnectionUtils.isConnected(this)) {
            if (validateFields()) {

                mMaterialDialog = showModalProgress();

                String email = mInputEmail.getEditText() != null ? mInputEmail.getEditText().getText().toString() : "";
                String password = mInputPassword.getEditText() != null ? mInputPassword.getEditText().getText().toString() : "";

                new AuthTokenService().authUser(email, password, new IServiceResponse<User>() {
                    @Override
                    public void onSuccess(User user) {
                        mMaterialDialog.setContent(R.string.activity_login_dialog_message_auth_user_description_load_info);
                        requestGetUserAuth(user.getAccessToken());
                    }

                    @Override
                    public void onError(String error) {
                        mMaterialDialog.dismiss();
                        new MaterialDialog.Builder(LoginActivity.this)
                                .title(R.string.activity_login_dialog_message_error_auth_title)
                                .content(R.string.activity_login_dialog_message_error_auth_description)
                                .positiveText(R.string.dialog_positive)
                                .show();
                    }
                });
            }
        }
    }

    private void requestGetUserAuth(String token) {

        new UserService().getUserAuth(token, new IServiceResponse<User>() {
            @Override
            public void onSuccess(User user) {
                mMaterialDialog.dismiss();
                showMainActivity();
            }

            @Override
            public void onError(String error) {
                User.deleteAll(User.class);
                mMaterialDialog.dismiss();
                new MaterialDialog.Builder(LoginActivity.this)
                        .title(R.string.activity_login_dialog_message_error_auth_title)
                        .content(R.string.activity_login_dialog_message_error_auth_description)
                        .positiveText(R.string.dialog_positive)
                        .show();
            }
        });
    }

    private void showMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    private MaterialDialog showModalProgress() {
        return new MaterialDialog.Builder(this)
                .autoDismiss(false)
                .cancelable(false)
                .title(R.string.progress_dialog_title)
                .content(R.string.activity_login_dialog_message_auth_user_description_authenticating)
                .progress(true, 0)
                .show();
    }

    @OnClick(R.id.activity_login_button_register)
    public void onClickShowRegisterNewUserActivity(View v) {
        startActivity(new Intent(this, RegisterNewUserActivity.class));
    }
}
