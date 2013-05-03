
(ns pun-tracker.web
  (:use compojure.core
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]
        [ring.adapter.jetty :only [run-jetty]]
        [pun-tracker.util :only [wrap-debug]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [pun-tracker.pages :as pages]
            [pun-tracker.actions :as actions]))

(defroutes app-routes
  (GET "/" [] pages/index)

  (GET "/create" [] pages/create)
  (POST "/create" [] actions/create)

  (GET "/login" [] pages/login)
  (POST "/login" [] actions/login)

  (GET "/register" [] pages/register)
  (POST "/register" [] actions/register)

  (route/resources "/assets"))

(def app
  (-> #'app-routes
      (wrap-debug)
      (wrap-reload)
      (wrap-stacktrace)
      (handler/site :session)))

;; Public
;; ------

(defn init []
  (run-jetty app
             {:port 3000}))

