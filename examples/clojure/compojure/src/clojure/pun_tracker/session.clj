
(ns pun-tracker.session
  (:require [pun-tracker.db :as db]
            [datomic.api :as d]))

(def ^ {:private true :dynamic true}
  *user* nil)

(defn- req-to-user [req]
  (if-let [eid (-> req :session :eid)]
    (d/entity
      (d/db @db/cnn)
      eid)))

;; Public
;; ------

(defn user []
  *user*)

(defn user-id []
  (:db/id *user*))

(defn wrap-user [handler]
  (fn [req]
    (binding [*user* (req-to-user req)]
      (handler req))))

