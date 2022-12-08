package services;

import org.bson.types.ObjectId;

public abstract class ServiceDec<T> {

    public abstract void add(T t);

    public abstract T get(ObjectId id);

    public abstract void delete(Object id);

    public abstract void update(T t);
}
