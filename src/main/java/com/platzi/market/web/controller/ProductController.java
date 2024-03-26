package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//con "@RestController" Indicamos a Spring que esta clase es un control de un API Rest
@RestController
//con "@RequestMapping("/products")" indicamos con que pat accederemos
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //se usa "@GetMapping" para exponer nuestro recurso de tipo get ya que obtenemos informacion
    @GetMapping("/all")
    @Operation(summary = "Get all supermarket products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    })
    //Usaremos ResponseEntity para controlar los llamados y respuestas de nuestros servicios
    //aqui definimos lo que va retornar en este caso una Lista de Productos
    public ResponseEntity<List<Product>> getAll() {
        //Aqui debemos agregar una nueva instancia de ResponseEntity, donde enviaremos el HTTPSTATUS
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    //aqui se le agrega el path variable
    @GetMapping("/{id}")
    @Operation(summary = "Search a product with an ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Unauthorized"))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Forbidden"))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Product not found"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "Error interno del servidor")))
    })
    //en los parametros de entrada se agrega "@PathVariable("productId")" para hacer referencia la variable a usar en el metodo
    //aqui al usar ResponseEntity ya no es necesario definir la respuesta con "Optional<Product>"
    public ResponseEntity<Product> getProduct(@Parameter(description = "The id of the product", required = true, schema = @Schema(implementation = Integer.class)) @PathVariable("id") int productId) {
        //dado que el servicio nos retorna un objeto tipo Optional usaremos el map
        //podemos enviar diferentes respuestas en cabecera si existe o no
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //se usa "@PostMapping" para exponer nuestro recurso de tipo post ya que crearemos o inicializamos un flujo
    @PostMapping("/save")
    //con "" indicamos que el dominio Producto es la estructura JSON que espera el servicio para ejecutarse
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    //se usa "@DeleteMapping" para exponer nuestro recurso de tipo delete ya que eliminaremos
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int productId) {
        boolean deleted = productService.delete(productId);
        return deleted ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }
}