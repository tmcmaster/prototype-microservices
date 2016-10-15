#!/bin/bash

# watch input and write to the output of required.
#yaml2json -p -i 4 -s -w $1

yaml2json -p -i 4 -s $*
