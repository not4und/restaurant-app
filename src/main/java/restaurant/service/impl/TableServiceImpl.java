package restaurant.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import restaurant.dao.TableDao;
import restaurant.model.Table;
import restaurant.service.TableService;

@Service
public class TableServiceImpl implements TableService {
    private final TableDao tableDao;

    public TableServiceImpl(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    @Override
    public Table add(Table table) {
        return tableDao.add(table);
    }

    @Override
    public Table update(Table table) {
        return tableDao.update(table);
    }

    @Override
    public Table get(Long id) {
        return tableDao.get(id).orElseThrow(
                () -> new RuntimeException("Table with id: " + id + " not found.")
        );
    }

    @Override
    public List<Table> getAll() {
        return tableDao.getAll();
    }

    @Override
    public List<Table> getAllByReservation(boolean reserved) {
        return tableDao.getAllByReservation(reserved);
    }
}
