# rankbot

Rankbot es una aplicación que permite comparar elementos de una lista en formato 1v1, para después calcular una tabla de rankings utilizando Elo.

Rankbot expone una API REST que obtiene un par de elementos y el usuario envía su opción preferida, empate o saltar al mismo endpoint.
El endpoint /results devuelve el calculo de Elo de la lista

La aplicación tiene varias formas de protección contra votos ilegitimos, incluyendo Rate Limiting (via bucket4j) y tiempo límite en el que el usuario puede enviar una respuesta, usando ExpiringMap.

Creado con Java, Spring Framework, Nginx y Postgres. 
Desplegado con Docker.

[Demo](https://rankbot.me/daft-punk/index.html)

***

Rankbot is a Spring application that lets you compare elements of a list in a 1v1 format, then calculates a list of scores using Elo.

Rankbot exposes a REST API. GET will give you a tuple of elements, and POST for sending a vote, being the users' choice, a draw or skipping a match. GET /results will return a table with Elo scores calculated.

This app has various forms of protection against malformed votes, including rate limiting (via bucket4j), and time limitations between a GET match and POST vote, using ExpiringMap.

Written with Java, Spring Framework, Nginx and Postgres.
Deployed with Docker.

[Demo](https://rankbot.me/daftpunk/index.html)
