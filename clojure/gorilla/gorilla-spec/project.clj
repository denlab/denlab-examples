(defproject gorilla-spec "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [gorilla-repl "0.4.0" :exclusions [ring/ring-json compojure]]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 ]
  :plugins [[lein-gorilla "0.4.0"]]
  :main ^:skip-aot gorilla-spec.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
