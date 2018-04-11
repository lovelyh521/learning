#!/bin/bash
scp -P 10022 root@10.129.84.10:/home/justlogin/*.rpm ./

# - [kubeadm-1.9.0-0.x86_64.rpm](https://packages.cloud.google.com/yum/pool/aa9948f82e7af317c97a242f3890985159c09c183b46ac8aab19d2ad307e6970-kubeadm-1.9.0-0.x86_64.rpm)
#- [kubectl-1.9.0-0.x86_64.rpm](https://packages.cloud.google.com/yum/pool/bc390a3d43256791bfb844696e7215fd7ad8a09f70a42667dab4997415a6ba75-kubectl-1.9.0-0.x86_64.rpm)
#- [kubelet-1.9.0-0.x86_64.rpm](https://packages.cloud.google.com/yum/8f507de9e1cc26e5b0043e334e26d62001c171d8e54d839128e9bade25ecda95-kubelet-1.9.0-0.x86_64.rpm)
#  安装kubeadm还需要依赖kubernetes-cni
#- [kubernetes-cni-0.6.0-0.x86_64.rpm](https://packages.cloud.google.com/yum/fe33057ffe95bfae65e2f269e1b05e99308853176e24a4d027bc082b471a07c0-kubernetes-cni-0.6.0-0.x86_64.rpm)
# 依赖tebtables socat，不需要翻墙
yum install -y ebtables
yum install -y socat
rpm -ivh *.rpm

setenforce 0

cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system

#docker info | grep -i cgroup
#cat /etc/systemd/system/kubelet.service.d/10-kubeadm.conf

#sed -i "s/cgroup-driver=systemd/cgroup-driver=cgroupfs/g" /etc/systemd/system/kubelet.service.d/10-kubeadm.conf
#systemctl daemon-reload
#systemctl restart kubelet

# 将镜像打包到gcr.io/google_containers
KUBE_VERSION=v1.9.0
KUBE_PAUSE_VERSION=3.0
ETCD_VERSION=3.1.10
DNS_VERSION=1.14.7

GCR_URL=xiejieyi
DEST_URL=gcr.io/google_containers

images=(kube-proxy-amd64:${KUBE_VERSION}
kube-scheduler-amd64:${KUBE_VERSION}
kube-controller-manager-amd64:${KUBE_VERSION}
kube-apiserver-amd64:${KUBE_VERSION}
pause-amd64:${KUBE_PAUSE_VERSION}
etcd-amd64:${ETCD_VERSION}
k8s-dns-sidecar-amd64:${DNS_VERSION}
k8s-dns-kube-dns-amd64:${DNS_VERSION}
k8s-dns-dnsmasq-nanny-amd64:${DNS_VERSION})
len=${#images[@]}
for ((i=0;i<$len;i++));do
 echo ${images[$i]}
 docker pull $GCR_URL/${images[$i]}
 docker tag $GCR_URL/${images[$i]} $DEST_URL/${images[$i]}
 #docker rmi $GCR_URL/$imageName
done