(defproject flickr2picasa "1.0.0-SNAPSHOT"
  :description "Flickr to Google photo transfer"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
		 [clj-http "0.1.1"]
                 [com.google.gdata/gdata-photos-2.0 "1.41.5"]]
  :dev-dependencies [[swank-clojure "1.2.1"]]
  :repositories {"mandubian-mvn" "http://mandubian-mvn.googlecode.com/svn/trunk/mandubian-mvn/repository"})