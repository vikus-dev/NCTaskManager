#!/bin/bash

[ ! -d docs ] && mkdir docs

javadoc -d docs -sourcepath src/main/java/ -subpackages ua
