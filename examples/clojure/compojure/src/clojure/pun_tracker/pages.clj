
(ns pun-tracker.pages
  (:use net.cgrand.enlive-html
        [datomic.api :only [entity] :as d])
  (:require [pun-tracker.db :as db]
            [clojure.string :as s]))

(defn pun-summary [pun]
  (fn [node]
    (at node
        [:.delete] (set-attr :href
                             (format "/puns/%s/destroy" (:db/id pun)))
        [:.body] (content (:pun/body pun)))))

(defn- site-web-root [attr]
  (fn [node]
    (update-in node
               [:attrs attr]
               #(s/replace % "../../resources/public/" "/assets/"))))

(deftemplate layout
  "index.html"
  [markup]
  [:link] (site-web-root :href)
  [:.design] (substitute markup))

(defsnippet index-content
  "index.html" [:.index-content]
  [pun-eids]
  [:.puns :li] (clone-for [eid pun-eids]
                          (pun-summary
                            (entity (d/db @db/cnn) eid))))

(defsnippet create-content
  "index.html" [:.create-content]
  [body]
  [(attr= :name "body")] (set-attr :value body))

(defsnippet login-content
  "index.html" [:.login-content]
  [])

(defsnippet register-content
  "index.html" [:.register-content]
  [])

;; Public
;; ------

(defn create [req]
  (layout
    (create-content
      (get-in req [:params :body]))))

(defn index [req]
  (layout
    (index-content
      (map first (db/puns)))))

(defn login [req]
  (layout
    (login-content)))

(defn register [req]
  (layout
    (register-content)))

