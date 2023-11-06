package http.api.authz

import input.attributes.request.http as http_request

default allow := false

allow {
    http_request.method == "GET"

    some id
    input.parsed_path = ["messages", id]

    some idx
    data.custom_info[idx].id == id
    trace_when_condition_fail(data.custom_info[idx].flag)

    data.custom_info[idx].flag == true
}

allow {
    http_request.method == "PUT"

    some id
    input.parsed_path = ["messages", id]
}

trace_when_condition_fail(condition) {
    condition != true
    trace("fail")
}