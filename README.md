# API de Consulta del Clima

Este proyecto consiste en una API para consultar el clima, la cual consume directamente la API de OpenWeatherMap.

## Tecnologías Utilizadas

- Lenguaje de Programación: Scala, descargar scala
- Herramienta de Construcción: sbt, se puede descargar en tu pc desde https://www.scala-sbt.org/download/
- Requisito: Java 11 o 17
- Requisito bases de datos: descargar SQLite desde https://www.sqlite.org/download.html

## Configuración y Ejecución

Para poder ejecutar este proyecto, se requiere tener instalado Java 11 en el equipo. Además, es necesario contar con sbt, que puede ser descargado desde el siguiente enlace: [Descargar sbt](https://www.scala-sbt.org/download/).

Es altamente recomendado utilizar IntelliJ IDEA como IDE para el desarrollo de este proyecto. Asegúrate de tener instalado el plugin de Scala en IntelliJ IDEA. Puedes hacerlo siguiendo estos pasos:

1. Abre IntelliJ IDEA.
2. Presiona `Ctrl+Alt+S` para abrir la pestaña de configuración.
3. Navega hasta `Plugins`.
4. Busca el plugin de Scala y asegúrate de tenerlo instalado.

Una vez instalado Java 11, sbt y el plugin de Scala en IntelliJ IDEA, puedes seguir estos pasos:

1. Clona este repositorio en tu máquina local.
2. Abre una terminal y navega hasta el directorio del proyecto.
3. Ejecuta el comando `sbt update` para descargdar las dependencias necesarias para el proyecto.
4. Ejecuta el comando `sbt run` para compilar y ehecutar el proyecto.
5. La API estará disponible en la dirección local especificada por sbt.

¡Disfruta de la experiencia de consultar el clima con esta API!
