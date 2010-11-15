(ns flickr
  (:require [clj-http.client :as http]
	    [clojure.zip :as zip]
	    [clojure.contrib.json :as json])
  (:import [java.security MessageDigest]
	   [org.apache.commons.codec.binary Hex]
	   [java.io ByteArrayInputStream]))

(def md5-digest (MessageDigest/getInstance "MD5"))

(defn md5 [s]
  (let [utf8bytes (.getBytes s "UTF-8")
	byte-hash (.digest md5-digest utf8bytes)]
    (String. (Hex/encodeHex byte-hash))))

(defn sorted-param-str [params]
  (let [m (apply sorted-map (interleave (map name (keys params))
			      (vals params)))]
    (apply str (mapcat identity m))))

(defn string-keys [m]
  (zipmap (map name (keys m)) (vals m)))

(defn api-sig [secret params]
  (md5 (str secret (sorted-param-str params))))

(defn flickr-api-call
  ([method params secret]
     (let [params+method (assoc params :method method :format "json" :nojsoncallback 1)
	   params+sig (if secret (assoc params+method :api_sig (api-sig secret params+method))
			  params+method)]
       (http/get "http://api.flickr.com/services/rest"
			     {:query-params (string-keys params+sig) })))
  ([method params]
      (flickr-api-call method params nil)))

(defn response-data [method params secret field]
  (-> (flickr-api-call method params secret) :body json/read-json))

(comment
  (defn token [api-key secret]
    (let [key {:api_key api-key}
	  frob (first (response-field "flickr.auth.getFrob" key secret :frob))]
      (first (response-field "flick.auth.getToken" (assoc key :frob frob) secret :token))))


  (flickr-api-call "flickr.photos.search" {:api_key "40df4672034af04b8c4fc588e6711d37" :user_id "73799316@N00" :per_page 500 })
  
  (response-data "flickr.photos.getSizes" {:photo_id "5162599730" :api_key "40df4672034af04b8c4fc588e6711d37" } nil nil))
