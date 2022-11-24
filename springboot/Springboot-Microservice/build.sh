#!/bin/bash
export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.16.1_1/libexec/openjdk.jdk/Contents/Home
# mvn --version

find . -type f -name "pom.xml" | while read line;
  do
    echo found file at $line
    len=${#line}
    pomLen=$((len-8))
    pomDir=${line:0:pomLen}
    cd $pomDir
    mvn clean install
    if [ $? -eq 0 ]; then
      ttab -t  eval "mvn spring-boot:run"
    else
      echo "Fail for ${pomDir:2}"
    fi
    # echo $pomDir
    # echo "${pomDir:7}"
    # dirlen=${#pomDir}
    # echo $dirlen
    # echo "${pomDir:2}"
    break
  done;



StringVal="service-registry cloud-config-server	user-service department-service cloud-gateway hystrix-dashboard"

for projName in $StringVal; do
    if [ $? -eq 0 ]; then
      ttab -t  eval "export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.16.1_1/libexec/openjdk.jdk/Contents/Home; mvn --version; mvn -f $projName spring-boot:run"
    else
      echo "Fail for $projName"
    fi
done