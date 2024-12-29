package mapper;

public interface Mapper<E, D> {
    D toDto(E entity); // Конвертує з Entity до DTO
    E toEntity(D dto); // Конвертує з DTO до Entity
}