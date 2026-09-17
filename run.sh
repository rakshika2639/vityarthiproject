#!/usr/bin/env bash
set -e
rm -rf out
mkdir -p out
find src/main/java -name '*.java' -print0 | xargs -0 javac -d out
java -cp out com.vityarthi.campuspulse.App
