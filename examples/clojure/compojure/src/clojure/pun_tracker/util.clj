
(ns pun-tracker.util
  (:use [clojure.pprint :only [pprint]]))

;; Public
;; ------

(defn md5
  "Generate a md5 checksum for the given string (https://gist.github.com/eliasson/1302024)"
  [token]
  (let [hash-bytes
         (doto (java.security.MessageDigest/getInstance "MD5")
               (.reset)
               (.update (.getBytes token)))]
       (.toString
         (new java.math.BigInteger 1 (.digest hash-bytes)) ; Positive and the size of the number
         16)))

(defn in?
  [lst elm]
  (some #(= elm %) lst))

