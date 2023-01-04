package restaurant.dao.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.dao.TableDao;
import restaurant.model.Table;

class TableDaoImplTest extends AbstractTest {
    private TableDao tableDao;
    private Table table;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Table.class};
    }

    @BeforeEach
    void setUp() {
        tableDao = new TableDaoImpl(getSessionFactory());
        table = new Table(6, false);
    }

    @Test
    void add_ok() {
        Table actual = tableDao.add(table);
        Assertions.assertNotNull(table);
        Assertions.assertNotNull(table.getId());
        Assertions.assertEquals(table, actual);
    }

    @Test
    void update_ok() {
        tableDao.add(table);
        table.setReserved(true);
        Table actual = tableDao.update(table);
        Assertions.assertNotNull(table);
        Assertions.assertEquals(table, actual);
    }

    @Test
    void get_ok() {
        tableDao.add(table);
        Optional<Table> actual = tableDao.get(1L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(table, actual.get());
    }

    @Test
    void getAll_ok() {
        getTableListTest(addTables(), tableDao.getAll());
    }

    @Test
    void getAllByReservation_ok() {
        getTableListTest(addTables(), tableDao.getAllByReservation(false));
    }

    private Table addTables() {
        tableDao.add(table);
        return tableDao.add(new Table(3, false));
    }

    private void getTableListTest(Table table1, List<Table> actual) {
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertTrue(actual.containsAll(List.of(table, table1)));
    }
}
