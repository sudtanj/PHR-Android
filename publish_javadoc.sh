#!/bin/bash
  echo -e "Publishing jsdoc...\n"

  cp -R "sudtanj/PHR-Android/2.0.5" $HOME/jsdoc-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/sudtanj/PHR-Android gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./jsdoc
  cp -Rf $HOME/jsdoc-latest ./jsdoc
  git add -f .
  git commit -m "Latest jsdoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc to gh-pages.\n"
 