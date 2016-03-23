package br.com.reserveon.reserveon.interfaces;

/**
 * Created by Bruno on 20/03/2016.
 */
public interface IServiceResponse<T> {
    void onSuccess(T data);
    void onError(String error);
}
