#!/bin/sh
cd `dirname $0`
expect -c "
spawn scp -rp ./SendServer/ francis@172.17.136.121:/home/francis/GMS测试报告
expect \"francis@172.17.136.121's password:\"
send \"autotest123.\r\"
interact
"
