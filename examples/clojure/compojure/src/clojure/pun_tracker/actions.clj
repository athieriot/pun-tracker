
(ns pun-tracker.actions
  (:use [pun-tracker.session :only [user-id]])
  (:require [datomic.api :as d]
            [fs.core :as fs]
            [pun-tracker.pages :as pages]
            [pun-tracker.db :as db]
            [pun-tracker.util :as util]
            [ring.util.response :as res]))

(defn- ->file-tx [file]
  {:db/id (d/tempid :db.part/user)
   :file/path (:path file)
   :file/mimeType (:content-type file)})

(defn- ->pun-tx [{:keys [params]} file-tx]
  {:db/id (d/tempid :db.part/user)
   :pun/body (:body params)
   :pun/added-by (user-id)
   :pun/file (:db/id file-tx)})

(defn- ->user-tx [{:keys [params]}]
  {:db/id (d/tempid :db.part/user)
   :user/email (:email params)
   :user/password (util/md5 (:password params))})

(defn- ->eid [req]
  (Long/parseLong
    (-> req :params :eid)))

(defn- upload [req image]
  (let [file (-> req :params image)
        tmp (:tempfile file)
        path (format "resources/images/%s"
                     (util/md5 (.getAbsolutePath tmp)))]
    (fs/copy tmp path)
    (merge file {:path path})))

(defn- go-home [msg]
  (-> (res/redirect "/")
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

(defn file [req]
  (let [eid (->eid req)
        file (db/->entity eid)]
    (-> (res/file-response (:file/path file))
        (res/content-type (:file/mimeType file)))))

(defn vote [req]
  (let [eid (->eid req)
        tx [:db/add eid :pun/votes (user-id)]]
    (d/transact @db/cnn [tx])
    (go-home "Vote Cast")))

(defn delete [req]
  (let [eid (->eid req)
        tx [:db.fn/retractEntity eid]]
    (d/transact @db/cnn [tx])
    (go-home "Pun deleted")))

(def login login*)

(defn logout [req]
  (-> (go-home "Logged out")
      (assoc :session {:eid nil})))

(defn register [req]
  (let [user-tx (->user-tx req)]
    (d/transact @db/cnn [user-tx])
    (login* req)))

(defn create [req]
  (let [file-tx (->file-tx
                  (upload req :image))
        pun-tx (->pun-tx req file-tx)]
    (d/transact @db/cnn [file-tx pun-tx])
    (go-home "Pun created!")))

