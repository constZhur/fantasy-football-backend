FROM eclipse-temurin:21 AS builder
WORKDIR /source
COPY ./build/libs/*.jar ./fantasy-football-engine.jar
RUN java -Djarmode=layertools -jar fantasy-football-engine.jar extract

FROM eclipse-temurin:21-jre
WORKDIR /opt/app
COPY --from=builder /source/dependencies/ ./
COPY --from=builder /source/spring-boot-loader/ ./
COPY --from=builder /source/snapshot-dependencies/ ./
COPY --from=builder /source/application/ ./
ENTRYPOINT java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher

