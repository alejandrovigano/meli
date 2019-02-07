# MELI

## Información inicial

#### Levantar la aplicación local

- Correr mvn clean install
- Correr mvn spring-boot:run
- Se utilizó H2 para el ambiente local

#### Acceder a la aplicacon en appengine

- ir a: https://meli-230900.appspot.com (Puede tardar el primer ingreso, ya que el servidor se apaga por inactividad)

#### Pasos para probar:
- https://meli-230900.appspot.com/clima/procesar - job que evalua la posicion de los planetas 10 años, calcula el pronostico climatico y lo guarda en la base de datos
- https://meli-230900.appspot.com/clima/analizar - analiza los datos de la base de datos, y genera un objeto con la informacin de dias sequias, periodos de lluvias y dias de presion y temp optimos.
- https://meli-230900.appspot.com/clima?dia={día} - obtiene de la base de datos, la información de un día en particular

## Información sobre resolución

#### Modelo de datos:

Sistema planetario
<img src="https://raw.githubusercontent.com/alejandrovigano/meli/efa3566394417ff2e278e7127fa78c8f3be8331d/uml/planets.svg?sanitize=true">
Pronóstico del clima
<img src="https://raw.githubusercontent.com/alejandrovigano/meli/efa3566394417ff2e278e7127fa78c8f3be8331d/uml/weather.svg?sanitize=true">

#### Resolución:

1. Se configura un sistema planetario con los datos dados en el ejercicio (Ver: **MeliApplication#planetarySystem()**)
2. Se crea la logica para obtener los datos de un planeta en un instante determinado (Se crea un PlanetSystemInstant, ver: **PlanetarySystemInstantServiceImpl#getInstant()**

- Se obtiene el angulo (entre 0 y 360) y la posición de un planeta sobre el plano paralelo a las orbita

3. Se crea la logica para obtener las condiciones climaticas de un dia, utilizando los datos generados en el punto **2** (Ver **WeatherServiceImpl#calculateWeather(day)**)

- Para determinar si el dia tiene sequia, se evalua que los angulos de los planetas sean iguales o tengan diferencia de exactamente 180°
- Para determinar si la presion y temperatura están en optimas condiciones, se evalua que la pendiente de las rectas formadas por planeta1-planeta2 y planeta2-planeta3 sean iguales. (Se deja un margen de error 0,01 por aproximaciones)
- Para determinar si hay lluvias, se compara que el area formada por la union de los tres planetas sea igual a la suma de las areas de los triangulos formados por: estrella-planeta1-planeta2 , estrella-planeta2-planeta3, estrella-planeta1-planeta3. Si las areas son iguales, la estrella está contenida dentro de los planetas y está lloviendo.
- Se crea un ForecastDay con la informacin del dia y la condición climatica

4. Se toma la cantidad de dias desde "hoy" hasta 10 años en el futuro, y se evaluan dia a dia las condiciones climaticas.

- El proceso es asincronico, se utiliza un threadpool fijo de 4 hilos.
- Se guarda la informacin de cada dia en la base de datos
- En cada corrida se borran todos los datos previamente generados

5. Se generan 3 endpoints 
- **/clima/procesar**, ejecuta el job y procesa de manera asyncronica y paralela la predicción de cada día.
- **/clima/analizar**, lee de la base de datos, los datos generados por /clima/procesar, y retorna un analisis de los proximós 10 años
- **/clima?dia={dia}**, retorna la condicin climatica de un dia en particular. 404 si el dia no existe en la base de datos
