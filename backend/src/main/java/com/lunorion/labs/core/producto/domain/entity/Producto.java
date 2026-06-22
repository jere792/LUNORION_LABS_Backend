package com.lunorion.labs.core.producto.domain.entity;

import com.lunorion.labs.shared.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.UUID;

public class Producto extends BaseEntity {
    private String tenantId;
    private UUID categoriaId;
    private String codigo;
    private String codigoBarra;
    private String nombre;
    private String descripcion;
    private String marca;
    private String modelo;
    private String unidadMedida;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockActual;
    private Integer stockMinimo;
    private String ubicacion;
    private String tipo;
    private boolean activo;

    public Producto() {}

    public Producto(UUID id, String tenantId, UUID categoriaId, String codigo, String nombre) {
        super(id);
        this.tenantId = tenantId;
        this.categoriaId = categoriaId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.activo = true;
    }

    public static Producto create(String tenantId, UUID categoriaId, String codigo, String codigoBarra,
                                  String nombre, String descripcion, String marca, String modelo,
                                  String unidadMedida, BigDecimal precioCompra, BigDecimal precioVenta,
                                  Integer stockActual, Integer stockMinimo, String ubicacion, String tipo) {
        Producto p = new Producto(UUID.randomUUID(), tenantId, categoriaId, codigo, nombre);
        p.codigoBarra = codigoBarra;
        p.descripcion = descripcion;
        p.marca = marca;
        p.modelo = modelo;
        p.unidadMedida = unidadMedida;
        p.precioCompra = precioCompra;
        p.precioVenta = precioVenta;
        p.stockActual = stockActual;
        p.stockMinimo = stockMinimo;
        p.ubicacion = ubicacion;
        p.tipo = tipo;
        return p;
    }

    public void updateStock(Integer stockActual) {
        this.stockActual = stockActual;
        markUpdated();
    }

    public void actualizar(UUID categoriaId, String codigo, String codigoBarra,
                           String nombre, String descripcion, String marca, String modelo,
                           String unidadMedida, java.math.BigDecimal precioCompra,
                           java.math.BigDecimal precioVenta, Integer stockActual,
                           Integer stockMinimo, String ubicacion, String tipo) {
        this.categoriaId = categoriaId;
        this.codigo = codigo;
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.unidadMedida = unidadMedida;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        markUpdated();
    }

    public String getTenantId() { return tenantId; }
    public UUID getCategoriaId() { return categoriaId; }
    public String getCodigo() { return codigo; }
    public String getCodigoBarra() { return codigoBarra; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getUnidadMedida() { return unidadMedida; }
    public BigDecimal getPrecioCompra() { return precioCompra; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public Integer getStockActual() { return stockActual; }
    public Integer getStockMinimo() { return stockMinimo; }
    public String getUbicacion() { return ubicacion; }
    public String getTipo() { return tipo; }
    public boolean isActivo() { return activo; }
}
