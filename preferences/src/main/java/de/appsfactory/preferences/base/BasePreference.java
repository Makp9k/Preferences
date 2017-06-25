package de.appsfactory.preferences.base;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import de.appsfactory.preferences.Preference;

/**
 * Created by Collider on 24.06.2017.
 */

public class BasePreference<T> implements Preference<T> {

    final CopyOnWriteArraySet<Observer<T>> observers = new CopyOnWriteArraySet<>();

    protected final AtomicReference<T> value = new AtomicReference<>();

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public void set(T value) {
        T oldValue = get();
        this.value.set(value);
        if (oldValue != value) {
            notifyObservers(value);
        }
    }

    @Override
    public Connection<T> observe(Observer<T> observer) {
        observers.add(observer);
        observer.call(this, get());
        return new Connection<>(this, observer);
    }

    @Override
    public void disconnect(Observer<T> observer) {
        observers.remove(observer);
    }

    private void notifyObservers(T value) {
        for (Observer<T> observer : observers) {
            observer.call(this, value);
        }
    }

}
