Weld Core MicroBenchmarks
=========================

To run the benchmark execute

> mvn clean package -PWeld

To override Weld version

> mvn clean package -PWeld -Dweld.version=2.2.7-SNAPSHOT

To run graph generator

> mvn clean package -Dgraph=2.2.7-SNAPSHOT.csv,3.0.0-SNAPSHOT.csv