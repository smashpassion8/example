#!/bin/bash
export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.16.1_1/libexec/openjdk.jdk/Contents/Home
# mvn --version

# find . -type f -name "pom.xml" | while read line;
#   do
#     echo found file at $line
#     len=${#line}
#     pomLen=$((len-8))
#     pomDir=${line:0:pomLen}
#     cd $pomDir
#     # ttab -t 'How Green Was My Valley'
#     # ttab -t echo "\033]0;${1}\007\c"
#     # mvn clean
#     mvn clean package dockerfile:push
#     # if [ $? -eq 0 ]; then
#     #   ttab -t  eval "mvn spring-boot:run"
#     # else
#     #   echo "Fail for ${pomDir:2}"
#     # fi
#     # echo $pomDir
#     # echo "${pomDir:7}"
#     # dirlen=${#pomDir}
#     # echo $dirlen
#     # echo "${pomDir:2}"
#     break
#   done;


# mvn -f service-registry clean install

# projName="service-registry"
# if [ $? -eq 0 ]; then
#   ttab -t  eval "export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.16.1_1/libexec/openjdk.jdk/Contents/Home; mvn --version; mvn -f $projName spring-boot:run"
# else
#   echo "Fail for $projName"
# fi

proj_arr=(service-registry cloud-config-server	user-service department-service cloud-gateway hystrix-dashboard)

for i in ${!proj_arr[@]}; do
  # if  [ $i -gt 0 ]
  # then
  #   break
  # fi

  echo "element $i is ${proj_arr[$i]}"
  mvn clean package dockerfile:push -f ${proj_arr[$i]}
done

# for projName in $StringVal; do
#     if [ $? -eq 0 ]; then
#       # ttab -t  eval "export JAVA_HOME=/usr/local/Cellar/openjdk@11/11.0.16.1_1/libexec/openjdk.jdk/Contents/Home; mvn --version; mvn -f $projName spring-boot:run"
#       mvn clean package dockerfile:push
#     else
#       echo "Fail for $projName"
#     fi
# done