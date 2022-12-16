**Aplicacion para agencia de viajes [en Java]** *pre-alpha version*

Grupo 4:
- Callán Pérez Sergio
- Dincă Alexandru-Andrei
- Ortega Moran Kenneth 
- Velarde Huancahuari Bryan Anthony

**Instrucciones de ejecución**
1. Se requiere la librería json-simple-1.1.1 y utilizar jdk18 o mayor.
2. Al ejecutar el programa, elegir entre cliente y administrador (si se elige este último se pedirá una contraseña antes de entrar a los menús, la cual es 12345)
3. 

**Funcionalidades añadidas:**

1. Función agregarTransporte
   1. La función lee el JSONConfigFileViajes cada que se utilice la función para evitar fallos por el uso de una versión anterior.
   2. Se crea un scanner para su posterior uso.
   3. Se llama a la función mostrar que mostrará todas las rutas registradas.
   4. El usuario digita el ID de la ruta de su interés de la lista mostrada.
   5. Se llama a la función conseguirInfoRuta que clonará la información de la ruta con el ID colocado si coincide con una ya registrada, caso contrario colocará como      valores a ciudadPartida y ciudadLlegada "error"
   6. Si las variables mencionadas tienen el valor "error", se saltará todo el proceso siguiente, caso contrario se copiarán sus valores a variables String.
   7. El usuario digita la información del transporte necesaria, salvo el ID del transporte, que se generará juntando los datos de las variables TipoTransporte, empresa
   y calidad.
   8. Se crea una variable Transporte con la información necesaria para el constructor.
   9. Se llama a la función agregar la cual, si ya hay un transporte con el mismo ID, mostrará un mensaje de aviso y retornará, caso contrario se guardará en el HashMap
      de transportes.
3. Función 

**Como usar:**
1. Entrar como administrador o cliente
2. Ambos tienen la opcion de registrar e ingresar
3. Al ingresar como administrador se tiene las siguientes opciones:
   1. Agregar hotel: crea un hotel
   2. Agregar cuarto: acrea un cuarto al hotel especificado
   3. Autogenerar cuarto: crea varios cuartos al hotel especificado segun los parametros especificados
4. Al ingresar como cliente se tiene las siguientes opciones:
   1. Realizar reserva en hotel: busca un cuarto no ocupado y lo reserva
5. Los administradores, clientes, hoteles y cuartos son guardados en archivos .json

