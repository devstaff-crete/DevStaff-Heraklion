# Demo: Authorino

Demo of [Authorino](https://github.com/kuadrant/authorino), K8s-native external authorization service, to secure an application, the **News Agency API**, on Kubernetes, presented by [Guilherme Cassolato](https://github.com/guicassolato).

<br/>

The News Agency API ("News API" for short) is a REST API to manage news articles (Create, Read, Delete), with no embedded concept of authentication or authorization. Records are stored in a Redis database deployed together with the application.

HTTP endpoints available:
```
POST /{category}          Create a news article
GET /{category}           List news articles
GET /{category}/{id}      Read a news article
DELETE /{category}/{id}   Delete a news article
```

A news article is structured as follows:

```jsonc
{
  "id": <string: auto-generated>,
  "title": <string>,
  "body": <string>,
  "date": <string: ISO 8601>,
  "author": <string>,
  "user_id": <string>
}
```

The attributes `author` and `user_id` can only be supplied in a stringified JSON passed in the request in the `x-ext-auth-data` HTTP header.

The demo covers securing the News API with authentication based on API keys as well as Kubernetes Service Account tokens, and authorization based on Kubernetes SubjectAccessReview.

### Outline

![Outline](http://www.plantuml.com/plantuml/png/XP31IWCn48RlUOgXNXGHzEP9kbOFuaLiZu8CoMoxR7OI9XFelhrnbuLOAruc97nV_ZzP9qNHF7YJ-euZ2WxWgCNiTKT7RNotvu5OmPP1Kb4IChjD42Q1krjZXAmYxpt1wecY3oFeWG1Zz9r5xG9_y2K7mAo7gnLW0ZTHjRUJCrBcA44BH6xILCRFwWmkshQjBzcIpK9dmfkt5-XfX6julK_m_jXivXvf4lxjyRl5tnsUZqhi0Asbb4hqT-2s0GqzSPfJQKACcNy1RXvE7sPEzWLPgixBulmqQdu9MPUH1_y5)

### Stack

![Stack](http://www.plantuml.com/plantuml/png/SoWkIImgAStDuIfAJIv9p4lFILLGyYvDIYtAIor9BLPII2nMo8Pp5Qgv51IG5BhcbULNWjMaWbXWQHG5VgdbnGgE0PvWDNb0JdnYGIPGKIsgEOwb9HdvHPbv-M1rYJ0ULoqN5voZe0knXCiXDIy5w600)

### Prerequisites

- [Docker](https://www.docker.com/)
- [Kind](https://kind.sigs.k8s.io/)

## Deploy the application

**API lifecycle**

![Lifecycle](http://www.plantuml.com/plantuml/png/hP9DI_j04CRl-HH3Jl_ofvY-U2kbfIY8e89w2yWccIHBDxDbTbQidzvjWkG5RvfSChiFy_pcoUoSA1RVcCWTDPqKgmOAB9Ktye8ViZUweWP984SIv86AhQVYO9cGOP544MCkYYg346-oxM9pbMrJsZ_TWNRWwSHMWW2BbBft7fwKbaa2Z_Sng95cqcivwfLXhIcqkQ5tU_w_zr9RrcHJ-jTevpHL4CUNquEbKbTnFElTriaQ7gp0xGMzDIKhRsMeffQhpl8PSyzQpk3o6Xk4qZ98ZH1GKdAY0Yje0aKJpm3Z7JAKIXi7Oa65IoJHkH8S0ItWbLGtmoTsJ9w6wYdP-jTa1YijqF8PbHyTd93Rw2oDq5OX9yvqKI3rN1te5EhwR-8QpHra1VIEin-NnXwZQB0uCDyEVkdtLtiyJNLI1ybumBxeBeFJ3gdmZVa2)

<details>
  <summary><b>① Create the cluster</b></summary>

  ```sh
  kind create cluster --name demo
  ```
</details>

<details>
  <summary><b>② Create the namespace</b></summary>

  ```sh
  kubectl create namespace news-agency
  ```
</details>

<details>
  <summary><b>③ Deploy the application</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: apps/v1
  kind: Deployment
  metadata:
    name: news-api
    labels:
      app: news-api
  spec:
    selector:
      matchLabels:
        app: news-api
    template:
      metadata:
        labels:
          app: news-api
      spec:
        containers:
        - name: news-api
          image: quay.io/kuadrant/authorino-examples:news-api
          imagePullPolicy: IfNotPresent
          env:
          - name: PORT
            value: "3000"
          - name: REDIS_URL
            value: redis://redis.news-agency.svc.cluster.local:6379
    replicas: 1
  ---
  apiVersion: v1
  kind: Service
  metadata:
    name: news-api
    labels:
      app: news-api
  spec:
    selector:
      app: news-api
    ports:
    - name: http
      port: 3000
      protocol: TCP
  ---
  apiVersion: apps/v1
  kind: Deployment
  metadata:
    name: redis
    labels:
      app: redis
  spec:
    selector:
      matchLabels:
        app: redis
    template:
      metadata:
        labels:
          app: redis
      spec:
        containers:
        - name: redis
          image: redis
          imagePullPolicy: IfNotPresent
          ports:
          - containerPort: 6379
    replicas: 1
  ---
  apiVersion: v1
  kind: Service
  metadata:
    name: redis
    labels:
      app: redis
  spec:
    selector:
      app: redis
    ports:
    - name: redis
      port: 6379
      protocol: TCP
  EOF
  ```
</details>

## Try the application unprotected

<details>
  <summary><b>① Expose the application</b></summary>

  ```sh
  kubectl port-forward -n news-agency service/news-api 3000:3000 2>&1 >/dev/null &;PID=$!
  ```
</details>

<details>
  <summary><b>② Send requests to the application unproctected</b></summary>

  ```sh
  curl http://api.news-agency.127.0.0.1.nip.io:3000/tech -s -i
  # HTTP/1.1 200 OK
  ```

  ```sh
  curl -X POST \
       -d '{"title":"Risky online behaviour ‘almost normalised’ among young people, says study","body":"EU-funded survey of people aged 16-19 finds one in four have trolled someone – while UK least ‘cyberdeviant’ of nine countries (By Dan Milmo - The Guardian)"}' \
       http://api.news-agency.127.0.0.1.nip.io:3000/tech -s -i
  # HTTP/1.1 200 OK
  ```
</details>

## Secure the application

Secure the application with authentication based on API keys and authorization using the Kubernetes SubjectAccessReview (Kubernetes RBAC).

<details>
  <summary><b>① Install Authorino</b></summary>

  Install the Authorino Operator:

  ```sh
  kubectl apply -f https://raw.githubusercontent.com/Kuadrant/authorino-operator/main/config/deploy/manifests.yaml
  ```

  Request an Authorino instance:

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: operator.authorino.kuadrant.io/v1beta1
  kind: Authorino
  metadata:
    name: authorino
  spec:
    listener:
      tls:
        enabled: false
    oidcServer:
      tls:
        enabled: false
  EOF
  ```
</details>

<details>
  <summary><b>② Inject the Envoy sidecar</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: v1
  kind: ConfigMap
  metadata:
    name: envoy
    labels:
      app: envoy
  data:
    envoy.yaml: |
      static_resources:
        clusters:
        - name: news-api
          connect_timeout: 0.25s
          type: strict_dns
          lb_policy: round_robin
          load_assignment:
            cluster_name: news-api
            endpoints:
            - lb_endpoints:
              - endpoint:
                  address:
                    socket_address:
                      address: 127.0.0.1
                      port_value: 3000
        - name: authorino
          connect_timeout: 0.25s
          type: strict_dns
          lb_policy: round_robin
          http2_protocol_options: {}
          load_assignment:
            cluster_name: authorino
            endpoints:
            - lb_endpoints:
              - endpoint:
                  address:
                    socket_address:
                      address: authorino-authorino-authorization.news-agency.svc.cluster.local
                      port_value: 50051
        listeners:
        - address:
            socket_address:
              address: 0.0.0.0
              port_value: 8080
          filter_chains:
          - filters:
            - name: envoy.http_connection_manager
              typed_config:
                "@type": type.googleapis.com/envoy.extensions.filters.network.http_connection_manager.v3.HttpConnectionManager
                stat_prefix: local
                route_config:
                  name: local_route
                  virtual_hosts:
                  - name: local_service
                    domains: ['*']
                    routes:
                    - match: { prefix: / }
                      route: { cluster: news-api }
                http_filters:
                - name: envoy.filters.http.ext_authz
                  typed_config:
                    "@type": type.googleapis.com/envoy.extensions.filters.http.ext_authz.v3.ExtAuthz
                    transport_api_version: V3
                    failure_mode_allow: false
                    include_peer_certificate: true
                    grpc_service:
                      envoy_grpc: { cluster_name: authorino }
                      timeout: 1s
                - name: envoy.filters.http.router
                  typed_config: {}
                use_remote_address: true
                skip_xff_append: true
      admin:
        access_log_path: "/tmp/admin_access.log"
        address:
          socket_address:
            address: 0.0.0.0
            port_value: 8081
  EOF
  ```

  ```sh
  kubectl patch deployment/news-api \
    --type=strategic \
    --patch '{"spec":{"template":{"spec":{"containers":[{"name":"envoy","image":"envoyproxy/envoy:v1.19-latest","command":["/usr/local/bin/envoy"],"args":["--config-path /usr/local/etc/envoy/envoy.yaml","--service-cluster front-proxy","--log-level info","--component-log-level filter:trace,http:debug,router:debug"],"ports":[{"containerPort":8080}],"volumeMounts":[{"mountPath":"/usr/local/etc/envoy","name":"config","readOnly":true}]}],"volumes":[{"configMap":{"items":[{"key":"envoy.yaml","path":"envoy.yaml"}],"name":"envoy"},"name":"config"}]}}}}' \
    --namespace news-agency
  ```
</details>

<details>
  <summary><b>③ Re-write the service</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: v1
  kind: Service
  metadata:
    name: news-api
    labels:
      app: news-api
  spec:
    selector:
      app: news-api
    ports:
    - name: http
      port: 8080
      protocol: TCP
  EOF
  ```

  ```sh
  kill $PID; kubectl port-forward -n news-agency service/news-api 8080:8080 2>&1 >/dev/null &;PID=$!
  ```
</details>

<details>
  <summary><b>④ Try the application locked down</b></summary>

  ```sh
  curl http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 404 Not Found
  # x-ext-auth-reason: Service not found
  ```
</details>

<details>
  <summary><b>⑤ Create an <code>AuthConfig</code> custom resource</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: authorino.kuadrant.io/v1beta1
  kind: AuthConfig
  metadata:
    name: news-api
  spec:
    hosts:
    - api.news-agency.127.0.0.1.nip.io
    identity:
    - name: api-key-users
      apiKey:
        selector:
          matchLabels:
            app: news-api
      credentials:
        in: authorization_header
        keySelector: API-KEY
    authorization:
    - name: k8s-rbac
      kubernetes:
        user:
          valueFrom:
            authJSON: auth.identity.metadata.annotations.api\.news-agency/username
        resourceAttributes:
          namespace:
            value: news-agency
          group:
            value: api.news-agency/v1
          resource:
            value: news
          verb:
            valueFrom:
              authJSON: context.request.http.method.@case:lower
  EOF
  ```
</details>

## Try the application protected

<details>
  <summary><b>① Try the application missing authentication</b></summary>

  ```sh
  curl http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 401 Unauthorized
  # www-authenticate: API-KEY realm="api-key-users"
  # x-ext-auth-reason: credential not found
  ```
</details>

<details>
  <summary><b>② Create an API key</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: v1
  kind: Secret
  metadata:
    name: api-key-1
    labels:
      authorino.kuadrant.io/managed-by: authorino
      app: news-api
    annotations:
      api.news-agency/username: alice
      api.news-agency/name: Alice Smith
  stringData:
    api_key: ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx
  type: Opaque
  EOF
  ```
</details>

<details>
  <summary><b>③ Try the application missing authorization</b></summary>

  ```sh
  curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 403 Forbidden
  # x-ext-auth-reason: Not authorized
  ```
</details>

<details>
  <summary><b>④ Grant <i>writer</i> access to the user</b></summary>

  Create the roles:

  ```sh
  kubectl -n news-agency apply -f -<<EOF
  apiVersion: rbac.authorization.k8s.io/v1
  kind: Role
  metadata:
    name: news-writer
  rules:
  - apiGroups: ["api.news-agency/v1"]
    resources: ["news"]
    verbs: ["get", "post"]
  ---
  apiVersion: rbac.authorization.k8s.io/v1
  kind: Role
  metadata:
    name: news-admin
  rules:
  - apiGroups: ["api.news-agency/v1"]
    resources: ["news"]
    verbs: ["get", "post", "delete"]
  EOF
  ```

  Bind the user to the role:

  ```sh
  kubectl -n news-agency apply -f -<<EOF
  apiVersion: rbac.authorization.k8s.io/v1
  kind: RoleBinding
  metadata:
    name: news-writers
  roleRef:
    apiGroup: rbac.authorization.k8s.io
    kind: Role
    name: news-writer
  subjects:
  - kind: User
    name: alice
  EOF
  ```
</details>

<details>
  <summary><b>⑤ Try the application with <i>writer</i> access</b></summary>

  Try to read the news articles:

  ```sh
  curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 200 OK
  ```

  Try to create a news article:

  ```sh
  curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' \
       -X POST \
       -d '{"title":"Pegasus spyware was used to hack reporters’ phones. I’m suing its creators","body":"When you’re infected by Pegasus, spies effectively hold a clone of your phone – we’re fighting back (By Nelson Rauda Zablah - The Guardian)"}' \
       http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 200 OK
  ```

  Try to delete a news article:

  ```sh
  article_id=$(curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' http://api.news-agency.127.0.0.1.nip.io:8080/tech -s | jq -r '.[1].id')
  ```

  ```sh
  curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' \
       -X DELETE \
       http://api.news-agency.127.0.0.1.nip.io:8080/tech/$article_id -s -i
  # HTTP/1.1 403 Forbidden
  # x-ext-auth-reason: Not authorized
  ```
</details>

## Extend to service accounts

Extend access to the application to Kubernetes service accounts, authenticated using Kubernetes TokenReview.

<details>
  <summary><b>① Alter the <code>AuthConfig</code> custom resource</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: authorino.kuadrant.io/v1beta1
  kind: AuthConfig
  metadata:
    name: news-api
  spec:
    hosts:
    - api.news-agency.127.0.0.1.nip.io
    identity:
    - name: api-key-users
      apiKey:
        selector:
          matchLabels:
            app: news-api
      credentials:
        in: authorization_header
        keySelector: API-KEY
      extendedProperties:
      - name: username
        valueFrom:
          authJSON: auth.identity.metadata.annotations.api\.news-agency/username
    - name: k8s-tokens
      kubernetes:
        audiences:
        - https://kubernetes.default.svc.cluster.local
      extendedProperties:
      - name: username
        valueFrom:
          authJSON: auth.identity.sub
    authorization:
    - name: k8s-rbac
      kubernetes:
        user:
          valueFrom:
            authJSON: auth.identity.username
        resourceAttributes:
          namespace:
            value: news-agency
          group:
            value: api.news-agency/v1
          resource:
            value: news
          verb:
            valueFrom:
              authJSON: context.request.http.method.@case:lower
  EOF
  ```
</details>

<details>
  <summary><b>② Create a service account</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: v1
  kind: ServiceAccount
  metadata:
    name: news-api-user-janitor
  EOF
  ```
</details>

<details>
  <summary><b>③ Grant <i>admin</i> access to the service account</b></summary>

  ```sh
  kubectl -n news-agency apply -f -<<EOF
  apiVersion: rbac.authorization.k8s.io/v1
  kind: RoleBinding
  metadata:
    name: news-admins
  roleRef:
    apiGroup: rbac.authorization.k8s.io
    kind: Role
    name: news-admin
  subjects:
  - kind: ServiceAccount
    name: news-api-user-janitor
  EOF
  ```
</details>

<details>
  <summary><b>④ Request a token</b></summary>

  ```sh
  access_token=$(kubectl create -n news-agency token news-api-user-janitor)
  ```
</details>

<details>
  <summary><b>⑤ Try the application with <i>admin</i> access</b></summary>

  Try to read the news articles:

  ```sh
  curl -H "Authorization: Bearer $access_token" http://api.news-agency.127.0.0.1.nip.io:8080/tech -s -i
  # HTTP/1.1 200 OK
  ```

  Try to delete a news article:

  ```sh
  curl -H "Authorization: Bearer $access_token" \
       -X DELETE \
       http://api.news-agency.127.0.0.1.nip.io:8080/tech/$article_id -s -i
  # HTTP/1.1 200 OK
  ```
</details>

## Bonus: Inject `author` and `user_id`

<details>
  <summary><b>① Alter the <code>AuthConfig</code> custom resource</b></summary>

  ```sh
  kubectl apply -n news-agency -f -<<EOF
  apiVersion: authorino.kuadrant.io/v1beta1
  kind: AuthConfig
  metadata:
    name: news-api
  spec:
    hosts:
    - api.news-agency.127.0.0.1.nip.io
    identity:
    - name: api-key-users
      apiKey:
        selector:
          matchLabels:
            app: news-api
      credentials:
        in: authorization_header
        keySelector: API-KEY
      extendedProperties:
      - name: username
        valueFrom:
          authJSON: auth.identity.metadata.annotations.api\.news-agency/username
      - name: name
        valueFrom:
          authJSON: auth.identity.metadata.annotations.api\.news-agency/name
    - name: k8s-tokens
      kubernetes:
        audiences:
        - https://kubernetes.default.svc.cluster.local
      extendedProperties:
      - name: username
        valueFrom:
          authJSON: auth.identity.sub
      - name: name
        valueFrom:
          authJSON: auth.identity.serviceaccount.name
    authorization:
    - name: k8s-rbac
      kubernetes:
        user:
          valueFrom:
            authJSON: auth.identity.username
        resourceAttributes:
          namespace:
            value: news-agency
          group:
            value: api.news-agency/v1
          resource:
            value: news
          verb:
            valueFrom:
              authJSON: context.request.http.method.@case:lower
    response:
    - name: x-ext-auth-data
      json:
        properties:
        - name: author
          valueFrom:
            authJSON: auth.identity.name
        - name: user_id
          valueFrom:
            authJSON: auth.identity.username
  EOF
  ```
</details>

<details>
  <summary><b>② Create a news article</b></summary>

  ```sh
  curl -H 'Authorization: API-KEY ndyBzreUzF4zqDQsqSPMHkRhriEOtcRx' \
       -X POST \
       -d '{"title":"Airlines warn of higher fares as industry moves to net zero target","body":"Iata director general Willie Walsh calls for greater production of sustainable aviation fuel (By Reuters)"}' \
       http://api.news-agency.127.0.0.1.nip.io:8080/business -s -i
  # HTTP/1.1 200 OK
  ```
</details>

## Cleanup

<details>
  <summary><b>① Delete the cluster</b></summary>

  ```sh
  kind delete cluster --name demo
  ```
</details>
