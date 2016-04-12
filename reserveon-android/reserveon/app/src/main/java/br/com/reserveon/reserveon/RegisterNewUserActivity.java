package br.com.reserveon.reserveon;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.UserService;
import br.com.reserveon.reserveon.utils.ConnectionUtils;
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

        boolean isCaracterUpper = false;
        boolean isCaracterLower = false;
        boolean isCaracterNumber = false;
        boolean isCaracterSpecial = false;
        boolean isSizeMinDefault = false;

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

                if (password.length() > 5) {
                    isSizeMinDefault = true;
                }

                for (int i = 0; i < password.length(); i++) {
                    Character caractere = password.charAt(i);

                    if (Character.isUpperCase(caractere)) {
                        isCaracterUpper = true;
                    } else if (Character.isLowerCase(caractere)) {
                        isCaracterLower = true;
                    } else if (caractere.toString().matches("[0-9]")) {
                        isCaracterNumber = true;
                    } else if (caractere.toString().matches("\\W")) {
                        isCaracterSpecial = true;
                    }
                }
                isValidPassword = isCaracterUpper && isCaracterLower && isCaracterNumber && isCaracterSpecial && isSizeMinDefault;

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

                final MaterialDialog materialDialog = showModalProgress();

                User user = new User();
                user.setName(mInputName.getEditText().getText().toString());
                user.setEmail(mInputEmail.getEditText().getText().toString());
                user.setPassword(mInputPassword.getEditText().getText().toString());
                user.setProfileId(1);

                new UserService().registerUser(user, new IServiceResponse<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        materialDialog.dismiss();
                        Toast.makeText(RegisterNewUserActivity.this, R.string.activity_register_new_user_toast_register_user, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        materialDialog.dismiss();
                        Toast.makeText(RegisterNewUserActivity.this, R.string.activity_register_new_user_toast_error_register_user, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            new MaterialDialog.Builder(this)
                    .autoDismiss(false)
                    .cancelable(false)
                    .title(R.string.dialog_without_connection_internet_title)
                    .content(R.string.dialog_without_connection_internet_description)
                    .positiveText(R.string.dialog_positive)
                    .show();
        }
    }
}
