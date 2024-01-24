En el contexto de la ingeniería informática, el manejo eficiente de las operaciones en segundo plano y la multitarea es crucial, especialmente en situaciones que emulan eventos críticos en tiempo real, como el lanzamiento de un cohete. Este ejercicio está inspirado en el histórico lanzamiento del Apollo 11 el 16 de julio de 1969, el cual llevó al primer hombre a la Luna. Los estudiantes deben crear una simulación de la cuenta regresiva para el lanzamiento, enfocándose en la sincronización de hilos y la comunicación en tiempo real con la interfaz de usuario.

Enunciado del Ejercicio:

Desarrolla una aplicación que simule la cuenta regresiva para el lanzamiento del Apollo 11. La interfaz debe permitir al usuario ingresar una cantidad de segundos y mostrar una barra de progreso y una etiqueta de texto que reflejen la cuenta atrás en tiempo real.

La cuenta atrás debe iniciarse cuando el usuario pulse un botón.
La aplicación debe permitir al usuario cancelar la cuenta atrás en cualquier momento.
Al finalizar la cuenta, se debe mostrar un mensaje en una ventana emergente que indique si el lanzamiento ha sido exitoso o cancelado.
Amplía la aplicación para que lance dos hilos concurrentes: el primero corresponde a la cuenta atrás, mientras que el segundo monitorea y registra el estado del primero (activo, cancelado, completado), posiblemente simulando un registro de las condiciones del lanzamiento.

Escala la aplicación para ejecutar cuatro hilos secuencialmente, donde cada hilo representa una fase crítica del lanzamiento. Cada hilo debe iniciar solo después de que el anterior haya concluido, simulando la naturaleza secuencial y dependiente de cada fase del lanzamiento del cohete.

Rúbrica de Evaluación (sobre 100 puntos):

Funcionalidad de la Cuenta Regresiva (30 puntos)

Inicio y fin correctos de la cuenta atrás: 10 puntos.
Visualización precisa mediante barra de progreso y etiqueta de texto: 10 puntos.
Funcionalidad de cancelación en tiempo real: 10 puntos.
Implementación de Hilos Concurrentes (20 puntos)

Ejecución correcta de dos hilos de forma concurrente: 10 puntos.
Monitoreo y registro adecuado del estado del hilo de cuenta atrás: 10 puntos.
Control de Flujo con Múltiples Hilos (30 puntos)

Ejecución secuencial correcta de los cuatro hilos: 15 puntos.
Sincronización adecuada y manejo de dependencias entre hilos: 15 puntos.
Diseño de la Interfaz de Usuario y Experiencia del Usuario (10 puntos)

Claridad y estética de la interfaz de usuario: 5 puntos.
Mensajes de estado claros y precisos (ventana emergente, etiquetas): 5 puntos.
Calidad del Código (10 puntos)

Legibilidad y mantenimiento del código: 5 puntos.
Comentarios y documentación adecuados: 5 puntos.
Bonus: Creatividad y Funcionalidad Extra (hasta 10 puntos extra)

Inclusión de características avanzadas, como simulaciones gráficas, sonidos de cuenta regresiva, información histórica del Apollo 11, etc.
Mejoras en la experiencia del usuario, como mensajes de error personalizados, opciones de reinicio, entre otros.
