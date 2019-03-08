package ar.edu.Almacen;

public class Proveedor 
{
	private int id_proveedor;
	private String nombre;
	private String telefono; 
	private String calle_domicilio;
	private int altura_domicilio;
	private int piso_domicilio;
	private String departamento_domicilio;
	private String especialidad;
	
	public Proveedor(int id_proveedor, String nombre, String telefono,
			String calle_domicilio, int altura_domicilio, int piso_domicilio,
			String departamento_domicilio, String especialidad) 
	{
		this.id_proveedor = id_proveedor;
		this.nombre = nombre;
		this.telefono = telefono;
		this.calle_domicilio = calle_domicilio;
		this.altura_domicilio = altura_domicilio;
		this.piso_domicilio = piso_domicilio;
		this.departamento_domicilio = departamento_domicilio;
		this.especialidad = especialidad;
	}

	public int getId_proveedor() 
	{
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor)
	{
		this.id_proveedor = id_proveedor;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getCalle_domicilio()
	{
		return calle_domicilio;
	}

	public void setCalle_domicilio(String calle_domicilio)
	{
		this.calle_domicilio = calle_domicilio;
	}

	public int getAltura_domicilio() 
	{
		return altura_domicilio;
	}

	public void setAltura_domicilio(int altura_domicilio)
	{
		this.altura_domicilio = altura_domicilio;
	}

	public int getPiso_domicilio() 
	{
		return piso_domicilio;
	}

	public void setPiso_domicilio(int piso_domicilio)
	{
		this.piso_domicilio = piso_domicilio;
	}

	public String getDepartamento_domicilio()
	{
		return departamento_domicilio;
	}

	public void setDepartamento_domicilio(String departamento_domicilio)
	{
		this.departamento_domicilio = departamento_domicilio;
	}

	public String getEspecialidad() 
	{
		return especialidad;
	}

	public void setEspecialidad(String especialidad) 
	{
		this.especialidad = especialidad;
	}

}
