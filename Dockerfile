FROM eclipse-temurin:17
EXPOSE 8110
ARG JAR_FILE=target/mortgage-calculator-0.0.2-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

ADD https://github.com/aws-observability/aws-otel-java-instrumentation/releases/download/v1.21.1/aws-opentelemetry-agent.jar /lib/aws-opentelemetry-agent.jar
ENV JAVA_TOOL_OPTIONS "-javaagent:/lib/aws-opentelemetry-agent.jar"

# OpenTelemetry agent configuration
ENV OTEL_TRACES_SAMPLER "always_on"
ENV OTEL_PROPAGATORS "tracecontext,baggage,xray"
ENV OTEL_RESOURCE_ATTRIBUTES "service.name=MortgageCalculator"
ENV OTEL_IMR_EXPORT_INTERVAL "10000"
ENV OTEL_EXPORTER_OTLP_ENDPOINT "http://localhost:4317"

ENTRYPOINT ["java","-jar","/app.jar"]