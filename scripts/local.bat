
minikube kubectl -- apply --filename local.yml

minikube kubectl -- expose deployment model-gallery --type=NodePort --port=8080

@REM minikube service model-gallery

minikube kubectl -- port-forward service/model-gallery 8080:8080
