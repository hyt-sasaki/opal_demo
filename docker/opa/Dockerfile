FROM openpolicyagent/opa:0.55.0-envoy
COPY ./docker/opa/config.yaml /config/config.yaml

CMD ["run", "--server", "-c", "/config/config.yaml"]