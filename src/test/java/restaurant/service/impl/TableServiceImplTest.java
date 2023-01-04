package restaurant.service.impl;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import restaurant.dao.TableDao;
import restaurant.model.Table;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {
    private static final Long ID = 1L;
    private static final boolean RESERVED = false;
    @InjectMocks
    private TableServiceImpl tableService;
    @Mock
    private TableDao tableDao;
    private Table table;

    @BeforeEach
    void setUp() {
        table = createTable(ID, RESERVED);
    }

    @Test
    void add_ok() {
        Table inputTable = createTable(null, RESERVED);
        Mockito.when(tableDao.add(inputTable)).thenReturn(table);
        Assertions.assertEquals(table, tableService.add(inputTable));
    }

    @Test
    void update_ok() {
        table.setReserved(!RESERVED);
        Table inputTable = createTable(ID, !RESERVED);
        Mockito.when(tableDao.update(inputTable)).thenReturn(table);
        Assertions.assertEquals(table, tableService.update(inputTable));
    }

    @Test
    void get_ok() {
        Mockito.when(tableDao.get(ID)).thenReturn(Optional.of(table));
        Assertions.assertEquals(table, tableService.get(ID));
    }

    @Test
    void get_notFound_notOK() {
        Mockito.when(tableDao.get(ID)).thenReturn(Optional.empty());
        Assertions.assertEquals("Table with id: " + ID + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> tableService.get(ID)).getMessage());
    }

    @Test
    void getAll_ok() {
        List<Table> tables = List.of(table, createTable(ID + 1, RESERVED));
        Mockito.when(tableDao.getAll()).thenReturn(tables);
        Assertions.assertEquals(tables, tableService.getAll());
    }

    @Test
    void getAllByReservation_ok() {
        List<Table> tables = List.of(table, createTable(ID + 1, RESERVED));
        Mockito.when(tableDao.getAllByReservation(RESERVED)).thenReturn(tables);
        Assertions.assertEquals(tables, tableService.getAllByReservation(RESERVED));
    }

    private Table createTable(Long id, boolean reserved) {
        Table newTable = new Table(6, reserved);
        newTable.setId(ID);
        return newTable;
    }
}
