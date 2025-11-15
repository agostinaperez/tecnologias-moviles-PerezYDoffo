Manos Locales es una aplicación Android desarrollada con Kotlin que utiliza Jetpack Compose para su interfaz de usuario. Permite a los usuarios explorar, marcar favoritos y recibir notificaciones de novedades como descuentos o un nuevo emprendimiento.
🎯 Características principales

    Descubrir emprendimientos: Visualiza una lista de emprendimientos cercanos.
    Favoritos: Marca y accede rápidamente a tus emprendimientos favoritos.

🚀 Cómo ejecutar la aplicación
1️⃣ Clonar el repositorio

   gh repo clone agostinaperez/tecnologias-moviles-PerezYDoffo
   cd Android

2️⃣ Abrir el proyecto en Android Studio

    Inicia Android Studio.
    Selecciona "Open an existing project".
    Navega al repositorio clonado y selecciónalo.

3️⃣  Construir y ejecutar la aplicación

    Conecta un dispositivo Android o inicia un emulador desde Android Studio.
    Haz clic en el botón Run para compilar y ejecutar la app.

📦 Dependencias clave

    Jetpack Compose: Interfaz de usuario moderna y declarativa.
    Coil: Carga eficiente de imágenes.
    Retrofit + Moshi: Consumo de la API mock levantada con json-server.
    Room + Coroutines: Persistencia local y reactividad del listado/favoritos.

🗄 Mock server y persistencia

La carpeta `mock_server/` incluye una base `db.json` compatible con `json-server`. Se levanta con:

cd mock_server
json-server --watch db.json --port 3000

La app apunta a http://10.0.2.2:3000/ por defecto (localhost visto desde el emulador). Hay que tener el servidor corriendo antes de abrir la app para que Retrofit pueda sincronizar Room con los últimos datos!!

🛠 Configuración del proyecto

Este proyecto está configurado con Gradle. Archivos clave de configuración:

    build.gradle (nivel de aplicación): Configuración de dependencias y plugins.
    gradle/libs.versions.toml: Gestión centralizada de versiones de bibliotecas.
