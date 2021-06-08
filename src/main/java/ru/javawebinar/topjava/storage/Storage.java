package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface Storage<T> {

    void delete(int id);

    Meal add(T t);

    T get(int id);

    Collection<T> getAll();
}
