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

echo "** Bumping to version $VERSION"

echo "** Checking if we are on the 'develop' branch"
DEVELOP_BRANCH=`git branch | grep "* develop" | wc -l`
if [ "$DEVELOP_BRANCH" -ne 1 ]
  then
    echo "Not on the 'develop' branch"
    exit 1
fi

read -p "Press enter to continue"

echo "** Building version $VERSION"
cd docs
antora --fetch site.yml

echo "** Push changes to GitHub"
cd ../../jpa-streamer-gh-pages/jpa-streamer

echo "** Checking if 'jpa-streamer-gh-pages/jpa-streamer' is on the 'gh-pages' branch"
DEVELOP_BRANCH=`git branch | grep "* gh-pages" | wc -l`
if [ "$DEVELOP_BRANCH" -ne 1 ]
  then
    echo "Not on the 'develop' branch"
    exit 1
fi
git add --all
git commit -m "Bump version to $VERSION"
git push

echo "Completed!"

