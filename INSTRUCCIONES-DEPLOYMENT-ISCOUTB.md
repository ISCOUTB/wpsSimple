# Instrucciones de Deployment para ISCOUTB

## 📋 Contexto

Después de aceptar los Pull Requests con las correcciones de los POMs, es necesario **publicar las nuevas versiones** de todos los paquetes BESA a GitHub Packages bajo la organización **ISCOUTB**.

---

## ⚙️ Prerequisitos

Antes de comenzar, asegúrese de tener configurado:

### 1. GitHub Personal Access Token

Necesita un token con permisos de **`write:packages`** y **`read:packages`**.

**Crear token:**
1. Ir a: https://github.com/settings/tokens
2. Click en "Generate new token (classic)"
3. Nombre: `ISCOUTB-Maven-Deploy`
4. Seleccionar permisos:
   - ✅ `write:packages`
   - ✅ `read:packages`
   - ✅ `repo` (recomendado)
5. Click "Generate token"
6. **Copiar el token** (se muestra una sola vez)

### 2. Configurar Maven settings.xml

Editar o crear el archivo `~/.m2/settings.xml` (en Windows: `C:\Users\<Usuario>\.m2\settings.xml`):

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                              http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>github</id>
            <username>SU_USUARIO_GITHUB</username>
            <password>SU_TOKEN_AQUI</password>
        </server>
    </servers>
</settings>
```

**Reemplazar:**
- `SU_USUARIO_GITHUB` con su usuario de GitHub
- `SU_TOKEN_AQUI` con el token generado en el paso 1

---

## 🚀 Proceso de Deployment

### ⚠️ IMPORTANTE: Orden de Publicación

Las librerías **DEBEN** publicarse en este orden debido a las dependencias:

1. **KernelBESA** (no tiene dependencias BESA)
2. **RationalBESA** (depende de KernelBESA)
3. **LocalBESA** (depende de KernelBESA)
4. **BDIBESA** (depende de KernelBESA y RationalBESA)
5. **eBDIBESA** (depende de KernelBESA y BDIBESA)
6. **RemoteBESA** (depende de KernelBESA y LocalBESA)
7. **wpsSimple** (depende de todas las anteriores)

---

## 📦 Comandos de Deployment

### 1️⃣ KernelBESA

```bash
# Navegar al repositorio
cd KernelBESA

# Limpiar builds anteriores, compilar, empaquetar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Ir a: https://github.com/orgs/ISCOUTB/packages
- Buscar: `io.github.iscoutb.kernel-besa`
- Versión: `3.6.0`

---

### 2️⃣ RationalBESA

```bash
# Navegar al repositorio
cd ../RationalBESA

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.rational-besa`
- Versión: `3.6.0`

---

### 3️⃣ LocalBESA

```bash
# Navegar al repositorio
cd ../LocalBESA

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.local-besa-simple`
- Versión: `3.6.0`

---

### 4️⃣ BDIBESA

```bash
# Navegar al repositorio
cd ../BDIBESA

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.bdi-besa`
- Versión: `3.6.0`

---

### 5️⃣ eBDIBESA

```bash
# Navegar al repositorio
cd ../eBDIBESA

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.ebdi-besa`
- Versión: `3.6.0`

---

### 6️⃣ RemoteBESA

```bash
# Navegar al repositorio
cd ../RemoteBESA

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.remote-besa`
- Versión: `3.6.0`

---

### 7️⃣ wpsSimple

```bash
# Navegar al repositorio
cd ../wpsSimple

# Limpiar, compilar y publicar
mvn clean deploy

# Esperar confirmación: "BUILD SUCCESS"
```

**Verificar en GitHub:**
- Buscar: `io.github.iscoutb.wps-simulator`
- Versión: `3.6.0`

---

## ✅ Verificación Final

Después de publicar todos los paquetes, verificar que están disponibles:

### Método 1: GitHub Web UI

1. Ir a: https://github.com/orgs/ISCOUTB/packages
2. Deberían aparecer 7 paquetes:
   - ✅ kernel-besa (3.6.0)
   - ✅ rational-besa (3.6.0)
   - ✅ local-besa-simple (3.6.0)
   - ✅ bdi-besa (3.6.0)
   - ✅ ebdi-besa (3.6.0)
   - ✅ remote-besa (3.6.0)
   - ✅ wps-simulator (3.6.0)

### Método 2: Comando Maven (desde cualquier proyecto)

```bash
# Verificar que se pueden descargar las dependencias
mvn dependency:tree | grep "io.github.iscoutb"
```

Debería mostrar todas las dependencias BESA descargadas correctamente.

---

## 🔍 Solución de Problemas

### Error: "Could not transfer artifact"

**Causa:** Token inválido o sin permisos

**Solución:**
1. Verificar que el token tenga permisos `write:packages`
2. Verificar que el token no haya expirado
3. Verificar que `settings.xml` esté correctamente configurado

---

### Error: "Could not find artifact io.github.iscoutb:kernel-besa:jar:3.6.0"

**Causa:** KernelBESA no se publicó correctamente

**Solución:**
1. Publicar primero KernelBESA
2. Esperar 2-3 minutos para que GitHub Packages indexe el paquete
3. Intentar publicar el siguiente paquete

---

### Error: "Duplicate entry"

**Causa:** La versión 3.6.0 ya existe en GitHub Packages

**Solución:**
- Si necesita sobrescribir: Eliminar el paquete desde GitHub Web UI primero
- Si es correcto: Incrementar la versión en el `pom.xml` (ej: 3.6.1)

---

## 📊 Resumen de Artefactos Generados

Cada comando `mvn clean deploy` genera y publica:

1. **JAR principal** - Código compilado (`<artifact>-3.6.0.jar`)
2. **Sources JAR** - Código fuente (`<artifact>-3.6.0-sources.jar`)
3. **Javadoc JAR** - Documentación (`<artifact>-3.6.0-javadoc.jar`)
4. **POM** - Descriptor Maven (`<artifact>-3.6.0.pom`)

Todos estos archivos se publican automáticamente en:
```
https://maven.pkg.github.com/ISCOUTB/<RepositoryName>
```

---

## 🎯 Resultado Esperado

Al finalizar, los usuarios podrán usar las librerías BESA simplemente agregando a su `pom.xml`:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/ISCOUTB/*</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>io.github.iscoutb</groupId>
        <artifactId>kernel-besa</artifactId>
        <version>3.6.0</version>
    </dependency>
    <!-- ... otras dependencias BESA ... -->
</dependencies>
```

Y configurando su `settings.xml` con credenciales de GitHub.

---

## 📝 Notas Adicionales

- **Tiempo estimado:** 15-20 minutos para publicar todos los paquetes
- **Conexión:** Requiere conexión a Internet estable
- **Espacio:** Los paquetes publicados ocupan ~1-2 MB cada uno
- **Visibilidad:** Los paquetes son **privados** por defecto, solo accesibles para miembros de ISCOUTB

Para hacer los paquetes **públicos** (opcional):
1. Ir a cada paquete en https://github.com/orgs/ISCOUTB/packages
2. Package Settings → Danger Zone → Change visibility → Public

---

## ✨ Mejoras Implementadas

Comparado con la versión anterior, esta configuración:

✅ **Elimina `systemPath`** - No más referencias a archivos locales  
✅ **Versiones consistentes** - Todas las librerías usan 3.6.0  
✅ **Sin perfiles locales** - Funcionamiento uniforme para todos  
✅ **Dependencias correctas** - Maven resuelve automáticamente desde GitHub Packages  
✅ **Build reproducible** - Cualquier desarrollador puede compilar sin configuración especial  

---

**Fecha de documento:** 29 de octubre de 2025  
**Autor:** Andres Garcia  
**Versión:** 1.0
