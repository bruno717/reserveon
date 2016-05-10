package br.com.reserveon.reserveon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.reserveon.reserveon.enums.PerfisUser;
import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.models.managers.ValidateFieldsManager;
import br.com.reserveon.reserveon.rest.AuthTokenService;
import br.com.reserveon.reserveon.rest.UserService;
import br.com.reserveon.reserveon.utils.ConnectionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 17/03/2016.
 */
public class RegisterNewUserActivity extends AppCompatActivity {

    @BindView(R.id.activity_register_new_user_textinputlayout_name)
    TextInputLayout mInputName;
    @BindView(R.id.activity_register_new_user_textinputlayout_email)
    TextInputLayout mInputEmail;
    @BindView(R.id.activity_register_new_user_textinputlayout_password)
    TextInputLayout mInputPassword;
    @BindView(R.id.activity_register_new_user_button_register)
    Button mButtonRegister;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        ButterKnife.bind(this);
    }

    private void setupTextInputLayout() {
        mInputName.setErrorEnabled(false);
        mInputName.setError(null);
        mInputEmail.setErrorEnabled(false);
        mInputEmail.setError(null);
        mInputPassword.setErrorEnabled(false);
        mInputPassword.setError(null);
    }

    private boolean validateFields() {

        boolean isValidName = false;
        boolean isValidEmail = false;
        boolean isValidPassword = false;

        mInputName.setErrorEnabled(false);
        mInputEmail.setErrorEnabled(false);
        mInputPassword.setErrorEnabled(false);

        if (mInputName.getEditText() != null && mInputEmail.getEditText() != null && mInputPassword.getEditText() != null) {
            String password = mInputPassword.getEditText().getText().toString();

            if (mInputName.getEditText().getText().length() > 0) {
                isValidName = true;
            } else {
                mInputName.setErrorEnabled(true);
                mInputName.setError(getString(R.string.activity_register_new_user_validation_name_text));
            }

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
        }

        return isValidName && isValidEmail && isValidPassword;
    }

    private MaterialDialog showModalProgress() {
        return new MaterialDialog.Builder(this)
                .autoDismiss(false)
                .cancelable(false)
                .title(R.string.progress_dialog_title)
                .content(R.string.activity_register_new_user_progress_dialog_description)
                .progress(true, 0)
                .show();
    }

    @OnClick(R.id.activity_register_new_user_button_register)
    public void onClickRegisterUser() {

        setupTextInputLayout();

        if (ConnectionUtils.isConnected(this)) {
            if (validateFields()) {

                mMaterialDialog = showModalProgress();

                final User user = new User();
                user.setName(mInputName.getEditText().getText().toString());
                user.setEmail(mInputEmail.getEditText().getText().toString());
                user.setPassword(mInputPassword.getEditText().getText().toString());
                user.setProfileId(PerfisUser.USER_CLIENT.getPerfilCod());

                new UserService().registerUser(user, new IServiceResponse<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        mMaterialDialog.setContent(R.string.activity_login_dialog_message_auth_user_description_authenticating);
                        requestAuthUser(user.getEmail(), user.getPassword());
                    }

                    @Override
                    public void onError(String error) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(RegisterNewUserActivity.this, R.string.activity_register_new_user_toast_error_register_user, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            new MaterialDialog.Builder(this)
                    .autoDismiss(false)
                    .cancelable(false)
                    .title(R.string.dialog_without_connection_internet_title)
                    .content(R.string.dialog_without_connection_internet_description)
                    .positiveText(R.string.dialog_positive)
                    .show();
        }
    }

    private void requestAuthUser(String email, String password) {

        new AuthTokenService().authUser(email, password, new IServiceResponse<User>() {
            @Override
            public void onSuccess(User user) {
                mMaterialDialog.setContent(R.string.activity_login_dialog_message_auth_user_description_load_info);
                requestGetUserAuth(user.getAccessToken());
            }

            @Override
            public void onError(String error) {
                mMaterialDialog.dismiss();
                new MaterialDialog.Builder(RegisterNewUserActivity.this)
                        .title(R.string.activity_login_dialog_message_error_auth_title)
                        .content(R.string.activity_login_dialog_message_error_auth_description)
                        .positiveText(R.string.dialog_positive)
                        .show();
            }
        });

    }

    private void requestGetUserAuth(String token) {

        new UserService().getUserAuth(token, new IServiceResponse<User>() {
            @Override
            public void onSuccess(User user) {
                mMaterialDialog.dismiss();
                startActivity(new Intent(RegisterNewUserActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            public void onError(String error) {
                User.deleteAll(User.class);
                mMaterialDialog.dismiss();
                new MaterialDialog.Builder(RegisterNewUserActivity.this)
                        .title(R.string.activity_login_dialog_message_error_auth_title)
                        .content(R.string.activity_login_dialog_message_error_auth_description)
                        .positiveText(R.string.dialog_positive)
                        .show();
            }
        });
    }
}
