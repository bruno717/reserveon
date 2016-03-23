package br.com.reserveon.reserveon.tasks;

import android.os.AsyncTask;

import br.com.reserveon.reserveon.interfaces.IServiceResponse;
import br.com.reserveon.reserveon.models.User;
import br.com.reserveon.reserveon.rest.UserService;

/**
 * Created by Bruno on 22/03/2016.
 */
public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

    private User mUser;
    private IServiceResponse<User> mCallback;

    public UserRegisterTask(User user, IServiceResponse<User> callback) {
        mUser = user;
        mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            mUser = new UserService().registerUser(mUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);

        if (mCallback != null) {
            if (isSuccess)
                mCallback.onSuccess(mUser);
            else
                mCallback.onError("Erro");
        }
    }
}
