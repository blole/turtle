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
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"



FROM common-dependencies AS app
RUN mkdir -p /output/app/
COPY app/build.gradle.kts app/
COPY app/src/main/ app/src/main/

FROM app AS app-build
RUN gradle shadowJar |& tee /output/app/build.txt

FROM app AS app-test
COPY app/src/test/ app/src/test/
RUN gradle test |& tee /output/app/test.txt

FROM tools AS app-test-built
WORKDIR /testing/
RUN mkdir -p /output/app/
COPY app/testfiles/ testfiles/
COPY --from=app-build /repo/app/build/libs/turtle-all.jar .
RUN bash -eo pipefail <<-EOF |& tee /output/app/test-built.txt
    EXITCODE=0
    for TESTFILE in testfiles/*.in; do
        EXPECTED_OUTPUT="\${TESTFILE%.in}.out"
        java -jar turtle-all.jar < "\$TESTFILE" > actual.out
        if cmp -s "\$EXPECTED_OUTPUT" actual.out ; then
            echo "pass \$TESTFILE"
        else
            echo "fail \$TESTFILE"
            diff -urN "\$EXPECTED_OUTPUT" actual.out
            EXITCODE=1
        fi
    done
    exit \$EXITCODE
EOF



FROM scratch AS ci
COPY --from=app-build /output/ /
COPY --from=app-test /output/ /
COPY --from=app-test-built /output/ /
