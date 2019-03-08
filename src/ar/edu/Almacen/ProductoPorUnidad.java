package ar.edu.Almacen;

public class ProductoPorUnidad extends Producto
{
	private float stock;
	
	public ProductoPorUnidad(int id_producto, String nombre,
			float precio_compra, float precio_venta, int id_proveedor,
			float f) 
	{
		super(id_producto, nombre, precio_compra, precio_venta, id_proveedor);
		this.stock = f;
	}

	public float getStock() 
	{
		return stock;
	}

	public void setStock(float stock) 
	{
		this.stock = stock;
	}

	
}
