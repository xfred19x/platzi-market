package com.platzi.market.persistence.entity;

import jakarta.persistence.*;

//Define que es una entidad de una BD
@Entity
//dado que el nombre de la clase es diferente al de la tabla de la BD se hace la referencia
@Table(name = "productos")
public class Producto {

    //se debe poner para indicar que es la clave primaria
    @Id
    //dado que el nombre de l atributo es diferente al de la columna de la BD se hace la referencia
    //Se le ap´lica la estrategia para que java autogenere esta clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    //Como es la clave primaria cada vez que se cree un nuevo producto
    private Integer idProducto;

    private String nombre;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "cantidad_stock")
    private Integer cantidadStock;

    private Boolean estado;

    //aqui escribiremos la relacion entre categoria y producto
    //se agrega la etiqueta "ManyToOne" porque para varios productos hay una categoria
    @ManyToOne
    //se hace referencia que la relacion entr eproducto y categoria
    //se indica que la columna "id_categoria" es el campo en relacion
    //se le agrega los atributos de no insertar y actualizar
    //se agrega esto para que cualquier cambio se haga en Categoria y no Producto
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
