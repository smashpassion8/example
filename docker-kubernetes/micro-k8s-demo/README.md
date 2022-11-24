# micro-k8s-demo
# naming pattern
podname-{replica-index}.{serviceName}.default.svc.cluster.local

# kubectl command
kubectl get all
kubectl get pods
kubectl get svc
kubectl get deployments

use get commands to check nothing is running

kubectl apply -f ./

use above kubectl get commands to get all information to see everything is running

kubectl port-forward ===> this is only for local testing. You can use Tool like kube-forwarder

add port forwarding for 
eureka-lb - localport=8761, resource port=80
cloud-gateway-svc - localport=9191, resource port=80
hystrix-dashboard-svc - localport=9295, resource port=80


kubectl scale --replicas=3 -f ./user-service.yml

kubectl delete -f ./

use above get commands to check that they are deleted
# minikube commands
minikube service list
minikube dashboard

# Tools
use https://github.com/pixel-point/kube-forwarder for port-forward in local - not for prod

# How to imporve kubernetes cluster
add health check
liveness pro
readyness pro
gracefull shutdown
resource configuration - how much resource of node or virtual machine can be given to any of the resources
can create different namespace according to different component we have e.g. FrontEnd, Backend or based on environment or based on team name