kubectl -- apply --filename local.yml

kubectl -- expose deployment model-gallery --type=NodePort --port=8080

minikube service model-gallery

kubectl -- port-forward service/model-gallery 8080:8080
