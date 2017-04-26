#!/bin/bash

################################################################################
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
################################################################################

exists() {
    type $1 > /dev/null 2>&1
}

INFO(){
    /bin/echo $'\e[104m\e[97m[INFO]\e[49m\e[39m' $@
}

ERROR(){
    /bin/echo $'\e[101m\e[97m[ERROR]\e[49m\e[39m' $@
}

exists ssh-keygen || { ERROR "Please install ssh-keygen"; exit 1; }

if [ ! -f ./secret/node.env ]; then
    INFO "Generating key pair"
    ssh-keygen -t rsa -N "" -f ./secret/id_rsa -C "epam.com"

    INFO "Generating ./secret/control.env"
    echo '# generated by lagerta-jepsen/docker/build.sh' > ./secret/control.env
    echo '# NOTE: \\n is expressed as ↩' >> ./secret/control.env
    echo SSH_PRIVATE_KEY="$(cat ./secret/id_rsa | sed -e ':a' -e 'N' -e '$!ba' -e 's/\n/↩/g')" >> ./secret/control.env
    echo SSH_PUBLIC_KEY=$(cat ./secret/id_rsa.pub) >> ./secret/control.env

    INFO "Generating ./secret/node.env"
    echo '# generated by lagerta-jepsen/docker/build.sh' > ./secret/node.env
#    echo ROOT_PASS=root >> ./secret/node.env
    echo AUTHORIZED_KEYS=$(cat ./secret/id_rsa.pub) >> ./secret/node.env
else
    INFO "No need to generate key pair"
fi

exists docker || { ERROR "Please install docker"; exit 1; }
exists docker-compose || { ERROR "Please install docker-compose"; exit 1; }

INFO "Building images"
docker build -t java-base base/.
docker build -t jepsen-control jepsen-control/.
#docker build -t subscriber subscriber/.
#docker build -t publisher publisher/.
