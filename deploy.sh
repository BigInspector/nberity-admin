#!/bin/bash
set -e
rm -rf build/deploy
mkdir -pv build/deploy
(cd build/deploy; unzip ../libs/nberity-0.0.1-SNAPSHOT.jar)
rsync -vaH --delete build/deploy/ nberity2@nberity.ee:/srv/nberity2/app/
ssh nberity2@nberity.ee sudo -u root /bin/systemctl restart nberity2.service
