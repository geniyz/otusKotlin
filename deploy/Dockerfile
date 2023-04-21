FROM gradle:jdk-alpine AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM bellsoft/liberica-runtime-container:jre-musl AS runner
COPY --from=builder /home/gradle/src/build/libs/*.jar /run.jar
RUN mkdir /app
ENV PORT="80"
ENV SGR_DIR="/app"
VOLUME "/app"
EXPOSE 80:80
ENTRYPOINT ["java", "-jar", "/run.jar"]
