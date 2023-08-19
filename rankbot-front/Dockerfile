FROM node:latest as build
COPY . rankbot
WORKDIR rankbot
RUN npm install -g @angular/cli
RUN npm install
RUN ng build --configuration production --aot

FROM nginx:latest as final
LABEL owner="ahianf"
RUN rm /etc/nginx/conf.d/default.conf
COPY conf.d/app.conf /etc/nginx/conf.d/app.conf
COPY conf.d/reverseproxy.conf /etc/nginx/conf.d/reverseproxy.conf
COPY --from=build rankbot/dist/rankbot-front /static