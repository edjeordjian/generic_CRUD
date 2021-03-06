use Almacen

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Fiambres Daniel', '4687-9344','Coronel Cardenas', 1981,3,null,'Fiambres')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Lacteos Barraza','4568-8263' ,'Concordia', 1344,null,null,'Lacteos')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('La Serenisima','4190-5566' ,'Piedras', 5043,null,null,'Lacteos')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Distribuidora Punta del Sol','4451-8961' ,'Directorio', 3109,2,'B','Quesos')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Fanacoa','4227-0231' ,'Murgiondo', 6149,6,null,'Aderezos')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Don Satur','4065-6521' ,'Chascomus', 144,2,'C','Bizcochos y Galletitas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Susante S.A.','3521-9897' ,'Yerbal', 1201,4,'Depto 3','Agua')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Pepsico',47676767 ,'Juan Manuel de Rosas', 8755,null,null,'Snacks y Gaseosas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Distribuidora Lactal','3568-8270' ,'Juan Bautista Alberdi', 7244,2,'J','Pan')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Salchichas El Aleman','3911-2474' ,'Lope De Vega', 1124,8,'Oficina A','Salchichas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Quilmes','4199-3366' ,'Bahia Blanca', 20561,null,null,'Cerveza')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Distribuidora Moderacion','4781-5021' ,'Rivadavia', 9599,3,'A','Bebidas Alcoholicas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Arcor','4686-2324' ,'Corrientes', 5314,null,null,'Galletitas,Chocolates,Helados')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Romance','3537-9066' ,'Pergamino', 911,1,'P','Yerba')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Distribuidora Muy Dulce','4967-0023' ,'Cruz', 4990,5,'Oficina 3','Azucar y Educorantes')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Celusal','4001-8630' ,'Argentina', 11044,null,null,'Sal')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Manaos','4521-1963' ,'Eva Peron', 7666,1,'Z','Bebidas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('Distribuidora El Tano','4288-8263' ,'Charcas', 2177,4,'Depto 2','Pastas')

Insert into Proveedores(nombre, telefono, calleDomicilio, alturaDomicilio, 
						pisoDomicilio, departamentoDomicilio, especialidad)
values('La Virginia S.A.','4687-5500' ,'Artigas', 6344,5,null,'Cafe,Capuccino,Chocolatada')

update proveedores
set departamentoDomicilio='-'
where departamentoDomicilio is null

update proveedores
set pisoDomicilio=0
where pisoDomicilio is null
