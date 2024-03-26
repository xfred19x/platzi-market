package com.platzi.market.persistence.crud;

import com.platzi.market.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//Se especifica a la interfase que extienda de CrudRepository
//donde  hacemos referencia la tabla y el tipo de la clave primaria "Integer" para hacer las operaciones
public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    //creamos un Query Metodo siguiendo el estandar de su nomenclatura para tener una mejor flexibilidad
    //buscara los productos "findBy" por el Id de Categoria "IDcategoria" y lo ordenara ascedente por el nombre "rderByNombreAsc"
    //recuerda que los parametros de los metodos tienen que tener el mismo nombre de los atributos definidos en los Entity
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    //crearemos un Query Metodo
    //Buscara los productos "findBy" que tenga menor cantidad al ingresado "CantidadStockLessThan" y que tenga un estado "AndEstado"
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}