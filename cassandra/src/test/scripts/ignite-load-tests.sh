#!/bin/sh
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

profile=/$1/.bash_profile
. $profile

TESTS_ROOT=$(readlink -m $( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd ))
TESTS_CLASSPATH="$TESTS_ROOT/lib/*:$TESTS_ROOT/settings"

java -cp "$TESTS_CLASSPATH" "org.apache.ignite.activestore.load.ActiveCacheStoreLoadTest"

if [ $? -ne 0 ]; then
    echo
    echo "--------------------------------------------------------------------------------"
    echo "[ERROR] Tests execution failed"
    echo "--------------------------------------------------------------------------------"
    echo
    exit 1
fi

echo
echo "--------------------------------------------------------------------------------"
echo "[INFO] Tests execution succeed"
echo "--------------------------------------------------------------------------------"
echo
