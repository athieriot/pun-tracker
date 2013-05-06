
(defproject pun-tracker "0.0.1"
  :description "Pun-tracker Application in Clojure"
  :dependencies [[org.clojure/clojure "1.5.0"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.8"]
                 [ring/ring-devel "1.1.8"]
                 [enlive "1.1.1"]
                 [fs "1.3.3"]
                 [com.datomic/datomic-free "0.8.3803"]]
  :plugins [[lein-cljsbuild "0.3.0"]]
  :source-paths ["src/clojure" "src/html"]
  :main pun-tracker.core
  :cljsbuild {
    :builds [{:source-paths ["src/cljs"]
              :compiler {:output-to "resources/public/js/main.js"
                         :optimizations :whitespace
                         :pretty-print true}}]
  }
  :hooks [leiningen.cljsbuild])

