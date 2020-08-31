#!/bin/bash
#
# JPAstreamer - Express JPA queries with Java Streams
# Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
#
# License: GNU Lesser General Public License (LGPL), version 2.1 or later.
#
# This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
# without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
# See the GNU Lesser General Public License for more details.
#
# See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
#

#Fail on any error
set -e

if [ $# -eq 0 ]
  then
    echo "Usage $0: version"
    exit 1
fi
VERSION=$1

mvn versions:set -DnewVersion="$VERSION"
RETURN_BODY="version: $VERSION"
sed -i tmp "s/version:.*\$/$RETURN_BODY/g" docs/antora.yml
rm docs/antora.ymltmp
