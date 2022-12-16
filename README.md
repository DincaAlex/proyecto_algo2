**Aplicacion para agencia de viajes [en Java]** *pre-alpha version*

Grupo 4:
- Callán Pérez Sergio
- Dincă Alexandru-Andrei
- Ortega Moran Kenneth 
- Velarde Huancahuari Bryan Anthony

**Instrucciones de ejecución**
1. Se requiere la librería json-simple-1.1.1 y utilizar jdk18 o mayor.
2. Al ejecutar el programa, elegir entre cliente y administrador (si se elige este último se pedirá una contraseña antes de entrar a los menús, la cual es 12345)

**Funcionalidades añadidas:**

1. Función agregarTransporte
   1. La función lee el JSONConfigFileViajes cada que se utilice la función para evitar fallos por el uso de una versión anterior.
   2. Se crea un scanner para su posterior uso.
   3. Se llama a la función mostrar que mostrará todas las rutas registradas.
   4. El usuario digita el ID de la ruta de su interés de la lista mostrada.
   5. Se llama a la función conseguirInfoRuta que clonará la información de la ruta con el ID colocado si coincide con una ya registrada, caso contrario colocará como valores a ciudadPartida y ciudadLlegada "error"
   6. Si las variables mencionadas tienen el valor "error", se saltará todo el proceso siguiente, caso contrario se copiarán sus valores a variables String.
   7. El usuario digita la información del transporte necesaria, salvo el ID del transporte, que se generará juntando los datos de las variables TipoTransporte, empresa y calidad.
   9. Se crea una variable Transporte con la información necesaria para el constructor.
   10. Se llama a la función agregar la cual, si ya hay un transporte con el mismo ID, mostrará un mensaje de aviso y retornará, caso contrario se guardará en el HashMap de transportes.
   11. Se guardan los cambios.
3. Función reservarHotel(String UUID)
   1. La función lee el JSONConfigFile cada que se utilice la función para evitar fallos por el uso de una versión anterior.
   2. Se crea un scanner para su posterior uso.
   3. Se evalua la función mostrarHoteles; de estar vacío se mostrará un mensaje de aviso y retornará verdadero, caso contrario se mostrará una lista con los datos de cada uno y retornará falso.
   5. De ser verdadero, se saltará todo el proceso siguiente, caso contrario el usuario digitará el nombre del hotel en el que se hospedará.
   6. Se crea una variable Cuarto solo con el nombre del hotel anterior, los demás campos estarán vacíos.
   7. Se llama a la función reservar(Cuarto cuarto, String UUID) que buscará en el archivo JSON un cuarto con el mismo nombre del cuarto creado.
   8. Si se encuentra un cuarto con ocupado en falso, se copiará la información al cuarto creado, se eliminará al cuarto encontrado de la lista y se agregará el cuarto creado a la lista.
   9. Se genera un String con la información copiada para la función reservarHotel(String UUID, String IDHotel, String IDCuarto) de ConfigReservaHotel, la cual guardará aparte los ID del hotel, el cliente y el cuarto en un json creado para ello.
   10. En caso el cuarto con ocupado hubiera sido verdadero, se continuaría la búsqueda con el siguiente elemento.
   11. Se guardan los cambios.
