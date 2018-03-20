(ns cljhttpproxy.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [tailrecursion.ring-proxy :refer [wrap-proxy]]
))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(defn log [m]
  (println m)
  (spit "/tmp/log" m :append true)
)

(defn wrap-log [handler]
  (fn
    ([request]
     (log request)
     (let [response (handler request)]
       (log response)
       response
       ))
    ([request respond raise]
     (log request)
     (let [response (handler request respond raise)]
       (log response)
       response
       ))))
(log "hi")
(def app
  ;; (-> app-routes
  ;;     (wrap-defaults site-defaults))
  ;; (wrap-defaults app-routes site-defaults)
  (-> app-routes
      wrap-log
      (wrap-proxy "/" "http://jira.fircosoft.net")
      wrap-log
      )
  )
