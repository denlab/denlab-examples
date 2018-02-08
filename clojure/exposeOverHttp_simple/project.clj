(defproject simplehttp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [mvxcvi/puget "1.0.2"]

                 [compojure "1.1.5"]
                 [ring/ring-devel "1.1.8"]
                 [ring/ring-core "1.1.8"]
                 ;; [http-kit "2.0.0"]

                 [http-kit "2.3.0-alpha5"]
                 ;; [ring/ring-devel "1.6.3"]
                 ;; [ring/ring-core "1.6.3"]
                 ;; [compojure "1.6.0"]

                 ]
  :main simplehttp.reload 
  )
