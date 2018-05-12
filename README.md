# Micrometer-spring-boot-sandbox

The project gives you the opportunity to run sample application, library in this case, to try out the possibilities offered by the
combining spring-boot, micrometer(with prometheus) and grafana technologies.

The application has defined for you four endpoints:

```
/book/add
/book/find
/book/borrow
/book/return
```
and a scheduler that calls the above endpoints to create metrics that can be seen in the application itself,
prometheus and grafana (or any metrics source that you can configure with micrometer). 
By default application creates 100 valid and invalid requests request every 20 seconds, those values can be
changed in the `application.properties` file and in the `@Scheduler` annotation located in `MetricsValuesProducer` class.

To play with Micrometer framework, you can define your own metrics under the `MetricsRegistry` class, as you can see 
there are two custom metrics defined already. To know more about Micrometer metrics registry and concepts follow the link:
`https://micrometer.io/docs/concepts`

## Getting Started

```
git clone https://github.com/jedrzejserwa/micrometer-spring-boot-sandbox.git
```

### Prerequisites

```
Java 8
Docker
Docker-compose
```

### Installing

Application only

```
cd micrometer-spring-boot-sandbox
./mvnw clean spring-boot:run
```

Application, prometheus and grafana

```
cd micrometer-spring-boot-sandbox
docker-compose up
```

## Usage

### Application metrics

```
localhost:8080/actuator/metrics
localhost:8080/actuator/prometheus
```

### Prometheus metrics

```
localhost:9090
```

### Grafana

_initial configuration of the source metric is needed_
```
localhost:3000

user: admin
password: admin
```

## Notes

* Before running ```docker-compose up``` it might be necessary to edit prometheus.yml file under /docker/env directory with
```- targets:``` sequence pointed into your local machine
* Sample micrometer dashboard for Grafana `https://grafana.com/dashboards/4701`

## Built With

* [Spring-boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Micrometer](https://micrometer.io) - Application metrics facade
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/get-docker) - Software containerizing platform

## Authors

* **Jedrzej Serwa** - [jedrzejserwa](https://github.com/jedrzejserwa)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details 
