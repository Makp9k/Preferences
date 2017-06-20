package de.appsfactory.mvp.preferences;

/**
 * Created by Admin on 19.06.2017.
 */

public interface Preference<T> {

    T get();

    void set(T value);

    void register(Observer<T> observer);

    void unregister(Observer<T> observer);

    interface Observer<T> {
        void call(Preference<T> preference, T value);
    }

}
