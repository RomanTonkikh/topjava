package ru.javawebinar.topjava.storage;

import java.util.Collection;

public interface Storage<T> {

    void delete(int id);

    T save(T t);

    T get(int id);

    Collection<T> getAll();
}
