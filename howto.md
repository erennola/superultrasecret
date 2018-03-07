### Para iniciar el cotizador se puede correr:
 * `$ java -jar cotizador.jar`
 * `$ java -DPORT="8888" -jar cotizador.jar`

### Ejemplo de request:
```sh
$ curl http://localhost:8080/stocks/GOOGL
{"ticker":"GOOGL","value":281.2730236307965}
```

### A TENER EN CUENTA:  
El cotizador es una aplicación un poco vieja... no siempre funciona tan bien ni tan rápido como nos gustaría :(

