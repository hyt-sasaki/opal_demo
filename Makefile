.PHONY : check-envoy-config

check-envoy-config:
	docker run --rm -v `pwd`/docker/envoy/config.yaml:/config/envoy.yaml envoyproxy/envoy:v1.27-latest --mode validate -c /config/envoy.yaml