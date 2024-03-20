# Robot simulator
## Installation
Open the devcontainer in e.g. vscode, github codespaces, or intellij ultimate

## Testing
### With gradle
To run the junit tests
```shell
gradle test
```

### With docker
To run all the tests, same as in github actions
```shell
docker buildx build . --rm --target ci --output /tmp/turtle-ci-output/
```
You can then find the output from building and testing under `/tmp/turtle-ci-output/`. You can see an example of the output in the _print dockerized test output_-step in github actions.
