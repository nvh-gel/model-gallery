minikube kubectl -- apply --filename local.yml

minikube kubectl -- expose deployment model-gallery --type=NodePort --port=8080

minikube service model-gallery

@REM minikube kubectl -- port-forward service/model-gallery 8080:8080
