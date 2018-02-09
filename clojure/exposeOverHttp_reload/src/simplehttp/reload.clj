(ns simplehttp.reload
  (:use     [org.httpkit.server     :only  [run-server]])
  (:require [ring.middleware.reload :as    reload]
            [compojure.handler      :refer [site]]
            [compojure.core         :refer [defroutes GET POST]]
            [clojure.pprint         :as    pprint]
            [puget.printer          :as    pg]
            [clj-http.client        :as    cli]
            [environ.core           :as    env]
            [clojure.string         :as    str]
            )
  ;; (:gen-class)
  )

(defn q []
  (let [host (env/env :jira-host)
        user (env/env :jira-user)
        pwd  (env/env :jira-password)
        auth [user pwd]
        args {:basic-auth auth, :as :json}
        url  (str "http://" host "/rest/api/latest/user/search?startAt=0&maxResults=1000&username=")
        ]
    ;; (pg/cprint url {:basic-auth [user pwd]})
    ;; (pg/cprint [args url])
    (:body (cli/get url args))
       ))

(defn req-body-eval [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (str (eval (read-string (slurp (:body req)))))})

(defroutes all-routes
  (GET "/" [] "handling-page")
  (POST "/" [data] (str (eval (read-string data))))
  ;; (GET "/save" [] handler)     ;; websocket
  ;; (route/not-found "<p>Page not found.</p>")
  ) ;; all other, return 404


;; {:status  200
;;  :headers {"Content-Type" "text/html"}
;;  :body   (str (eval (read-string (slurp (:body req)))))}

(defn in-dev? [& args] true) ;; TODO read a config variable from command line, env, or file?

(defn -main [& args] ;; entry point, lein run will pick up and start from here
  (let [handler (if (in-dev? args)
                  (reload/wrap-reload (site #'all-routes)) ;; only reload when dev
                  (site all-routes))]
    (run-server handler {:port 18081})
    (println "server started on 18081")))

#_(
   (-main) 

   )
