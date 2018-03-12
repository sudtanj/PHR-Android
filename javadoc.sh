#!/bin/bash
git checkout gh-pages
git checkout master -- javadoc
rsync -r --ignore-existing ./javadoc/* .       
rm -rf javadoc
git add -u *
git commit -m "updating javadoc"
git push --all