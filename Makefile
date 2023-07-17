hello:
	echo "Hello"

package:
	./mvnw package

clean:
	./mvnw clean

run:
	./mvnw spring-boot:run

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

# sets the context so machine can communicate with the eks cluster called eks-workshop
# machine must first have aws credentials
eks-workshop-kubeconfig:
	aws eks update-kubeconfig --region us-east-1 --name eks-workshop

eks-get-grafana-url:
	kubectl get ingress -n grafana grafana -o=jsonpath='{.status.loadBalancer.ingress[0].hostname}'

eks-get-grafana-user:
	kubectl get -n grafana secrets/grafana -o=jsonpath='{.data.admin-user}' | base64 -d

eks-get-grafana-pw:
	kubectl get -n grafana secrets/grafana -o=jsonpath='{.data.admin-password}' | base64 -d

get-mc-pods:
	kubectl get pods -n mc-dev






