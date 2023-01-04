package restaurant.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restaurant.dto.request.TableRequestDto;
import restaurant.dto.response.TableResponseDto;
import restaurant.model.Table;
import restaurant.service.TableService;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@RestController
@RequestMapping("/tables")
public class TableController {
    private final TableService tableService;
    private final RequestDtoMapper<TableRequestDto, Table> tableRequestDtoMapper;
    private final ResponseDtoMapper<TableResponseDto, Table> tableResponseDtoMapper;

    public TableController(TableService tableService,
                           RequestDtoMapper<TableRequestDto, Table>
                                   tableRequestDtoMapper,
                           ResponseDtoMapper<TableResponseDto, Table>
                                   tableResponseDtoMapper) {
        this.tableService = tableService;
        this.tableRequestDtoMapper = tableRequestDtoMapper;
        this.tableResponseDtoMapper = tableResponseDtoMapper;
    }

    @GetMapping
    public List<TableResponseDto> getAll() {
        return tableService.getAll().stream()
                .map(tableResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/add")
    public TableResponseDto add(@RequestBody @Valid TableRequestDto requestDto) {
        return tableResponseDtoMapper.mapToDto(tableService
                .add(tableRequestDtoMapper.mapToModel(requestDto)));
    }

    @PutMapping("/update/{id}")
    public TableResponseDto update(@RequestBody @Valid TableRequestDto requestDto,
                                   @PathVariable Long id) {
        Table table = tableRequestDtoMapper.mapToModel(requestDto);
        table.setId(id);
        return tableResponseDtoMapper.mapToDto(tableService.update(table));
    }

    @PutMapping("/reserve/{id}")
    public void reserve(@PathVariable Long id,
                        @RequestParam boolean reserved) {
        Table table = tableService.get(id);
        table.setReserved(reserved);
        tableService.update(table);
    }
}
