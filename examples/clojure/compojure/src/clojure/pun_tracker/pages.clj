
(ns pun-tracker.pages
  (:use net.cgrand.enlive-html
        [datomic.api :only [entity] :as d]
        [pun-tracker.session :only [user]])
  (:require [pun-tracker.db :as db]
            [clojure.string :as s]))

(declare flash-message)

(def static-prefix "../../resources/public/")

(def live-prefix "/assets/")

(defn pun-summary [pun]
  (fn [node]
    (at node
        [:.delete] (set-attr :href
                             (format "/puns/%s/destroy" (:db/id pun)))
        [:.vote] (set-attr :href
                           (format "/puns/%s/vote" (:db/id pun)))
        [:.body] (content (:pun/body pun)))))

(defn- site-web-root [attr]
  (fn [node]
    (update-in node
               [:attrs attr]
               #(s/replace % static-prefix live-prefix))))

(defn- flash-message [req]
  (if-let [msg (:flash req)]
    (flash-success msg)))

;; Templates and Snippets
;; ----------------------

(defsnippet flash-success
  "index.html" [:.alert-success]
  [msg]
  [:*] (content msg))

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

(deftemplate layout
  "index.html"
  [req markup]
  [:body] (add-class (if (user) "logged-in" ""))
  [:link] (site-web-root :href)
  [:.design] (substitute markup)
  [:.container] (prepend (flash-message req)))

;; Public
;; ------

(defn create [req]
  (layout req
    (create-content
      (get-in req [:params :body]))))

(defn index [req]
  (layout req
    (index-content
      (map first (db/puns)))))

(defn login [req]
  (layout req
    (login-content)))

(defn register [req]
  (layout req
    (register-content)))

