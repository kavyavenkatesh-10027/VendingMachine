package repository;

import util.VendingMachineException;

import java.util.*;

public abstract class BaseRepository<T> {

    protected final Map<String, T> store = new HashMap<>();

    protected abstract String getId(T entity);

    public void add(T entity) {
        if (existsById(getId(entity))){
            throw new VendingMachineException("Entity is either null or already exists in the system");
        }
        store.put(getId(entity), entity);
    }

    public T findById(String id) {
        if (!existsById(id)) {
            throw new VendingMachineException("Entity of Id: "+ id + "is either null or does not exists");
        }
        return store.get(id);
    }

    public Set<T> findAll() {
        return Collections.unmodifiableSet(new HashSet<>(store.values()));
    }

    public void removeById(String id) {
        if (!existsById(id)){
            throw new VendingMachineException("Entity of Id: "+ id + "is either null or does not exists");
        }
        store.remove(id);
    }

    public boolean existsById(String id) {
        return id != null && store.containsKey(id);
    }
}
