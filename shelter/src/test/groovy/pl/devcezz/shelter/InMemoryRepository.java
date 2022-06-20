package pl.devcezz.shelter;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

public abstract class InMemoryRepository<T, ID> {

    private static final String ENTITIES_MUST_NOT_BE_NULL = "Entities must not be null";
    private static final String ENTITY_MUST_NOT_BE_NULL = "Entity must not be null";
    private static final String IDS_MUST_NOT_BE_NULL = "Passed ids must not be null";
    private static final String ID_MUST_NOT_BE_NULL = "Passed id must not be null";
    private static final String ENTITY_OF_GIVEN_ID_NOT_EXISTS = "Entity of id %s does not exists";

    protected final Map<ID, T> entities = new ConcurrentHashMap<>();

    public abstract <S extends T> S save(S entity);

    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        Assert.notNull(entities, ENTITIES_MUST_NOT_BE_NULL);

        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::save)
                .toList();
    }

    public Optional<T> findById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        return Optional.ofNullable(entities.get(id));
    }

    public boolean existsById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        return findById(id).isPresent();
    }

    public Iterable<T> findAll() {
        return entities.values()
                .stream()
                .toList();
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        Assert.notNull(ids, IDS_MUST_NOT_BE_NULL);

        return StreamSupport.stream(ids.spliterator(), false)
                .filter(entities::containsKey)
                .map(entities::get)
                .toList();
    }

    public long count() {
        return entities.size();
    }

    public void deleteById(ID id) {
        Assert.notNull(id, ID_MUST_NOT_BE_NULL);
        delete(findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format(ENTITY_OF_GIVEN_ID_NOT_EXISTS, id), 1)));
    }

    public void delete(T entity) {
        Assert.notNull(entity, ENTITY_MUST_NOT_BE_NULL);
        entities.values()
                .remove(entity);
    }

    public void deleteAllById(Iterable<? extends ID> ids) {
        Assert.notNull(ids, IDS_MUST_NOT_BE_NULL);
        ids.forEach(this::deleteById);
    }

    public void deleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, ENTITIES_MUST_NOT_BE_NULL);
        entities.forEach(this::delete);
    }

    public void deleteAll() {
        entities.clear();
    }
}
