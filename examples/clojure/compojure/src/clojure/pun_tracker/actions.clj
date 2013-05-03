
(ns pun-tracker.actions
  (:require [datomic.api :as d]
            [pun-tracker.pages :as pages]
            [pun-tracker.db :as db]
            [ring.util.response :as response]))

(defn- req-to-pun [{:keys [params]}]
  {:db/id (d/tempid :db.part/user)
   :pun/body (:body params)})

;; Public
;; ------

(defn create [req]
  (let [pun-tx (req-to-pun req)]
    (d/transact @db/cnn [pun-tx])
    (response/redirect "/")))

