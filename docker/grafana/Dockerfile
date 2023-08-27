FROM grafana/grafana:latest

RUN mkdir -p /etc/grafana/provisioning/datasources
COPY ./docker/grafana/ds.yaml /etc/grafana/provisioning/datasources/ds.yaml

ENTRYPOINT ["/run.sh"]