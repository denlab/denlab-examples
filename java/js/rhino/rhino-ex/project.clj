(defproject jd-ex "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [org.mozilla/rhino "1.7.10"]
                 ]
  :main ^:skip-aot jd-ex.core
  :target-path "target/%s"
    :profiles {:uberjar {:aot :all}})
