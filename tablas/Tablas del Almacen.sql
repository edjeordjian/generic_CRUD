use Almacen

create table Proveedores(
id_proveedor smallint identity(1,1),
nombre varchar(50),
telefono varchar(25),
calleDomicilio varchar(25),
alturaDomicilio smallint,
pisoDomicilio tinyint,
departamentoDomicilio varchar(25),
especialidad varchar(50))

create table Compras(
id_compra int identity(1,1) ,
fecha varchar(10),
gasto money
 )

create table Ventas(
id_venta int identity(1,1) ,
fecha varchar(10),
recaudacion money)

create table Productos(
id_producto int identity(1,1),
nombre varchar(50),
precio_compra money,
precio_venta money,
stock smallint,
tipo_stock varchar(2),
id_proveedor smallint )

create table ProductosXcompra(
id_productosXcompra int identity(1,1),
id_compra int ,
id_producto int,
cantidad int,
precio_parcial money)

create table ProductosXventa(
id_productosXventa int identity(1,1) ,
id_venta int ,
id_producto int ,
cantidad int,
precio_parcial money)

