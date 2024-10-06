package co.edu.itp.svu.repository;

import co.edu.itp.svu.domain.Pqrs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Pqrs entity.
 */
@Repository
public interface PqrsRepository extends MongoRepository<Pqrs, String> {}
