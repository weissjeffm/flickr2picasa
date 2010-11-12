(ns flickr
  (:require [clj-http.client :as http])
  (:import [java.security MessageDigest]
	   [org.apache.commons.codec.binary Hex]))

(defn flickr-api-call
  [method params]
  (http/get "http://api.flickr.com/services/rest"
	    {:query-params (assoc params :method method) }))

(def md5-digest (MessageDigest/getInstance "MD5"))

(defn md5 [s]
  (let [utf8bytes (.getBytes s "UTF-8")
	byte-hash (.digest md5-digest utf8bytes)]
    (String. (Hex/encodeHex byte-hash))))

(comment (defn sorted-params [params]
	  (sorted-map) ))