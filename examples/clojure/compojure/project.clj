
(defproject pun-tracker "0.0.1"
  :description "Pun-tracker Application in Clojure"
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [ring/ring-devel "1.1.8"]
                 [enlive "1.1.1"]
                 [com.datomic/datomic-free "0.8.3803"]]
  :source-paths ["src/clojure" "src/html"]
  :main pun-tracker.core)

