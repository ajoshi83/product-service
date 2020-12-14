FROM azul/zulu-openjdk-alpine:11-jre
ARG JAR_FILE
RUN addgroup -g 1000 techies
RUN adduser -D -H -u 1000 -G techies supportguy
RUN mkdir -p /logs
RUN chown supportguy /logs
USER supportguy
VOLUME /tmp
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]