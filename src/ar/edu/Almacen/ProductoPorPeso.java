package ar.edu.Almacen;

public class ProductoPorPeso extends Producto
{
	private float peso;

	public ProductoPorPeso(int id_producto, String nombre, float precio_compra,
			float precio_venta, int id_proveedor, float peso)
	{
		super(id_producto, nombre, precio_compra, precio_venta, id_proveedor);
		this.peso = peso;
	}

	public float getStock() 
	{
		return peso;
	}

	public void setStock(float peso) 
	{
		this.peso = peso;
	} 
	
}
