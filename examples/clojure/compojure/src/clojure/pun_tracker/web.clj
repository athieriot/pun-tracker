
(ns pun-tracker.web
  (:use compojure.core
        net.cgrand.enlive-html
        [ring.middleware.reload :only [wrap-reload]]
        [ring.middleware.stacktrace :only [wrap-stacktrace]]
        [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [pun-tracker.pages :as pages]
            [pun-tracker.actions :as actions]))

(defroutes app-routes
  (GET "/" [] pages/index)
  (GET "/create" [] pages/create)
  (POST "/create" [] actions/create)
  (route/resources "/assets"))

(def app
  (-> #'app-routes
      (wrap-reload)
      (wrap-stacktrace)
      (handler/site)))

;; Public
;; ------

(defn init []
  (run-jetty app
             {:port 3000}))

