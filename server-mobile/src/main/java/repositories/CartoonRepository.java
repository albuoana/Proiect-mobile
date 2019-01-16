package repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import model.Cartoon;

@Repository
public interface CartoonRepository extends BaseRepository<Cartoon> {
    List<Cartoon> findAll();
}
