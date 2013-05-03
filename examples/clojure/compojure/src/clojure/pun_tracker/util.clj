
(ns pun-tracker.util
  (:use [clojure.pprint :only [pprint]]))

;; Public
;; ------

(defn md5 [string]
  string)

(defn wrap-debug [handler]
  (fn [req]
    (pprint req)
    (handler req)))

