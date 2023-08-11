FROM openpolicyagent/opa:0.55.0-envoy
COPY ./docker/opa/config.yaml /config/config.yaml
COPY ./docker/opa/policies /config/policies

CMD ["run", "--server", "-c", "/config/config.yaml", "-b", "/config/policies"]