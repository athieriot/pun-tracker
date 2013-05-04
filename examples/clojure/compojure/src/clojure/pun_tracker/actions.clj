
(ns pun-tracker.actions
  (:use [pun-tracker.session :only [user-id]])
  (:require [datomic.api :as d]
            [pun-tracker.pages :as pages]
            [pun-tracker.db :as db]
            [pun-tracker.util :as util]
            [ring.util.response :as response]))

(defn- req-to-pun [{:keys [params]}]
  {:db/id (d/tempid :db.part/user)
   :pun/body (:body params)
   :pun/added-by (user-id)})

(defn- req-to-user [{:keys [params]}]
  {:db/id (d/tempid :db.part/user)
   :user/email (:email params)
   :user/password (util/md5 (:password params))})

(defn- req-to-eid [req]
  (Long/parseLong
    (-> req :params :eid)))

(defn- go-home [msg]
  (-> (response/redirect "/")
      (assoc :flash msg)))

(defn- login* [{:keys [params] :as req}]
  (if-let [eid (db/user (:email params)
                        (:password params))]
    (-> (go-home "Logged in!")
        (assoc-in [:session]
                  {:eid eid}))
    (pages/login req)))

;; Public
;; ------

(defn vote [req]
  (let [eid (req-to-eid req)
        tx [:db/add eid :pun/votes (user-id)]]
    (d/transact @db/cnn [tx])
    (go-home "Vote Cast")))

(defn delete [req]
  (let [eid (req-to-eid req)
        tx [:db.fn/retractEntity eid]]
    (d/transact @db/cnn [tx])
    (go-home "Pun deleted")))

(def login login*)

(defn logout [req]
  (-> (go-home "Logged out")
      (assoc :session {:eid nil})))

(defn register [req]
  (let [user-tx (req-to-user req)]
    (d/transact @db/cnn [user-tx])
    (login* req)))

(defn create [req]
  (let [pun-tx (req-to-pun req)]
    (d/transact @db/cnn [pun-tx])
    (go-home "Pun created!")))

