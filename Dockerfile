FROM azul/zulu-openjdk:17-latest
LABEL authors="flolec"

COPY target/blockly-trader-0.0.1-SNAPSHOT.jar /

CMD ["java", "-jar", "blockly-trader-0.0.1-SNAPSHOT.jar"]
