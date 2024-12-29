package mapper;

import java.util.function.Function;

public abstract class AbstractMapper<E, D> implements Mapper<E, D> {
    private final Function<E, D> toDtoFunction;
    private final Function<D, E> toEntityFunction;

    public AbstractMapper(Function<E, D> toDtoFunction, Function<D, E> toEntityFunction) {
        this.toDtoFunction = toDtoFunction;
        this.toEntityFunction = toEntityFunction;
    }

    @Override
    public D toDto(E entity) {
        return toDtoFunction.apply(entity);
    }

    @Override
    public E toEntity(D dto) {
        return toEntityFunction.apply(dto);
    }
}