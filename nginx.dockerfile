FROM nginx:latest
RUN rm /etc/nginx/conf.d/default.conf
COPY /nginx/conf.d/app.conf /etc/nginx/conf.d/app.conf
COPY /nginx/conf.d/reverseproxy.conf /etc/nginx/conf.d/reverseproxy.conf
COPY ./nginx/ssl/ /etc/nginx/ssl
COPY ./nginx/static /static