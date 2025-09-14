package cardoso.commerce.app.repository;

import cardoso.commerce.app.entity.Basket;
import cardoso.commerce.app.entity.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BasketRepository extends MongoRepository<Basket, String> {

    Optional<Basket> findByClientIdAndStatus(Long clientId, Status status);
}