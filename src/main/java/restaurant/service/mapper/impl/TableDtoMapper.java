package restaurant.service.mapper.impl;

import org.springframework.stereotype.Component;
import restaurant.dto.request.TableRequestDto;
import restaurant.dto.response.TableResponseDto;
import restaurant.model.Table;
import restaurant.service.mapper.RequestDtoMapper;
import restaurant.service.mapper.ResponseDtoMapper;

@Component
public class TableDtoMapper implements RequestDtoMapper<TableRequestDto, Table>,
        ResponseDtoMapper<TableResponseDto, Table> {
    @Override
    public Table mapToModel(TableRequestDto dto) {
        return new Table(dto.getSeats(), dto.isReserved());
    }

    @Override
    public TableResponseDto mapToDto(Table table) {
        return new TableResponseDto(
                table.getId(),
                table.getSeats(),
                table.isReserved()
        );
    }
}
