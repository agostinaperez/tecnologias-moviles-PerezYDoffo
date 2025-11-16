# Mock server

Este mock usa json-server para simular la API utilizada por la app.

## Cómo levantarlo

cd mock_server
json-server --watch db.json --port 3000

El servidor expone los endpoints:

- `GET /emprendimientos`
- `GET /emprendimientos/{id}`
- `PATCH /emprendimientos/{id}` para actualizar el estado `isFav`.
- `GET /users` y `GET /users?email={email}` para obtener usuarios o filtrar por email.
- `GET /users/{id}` para traer el perfil del usuario autenticado.
- `POST /users` para registrar nuevos usuarios (enviar `passwordHash` ya encriptado).
- `PATCH /users/{id}` para editar datos del perfil o actualizar la contraseña.
