package dat.daos;

import java.util.List;

public interface iDAO <DTO, I> { // jeg forstod det med at returtypen skulle være DTO således - nu hvor det skulle være generic
    DTO getById(I i);
    List<DTO> getAll();
    DTO create(DTO dto);
    DTO update(I i, DTO dto);
    DTO delete(I i);

}
