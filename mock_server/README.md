# Mock server

Este mock usa json-server para simular la API utilizada por la app.

## Cómo levantarlo

cd mock_server
json-server --watch db.json --port 3000

El servidor expone los endpoints:

- `GET /emprendimientos`
- `GET /emprendimientos/{id}`
- `PATCH /emprendimientos/{id}` para actualizar el estado `isFav`.

