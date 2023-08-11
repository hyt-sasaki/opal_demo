package http.api.authz

import input.attributes.request.http as http_request

allow {
    http_request.method == "GET"
    input.parsed_path == ["message"]
}