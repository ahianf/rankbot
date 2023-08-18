FROM nginx:latest
RUN rm /etc/nginx/conf.d/default.conf
COPY /rankbot-front/conf.d/app.conf /etc/nginx/conf.d/app.conf
COPY /rankbot-front/conf.d/reverseproxy.conf /etc/nginx/conf.d/reverseproxy.conf
COPY ./rankbot-front/static /static