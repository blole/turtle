# syntax=docker/dockerfile:1.4
FROM mcr.microsoft.com/devcontainers/base:1.0.19-ubuntu-22.04
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# install tools that should be available in the dev container
WORKDIR /devcontainersetup/
ENV BASH_ENV /root/.env
RUN touch "$BASH_ENV" && \
    echo '. "'"$BASH_ENV"'"' >> ~/.bashrc && \
    echo '. "'"$BASH_ENV"'"' >> ~/.zshrc

RUN cat <<EOF >> "$BASH_ENV"
alias dive="docker run -ti --rm  -v /var/run/docker.sock:/var/run/docker.sock wagoodman/dive"
EOF
