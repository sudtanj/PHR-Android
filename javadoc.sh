#!/bin/sh

commit_website_files() {
  git branch gh-pages
  git checkout gh-pages
  git checkout master -- javadoc
  rsync -r --ignore-existing ./javadoc/* .       
  rm -rf javadoc
  git add -u *
  git commit --message "Javadoc by Travis. build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
   git push -f origin HEAD:gh-pages
}

commit_website_files
upload_files
