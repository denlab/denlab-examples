(ns slackerex.core
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn timestamp
  "return server time in milliseconds"
  []
  (System/currentTimeMillis))

(timestamp)
(use 'slacker.server)
(start-slacker-server [(the-ns 'slackerex.core)] 2104 [:http])


