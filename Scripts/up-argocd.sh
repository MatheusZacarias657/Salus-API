kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

kubectl -n argocd scale statefulset/argocd-application-controller --replicas 1
kubectl -n argocd scale deployment/argocd-dex-server --replicas 0
kubectl -n argocd scale deployment/argocd-repo-server --replicas 0
kubectl -n argocd scale deployment/argocd-server --replicas 1
kubectl -n argocd scale deployment/argocd-redis --replicas 0
kubectl -n argocd scale deployment/argocd-applicationset-controller --replicas 1
kubectl -n argocd scale deployment/argocd-notifications-controller --replicas 0

kubectl get secrets argocd-initial-admin-secret -n argocd -o yaml

kubectl port-forward svc/argocd-server 8080:443 -n argocd