package restaurant.service.mapper;

public interface RequestDtoMapper<D, M> {
    M mapToModel(D dto);
}
