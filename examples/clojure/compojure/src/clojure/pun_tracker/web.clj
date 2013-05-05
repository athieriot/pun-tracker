
(ns pun-tracker.web
  (:use compojure.core
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]
        [ring.adapter.jetty :only [run-jetty]]
        [pun-tracker.session :only [wrap-user user]]
        [pun-tracker.db :only [wrap-db]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [pun-tracker.pages :as pages]
            [pun-tracker.actions :as actions]))

(defroutes www-routes
  (GET "/" [] pages/index)

  (GET "/create" [] pages/create)
  (POST "/create" [] actions/create)

  (GET "/login" [] pages/login)
  (POST "/login" [] actions/login)
  (GET "/logout" [] actions/logout)

  (GET "/register" [] pages/register)
  (POST "/register" [] actions/register)

  (GET "/file/:eid" [] actions/file)

  (GET "/puns/:eid/vote" [] actions/vote)
  (GET "/puns/:eid/delete" [] actions/delete)

  (route/resources "/assets"))

(def app-routes
  (routes
    (wrap-user www-routes)
    (route/not-found "404")))

(def app
  (-> #'app-routes
      (wrap-db)
      (wrap-reload)
      (wrap-stacktrace)
      (handler/site)))

;; Public
;; ------

(defn init []
  (run-jetty app
             {:port 3000}))

