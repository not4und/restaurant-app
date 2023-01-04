package restaurant.service;

import java.util.List;
import restaurant.model.Table;

public interface TableService {
    Table add(Table table);

    Table update(Table table);

    Table get(Long id);

    List<Table> getAll();

    List<Table> getAllByReservation(boolean reserved);
}
