FROM envoyproxy/envoy:v1.27-latest
COPY ./docker/opal-server-proxy/config.yaml /config/envoy.yaml

ENTRYPOINT ["/usr/local/bin/envoy", "-c", "/config/envoy.yaml"]