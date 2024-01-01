FROM my-openjdk8
 
ENV SERVICE_PORTS=8080 \
    TZ=Asia/Shanghai
 
RUN mkdir -p /app/
 
WORKDIR /app
 
COPY ./entrypoint.sh /app/
RUN chmod 755 -R /app/
 
COPY ./ruoyi*.jar /app/
 
ENTRYPOINT ["/app/entrypoint.sh"]