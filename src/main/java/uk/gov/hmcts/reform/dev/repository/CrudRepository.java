package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface CrudRepository<T, ID, S> extends Repository<T, ID> {
    <E extends T> E save(E entity);

    Optional<T> findById(ID id);

    Iterable<T> findAll();

    void deleteById(ID id);

    Optional<T> updateTask(ID id, S status);
}
