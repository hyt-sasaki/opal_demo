FROM openpolicyagent/opa:0.58.0-envoy
COPY ./docker/opa/config.yaml /config/config.yaml

#CMD ["run", "--server", "-c", "/config/config.yaml", "--log-format", "json-pretty"]
CMD ["run", "--server", "-c", "/config/config.yaml"]
