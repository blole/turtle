# syntax=docker/dockerfile:1.4
FROM gradle:8.6.0-jdk21-jammy AS tools
SHELL ["/bin/bash", "-eo", "pipefail", "-c"]

WORKDIR /repo/

RUN mkdir /output/ && \
    java --version |& tee /output/java-version.txt && \
    gradle --version |& tee /output/gradle-version.txt



FROM tools AS common-dependencies
COPY \
    gradle.properties \
    settings.gradle.kts \
    ./
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.console=verbose"



FROM common-dependencies AS app
RUN mkdir -p /output/app/
COPY app/build.gradle.kts app/
COPY app/src/main/ app/src/main/

FROM app AS app-build
RUN gradle shadowJar |& tee /output/app/build.txt

FROM app AS app-test
COPY app/src/test/ app/src/test/
RUN gradle test |& tee /output/app/test.txt



FROM scratch AS ci
COPY --from=app-build /output/ /
COPY --from=app-test /output/ /
