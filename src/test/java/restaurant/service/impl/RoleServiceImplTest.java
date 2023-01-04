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
import restaurant.dao.RoleDao;
import restaurant.model.Role;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    private static final Long ID = 1L;
    @InjectMocks
    private RoleServiceImpl roleService;
    @Mock
    private RoleDao roleDao;
    private Role role;

    @BeforeEach
    void setUp() {
        role = createRole(ID, Role.RoleName.USER);
    }

    @Test
    void add_ok() {
        Role inputRole = createRole(null, Role.RoleName.USER);
        Mockito.when(roleDao.add(inputRole)).thenReturn(role);
        Assertions.assertEquals(role, roleService.add(inputRole));
    }

    @Test
    void getAll_ok() {
        List<Role> roles = List.of(role, createRole(ID + 1, Role.RoleName.ADMIN));
        Mockito.when(roleDao.getAll()).thenReturn(roles);
        Assertions.assertEquals(roles, roleService.getAll());
    }

    @Test
    void getByName_ok() {
        Mockito.when(roleDao.getByName(Role.RoleName.USER)).thenReturn(Optional.of(role));
        Assertions.assertEquals(role, roleService.getByName(Role.RoleName.USER));
    }

    @Test
    void getByName_notFound_notOk() {
        Mockito.when(roleDao.getByName(Role.RoleName.USER)).thenReturn(Optional.empty());
        Assertions.assertEquals("Role with name: " + Role.RoleName.USER + " not found.",
                Assertions.assertThrows(RuntimeException.class,
                        () -> roleService.getByName(Role.RoleName.USER)).getMessage());
    }

    private Role createRole(Long id, Role.RoleName name) {
        Role newRole = new Role(name);
        newRole.setId(id);
        return newRole;
    }
}
