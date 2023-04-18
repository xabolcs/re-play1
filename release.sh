#!/usr/bin/env sh
set -e

version=$1

if [ -z "$version" ] ; then
  echo "Usage: ./release 2.1.0"
  exit 0
fi

echo "Releasing version: ${version}"
git tag -a "v${version}" -m "released replay ${version}"
git push origin --tags

./gradlew clean check 
./gradlew uitest -Dselenide.headless=true 
./gradlew publishToMavenLocal
./gradlew publish --no-parallel --info