package de.appsfactory.mvp.preferences;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Collider on 24.06.2017.
 */

public class BasePreference<T> implements Preference<T> {

    final CopyOnWriteArraySet<Observer<T>> observers = new CopyOnWriteArraySet<>();
    final AtomicReference<T> value = new AtomicReference<>();

    @Override
    public T get() {
        return value.get();
    }

    @Override
    public void set(T value) {
        this.value.set(value);
    }

    @Override
    public void observe(Observer<T> observer) {
        observers.add(observer);
    }

    @Override
    public void disconnect(Observer<T> observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer<T> observer : observers) {
            observer.call(this, get());
        }
    }

}
