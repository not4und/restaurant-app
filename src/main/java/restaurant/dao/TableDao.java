package restaurant.dao;

import java.util.List;
import java.util.Optional;
import restaurant.model.Table;

public interface TableDao {
    Table add(Table table);

    Table update(Table table);

    Optional<Table> get(Long id);

    List<Table> getAll();

    List<Table> getAllByReservation(boolean reserved);
}
