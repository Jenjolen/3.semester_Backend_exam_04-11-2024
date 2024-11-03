package dat.daos;

import java.util.List;

public interface iDAO <T, I> {
    T read(I i);
    List<T> readAll();
    T create(T t);
    T update(I i, T t);

}
