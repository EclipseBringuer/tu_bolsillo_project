# ------------------------------------
# STAGE 1: COMPILACIÓN (BUILD)
# ------------------------------------
FROM node:22-alpine AS builder

WORKDIR /app

# Copia los archivos de configuración
COPY package.json package-lock.json ./
# Instala las dependencias de producción y desarrollo
RUN npm install

# Copia el código fuente de Angular
COPY . .

# Compila la aplicación de Angular para producción.
# Esto genera los archivos estáticos en la carpeta "dist/browser" (o similar).
# Asegúrate de que el nombre del proyecto de Angular coincida con la ruta de salida.
RUN npm run build

# ------------------------------------
# STAGE 2: SERVICIO (PRODUCTION) con NGINX
# ------------------------------------
FROM nginx:alpine AS final

# Elimina la configuración por defecto de NGINX
RUN rm /etc/nginx/conf.d/default.conf

# Copia la configuración personalizada de NGINX (Paso 2.1)
COPY ./nginx-custom.conf /etc/nginx/conf.d/default.conf

# Copia los archivos compilados de la aplicación del stage 'builder'
# Asegúrate de usar la ruta de salida correcta de tu compilación (típicamente /dist/browser/app-name)
COPY --from=builder /app/dist/frontend/browser/ /usr/share/nginx/html

# Puerto por defecto de NGINX
EXPOSE 80

# Comando para iniciar NGINX
CMD ["nginx", "-g", "daemon off;"]