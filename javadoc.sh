#!/bin/sh

setup_git() {
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis CI"
  git config remote.origin.url https://$GITHUB_TOKEN@github.com/sudtanj/PHR-Android.git
}

commit_website_files() {
  git checkout gh-pages
  git checkout master -- javadoc
  rsync -r --ignore-existing ./javadoc/* .       
  rm -rf javadoc
  git add -u *
  git commit --message "Javadoc by Travis. build: $TRAVIS_BUILD_NUMBER"
}

upload_files() {
   git push origin HEAD:gh-pages
}

setup_git
commit_website_files
upload_files
