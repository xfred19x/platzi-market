package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//se usa esta anotacion "@Mapper" para indicar al proyecto que es un mapeador
//Para integrarlo con sprin se agrega "componentModel = "spring""
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    //Aqui indicamos como queremos hacer nuestro Map
    //Category lo que se expone tendra como input el Entity de Categoria
    // con "@Mappings" contiene todos los mapeos
    @Mappings(value = {
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "description"),
            @Mapping(source = "estado", target = "state")
    })
    Category toCategory(Categoria categoria);

    //Si queremos hacer la inversa del mapeo no es necesario otra vez detallar el mapeo basta con "@InheritInverseConfiguration"
    @InheritInverseConfiguration
    //Como en Categoria tenemos el atributo "productos" debemos ignorlarlo
    @Mapping(target = "productos", ignore = true)
    Categoria toCategoria(Category category);
}