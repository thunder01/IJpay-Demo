FROM mamohr/centos-java

#修改容器的时区设置
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

#将编译好的springboot jar包复制进容器中
ADD ./target/IJPay-Demo-0.0.1-SNAPSHOT.jar IJPay-Demo-0.0.1-SNAPSHOT.jar

#容器暴露的端口
EXPOSE 80

#启动容器时运行jar包
ENTRYPOINT ["java","-jar","IJPay-Demo-0.0.1-SNAPSHOT.jar"]