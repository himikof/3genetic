#!/bin/sh
ant build
cp scripts/3genetic.sh deploy
mkdir deploy/lib
cp lib/*.jar deploy/lib
cd deploy
./3genetic.sh
