
(ns pun-tracker.db
  (:use [datomic.api :only [q db] :as d]))

(def uri "datomic:mem://puntracker")

(def cnn (atom nil))

(def schema-tx
  [; puns
   {:db/id #db/id [:db.part/db]
    :db/ident :pun/body
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/fulltext true
    :db.install/_attribute :db.part/db}
   {:db/id #db/id [:db.part/db]
    :db/ident :pun/added-by
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db}
   {:db/id #db/id [:db.part/db]
    :db/ident :pun/voted-for-by
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many
    :db.install/_attribute :db.part/db}
   ; users
   {:db/id #db/id [:db.part/db]
    :db/ident :user/email
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db.install/_attribute :db.part/db}])

(def find-puns-tx
  '[:find ?e
    :where [?e :pun/body]])

(def pun-body-matches
  '[[pun-matches ?text ?e]
    [(fulltext $ :pun/body ?text) [[?e]]]])

(def find-puns-matching-tx
  '[:find ?e
    :in $ % ?text
    :where (pun-matches ?text ?e)])

;; Public
;; ------

(defn puns []
  (q find-puns-tx
     (db @cnn)))

(defn puns-matching [text]
  (q find-puns-matching-tx
     (db @cnn)
     [pun-body-matches]
     text))

(defn init []
  (d/create-database uri)
  (reset! cnn (d/connect uri))
  (d/transact @cnn schema-tx))

(init)

