
(ns pun-tracker.pages
  (:use net.cgrand.enlive-html
        [pun-tracker.session :only [user user-id]]
        [pun-tracker.util :only [in?]])
  (:require [pun-tracker.db :as db]
            [clojure.string :as s]))

(declare flash-success)

(def static-prefix "../../resources/public/")

(def live-prefix "/assets/")

(defn user-has-voted [pun]
  (in? (map :db/id (:pun/votes pun))
       (user-id)))

(defn- site-web-root [attr]
  (fn [node]
    (update-in node
               [:attrs attr]
               #(s/replace % static-prefix live-prefix))))

(defn- flash-message [req]
  (if-let [msg (:flash req)]
    (flash-success msg)))

(defn- file-url [file]
  (format "/file/%s"
          (:db/id file)))

;; Templates and Snippets
;; ----------------------

(defsnippet pun-summary
  "index.html" [:.puns :.pun]
  [pun]
  [:.user] (content (-> pun :pun/added-by :user/email))
  [:.vote] (if (user-has-voted pun)
             (substitute nil)
             identity)
  [:.image] (if (:pun/file pun)
              (set-attr :src (file-url (:pun/file pun)))
              (substitute nil))
  [:.delete :a] (set-attr :href (format "/puns/%s/delete" (:db/id pun)))
  [:.vote :a] (set-attr :href (format "/puns/%s/vote" (:db/id pun)))
  [:.body] (content (:pun/body pun))
  [:.votes :span] (content (-> pun
                               :pun/votes
                               count
                               str)))

(defsnippet flash-success
  "index.html" [:.alert-success]
  [msg]
  [:*] (content msg))

(defsnippet index-content
  "index.html" [:.index-content]
  [pun-eids]
  [:.puns] (content
             (doall
               (map (comp pun-summary db/->entity)
                    pun-eids))))

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
  [:script] (site-web-root :src)
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

