hello:
	echo "Hello"

package:
	./mvnw package

clean:
	./mvnw clean

create-docker-image-using-dockerfile:
	docker build -f Dockerfile -t mortgage-calculator-dockerfile:0.0.2 .

start-dockerfile-image:
	docker run -p8110:8110 mortgage-calculator-dockerfile:0.0.2

create-docker-image-using-spring:
	./mvnw spring-boot:build-image

start-spring-image:
	echo "starting docker image created by spring"
	docker run -p8110:8110 mortgage-calculator:0.0.2-SNAPSHO

tag-spring-image:
	docker tag mortgage-calculator:0.0.2-SNAPSHOT gkovan/mortgage-calculator:latest



