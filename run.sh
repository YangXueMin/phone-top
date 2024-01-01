#!/bin/sh
# 定义应用名称
app_name='party-building'
# 定义应用版本
app_version='latest'
echo '----copy jar----'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi ${app_name}:${app_version}
echo '----rm image----'
# 打包编译docker镜像
docker build -t ${app_name}:${app_version} .
echo '----build image----'
docker run -p 8080:8080 --name ${app_name} \
-e TZ="Asia/Shanghai" \
-v /etc/localtime:/etc/localtime \
-d ${app_name}:${app_version}
echo '----start container----'