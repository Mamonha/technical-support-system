package app.repositories;

import app.entities.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository <Response, Long> {
}
