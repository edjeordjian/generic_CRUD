package ar.edu.Almacen;

public abstract class Producto 
{
	private int id_producto;
	private String nombre;
	private float precio_compra;
	private float precio_venta;
	private int id_proveedor;
	
	public Producto(int id_producto, String nombre, float precio_compra,
			float precio_venta, int proveedor) 
	{
		this.id_producto = id_producto;
		this.nombre = nombre;
		this.precio_compra = precio_compra;
		this.precio_venta = precio_venta;
		this.id_proveedor = proveedor;
	}

	public int getId_producto() 
	{
		return id_producto;
	}

	public void setId_producto(int id_producto)
	{
		this.id_producto = id_producto;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public float getPrecio_compra()
	{
		return precio_compra;
	}

	public void setPrecio_compra(float precio_compra) 
	{
		this.precio_compra = precio_compra;
	}

	public float getPrecio_venta() 
	{
		return precio_venta;
	}

	public void setPrecio_venta(float precio_venta) 
	{
		this.precio_venta = precio_venta;
	}

	public int getProveedor() 
	{
		return id_proveedor;
	}

	public void setProveedor(int proveedor) 
	{
		this.id_proveedor = proveedor;
	} 
	
	public abstract void setStock(float stock);
	public abstract float getStock();
}
