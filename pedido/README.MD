API Pedido Caso Bookpoint
SpringBoot + JPA + MYSQL

Query para crear Base de Datos requerida:
CREATE DATABASE pedido_db

Endpoints de los metodos existentes

Pedido:
Crear:
POST http://localhost:8087/api/v1/pedidos
{
  "usuarioId": 10,
  "ventaId": 1,
  "carritoId": 1,
  "direccionEntrega": "Los Carrera 123, Concepción",
  "formaPago": "TARJETA"
}
Buscar por id:
GET http://localhost:8087/api/v1/pedidos/{id}

Listar pedidos por usuario:
GET http://localhost:8087/api/v1/pedidos/usuario/{usuario{id}}

Listar todos los pedidos:
GET http://localhost:8087/api/v1/pedidos

Actualizar estado del pedido:
PUT http://localhost:8087/api/v1/pedidos/{id}/estado
{
  "estado": "EN_PROCESO"
}

Cancelar pedido:
DELETED http://localhost:8087/api/v1/pedidos/{id}

Ver historial de pedidos:
GET http://localhost:8087/api/v1/pedidos/historial/{usuarioId}

Pasos a seguir:
-Crear base de datos con el query
-Correr la aplicacion
-Crear pedido