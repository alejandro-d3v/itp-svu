package co.edu.itp.svu.repository;

import co.edu.itp.svu.domain.Oficina;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Oficina entity.
 */
@Repository
public interface OficinaRepository extends MongoRepository<Oficina, String> {}
