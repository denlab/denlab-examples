(ns simplehttp.reload
  (:use     [org.httpkit.server     :only  [run-server]])
  (:require [ring.middleware.reload :as    reload]
            [compojure.handler      :refer [site]]
            [compojure.core         :refer [defroutes GET POST]]
            [clojure.pprint         :as    pprint]
            [puget.printer          :as    pg]
            )
  )

(defn req-body-eval [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (str (eval (read-string (slurp (:body req)))))})

(defroutes all-routes
  (GET "/" [] "handling-page")
  (POST "/" [data] (str (eval (read-string data))))
  ) ;; all other, return 404


(defn in-dev? [& args] true) ;; TODO read a config variable from command line, env, or file?

(defn -main [& args] ;; entry point, lein run will pick up and start from here
  (let [handler (if (in-dev? args)
                  (reload/wrap-reload (site #'all-routes)) ;; only reload when dev
                  (site all-routes))]
    (run-server handler {:port 18081})
    (println "server started on 18081")))
