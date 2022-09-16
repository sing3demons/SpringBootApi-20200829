# SpringBootApi-20200829

Basically we need to create Docker Network to be a bridged between ZooKeeper and Kafka Docker Containers.

Step 1 — Create Docker Network
```Create Docker Network
docker network create spring-boot-api --driver bridge
```

Step 2 — Launch ZooKeeper container
```2
docker run -d --name zookeeper --network spring-boot-api -p 2181:2181 -e ALLOW_ANONYMOUS_LOGIN=yes bitnami/zookeeper
```

Step 3 — Launch Kafka container

```3
docker run -d --name kafka --network spring-boot-api --hostname localhost -p 9092:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -e KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181 bitnami/kafka
```