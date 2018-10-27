#!/bin/bash

function command_exists() {
    # which "$@" >/dev/null 2>&1
    command -v "$@" >/dev/null 2>&1
}

function check_command_can_be_execute(){
    [ $# -ne 1 ] && return 1
    command_exists $1
}

CURRENT_DIR=`pwd`
DEPLOY_DIR=~/workspace/monitor-server/gateway/src/main/resources/static/
GATEWAY_DIR=~/workspace/monitor-server/gateway/
BACKUP_DIR=$CURRENT_DIR/backup  
if [ ! -d $BACKUP_DIR ]; then  
    mkdir $BACKUP_DIR  
fi
LOGS_DIR=$CURRENT_DIR/logs  
if [ ! -d $LOGS_DIR ]; then  
    mkdir $LOGS_DIR  
fi
rm -r $BACKUP_DIR/*
echo "move current web package to "$BACKUP_DIR
mv -f $DEPLOY_DIR/* $BACKUP_DIR
echo "check out web package to  "$DEPLOY_DIR
svn --username=guanxd co http://192.168.80.200:8080/svn/bigstorage/trunk/monitor/web/yun/dist  $DEPLOY_DIR 
PIDS=`ps -f -u ${USER} |grep gateway|awk '!/grep/{print $2}'` 
echo "kill existing process ..." 
if [ -n "$PIDS" ]; then
    for PID in $PIDS
    do	
    echo "To kill PID: $PID ..."
    kill $PID
    done
    PIDS=`ps -f -u ${USER} |grep gateway|awk '!/grep/{print $2}'` 
    if [ -n "$PIDS" ]; then
      for PID in $PIDS
      do	
      echo "To kill PID: $PID ..."
      kill -9 $PID
      done
    fi
    PIDS=`ps -f -u ${USER} |grep gateway|awk '!/grep/{print $2}'`
    if [ -n "$PIDS" ]; then 	 
      for PID in $PIDS
      do
      echo "fail to kill PID: $PID ..."
      #kill -9 $PID
      done
      exit 1
    fi
fi  

check_command_can_be_execute mvn
cd ${GATEWAY_DIR}
echo "build package again, pls wait..."
mvn clean install >>${LOGS_DIR}/mvn_build_$(date +%Y%m%d)_$$.log 2>&1
retval=$?
if [ ${retval} -ne 0 ] ; then
    echo "package build failed! More details refer to ${LOGS_DIR}/mvn_build_$(date +%Y%m%d)_$$.log"
    exit 1
else
    echo "package build successfully! "
fi
echo "Restarting ..."
nohup java -Xmx512m -jar ./target/monitor-server-gateway-0.0.1-SNAPSHOT.jar >> /home/xd_guan/logs/monitor-server-gateway-0.0.1-console.log 2>&1 &
PIDS=`ps -f -u ${USER} |grep gateway|awk '!/grep/{print $2}'`
echo "Started, PID: "$PIDS
