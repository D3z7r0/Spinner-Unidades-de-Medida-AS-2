# Spinner-Unidades-de-Medida-AS-2
Aplicacion Movil en Android Studio, se requerìa un conversor de medidas, en donde en la parte superior tuvieramos 3 botones principales en donde al clickear se cargaran las vistas correspondientes para hacer el cálculo.

# Descripcion general
Hay 3 categorias que son los botones para ingresar el valor (Longitud, Masa y Volumen), los cuales se encuentran en el MainActivity.java, la navegación se gestiona reemplazando dinámicamente los fragmentos dentro de un FrameLayout.
Cada categoría de conversión es un módulo totalmente autínomo (Fragment) que maneja su lógica de negocio y ciclo de vida, compartiendo un mismo diseño visual (fragment_universal.xml) para mantener consistencia de la UI.

# Componentes:
XML:
activity_main.xml: Layout principal que contiene una barra de botones superior, el frameLayout.
fragment_universal.xml: Layout reutilizable que define la estructura visual para todas las conversiones, edittext, spinner para la seleccion de unidades, botón de accion y el scrollview + textview.

# JAVA
Lógica de Control (Java)
MainActivity.java

Actúa como el orquestador de la navegación.
Función: Inicializa las instancias de Longitud, Masa y Volumen una sola vez.
Método loadFragment(Fragment): Utiliza el FragmentManager para realizar transacciones de reemplazo (replace) en el contenedor principal.
Longitud.java, Masa.java, Volumen.java
Clases que extienden de Fragment. Cada una contiene la lógica específica de su magnitud física.
    Estructura de Datos:
        UNITS (String[]): Arreglo con los nombres de las unidades para el Spinner.
        CONVERSION_FACTORS (double[]): Arreglo paralelo a UNITS que contiene los factores de equivalencia respecto a una Unidad Base (Metro, Kilogramo, Litro).
        Nota: Se utiliza una relación 1:1 entre el índice del Spinner y el índice del arreglo de factores.
    Algoritmo de Conversión: Se utiliza una conversión de dos pasos para simplificar la combinatoria:
    ValorBase=FactorEntradaValorEntrada​
    ValorFinal=ValorBase×FactorSalida
    Manejo de Cadenas (StringBuilder): Se utiliza StringBuilder en lugar de concatenación simple (+) dentro del bucle de resultados para optimizar el uso de memoria y rendimiento, ya que se generan múltiples líneas de texto en cada cálculo.
Manejo de Errores: Implementación de bloque try-catch (NumberFormatException) para evitar cierres inesperados si el usuario ingresa caracteres no válidos o cadenas vacías.


#MANUAL DE USUARIO:
Bienvenido al Conversor de Unidades. Esta aplicación le permite realizar conversiones rápidas y precisas entre diferentes unidades de medida para Longitud, Masa y Volumen.
1. Pantalla Principal y Navegación
Al abrir la aplicación, verá tres botones en la parte superior:
    Longitud: Para convertir metros, kilómetros, pulgadas, etc.
    Masa: Para convertir kilogramos, libras, onzas, etc.
    Volumen: Para convertir litros, galones, mililitros, etc.
Instrucción: Toque cualquiera de estos botones para cambiar inmediatamente la herramienta de conversión.
2. Cómo realizar una conversión
Siga estos pasos sencillos dentro de cualquier categoría:
    Ingresar Valor: Toque el campo que dice "Ingresa el valor". Se abrirá el teclado numérico. Escriba la cantidad que desea convertir (ej: 10.5).
    Seleccionar Unidad: Toque el menú desplegable (Spinner) debajo del campo de texto. Seleccione la unidad actual de su valor (por ejemplo, si ingresó 10.5 y son Metros, seleccione "Metros").
    Convertir: Presione el botón "CONVERTIR".
    Ver Resultados: En la parte inferior de la pantalla, aparecerá una lista detallada mostrando la equivalencia de su valor en todas las otras unidades disponibles en la aplicación.
3. Solución de Problemas Frecuentes
    Error: "Por favor, ingrese un número válido"
        Causa: Probablemente presionó el botón "Convertir" sin escribir nada en el campo de texto, o ingresó un símbolo extraño.
        Solución: Asegúrese de escribir solo números y un punto decimal si es necesario.
    No veo todos los resultados
        Solución: El área de resultados es desplazable. Deslice su dedo hacia arriba sobre el texto de los resultados para ver la lista completa.
    La aplicación no hace nada al presionar los botones superiores
        Solución: Si ya está en la pantalla de "Longitud", presionar el botón "Longitud" no hará ningún cambio visual. Intente seleccionar una categoría diferente.
