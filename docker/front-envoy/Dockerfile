FROM envoyproxy/envoy:v1.27-latest
COPY ./docker/front-envoy/config.yaml /config/envoy.yaml

ENTRYPOINT ["/usr/local/bin/envoy", "-c", "/config/envoy.yaml"]