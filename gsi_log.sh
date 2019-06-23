#!/bin/bash

while true;
do
	adb wait-for-device; 
	echo "device online             :$(date)"
	adb logcat -b all>$(adb shell getprop ro.product.name)_$(date +%s).txt;
	echo "device offline,waiting... :$(date)";
done
