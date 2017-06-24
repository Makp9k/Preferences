package de.appsfactory.mvp.preferences;

/**
 * Created by Admin on 19.06.2017.
 */

public interface Preference<T> {

    T get();

    void set(T value);

    void observe(Observer<T> observer);

    void disconnect(Observer<T> observer);

    interface Observer<T> {
        void call(Preference<T> preference, T value);
    }

}
