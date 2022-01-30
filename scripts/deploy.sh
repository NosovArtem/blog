#!/usr/bin/env bash

mvn clean package
#----------------------------------------------------------------------------------
echo 'Copy files...'

scp -P 11169 -i ~/.ssh/id_rsa \
    /home/art/projects/blog/target/blog-1.0-SNAPSHOT.jar  \
    art@192.168.0.112:/home/art/
#----------------------------------------------------------------------------------
echo 'Restart server...'

ssh -p 11169 -i ~/.ssh/id_rsa art@192.168.0.112 << EOF
pgrep java | xargs kill -9
nohup java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar blog-1.0-SNAPSHOT.jar > log.txt &
EOF
#----------------------------------------------------------------------------------
echo 'Bye'