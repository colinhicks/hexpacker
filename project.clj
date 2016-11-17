(defproject colinhicks/hexpacker-stitch-lite "0.1.0-SNAPSHOT"
  :description "Non-geo stitching features of AbleEng/hexpacker ported to cljc."
  :url "https://github.com/colinhicks/hexpacker-stitch-lite/"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.28"]]
  :main ^:skip-aot hexpacker.core
  :target-path "target/%s")
  
