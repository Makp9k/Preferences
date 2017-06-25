package de.appsfactory.preferences;

/**
 * Created by Admin on 19.06.2017.
 */

public interface Preference<T> {

    T get();

    void set(T value);

    Connection<T> observe(Observer<T> observer);

    void disconnect(Observer<T> observer);

    interface Observer<T> {
        void call(Preference<T> preference, T value);
    }

    final class Connection<T> {
        private final Preference<T> preference;
        private final Observer<T> observer;

        public Connection(Preference<T> preference, Observer<T> observer) {
            this.preference = preference;
            this.observer = observer;
        }

        public void dispose() {
            preference.disconnect(observer);
        }

        public Preference<T> getPreference() {
            return preference;
        }

        public Observer<T> getObserver() {
            return observer;
        }
    }

}
