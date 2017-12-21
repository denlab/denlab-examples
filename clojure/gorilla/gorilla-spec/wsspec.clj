;; gorilla-repl.fileformat = 1

;; **
;;; # Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns gorilla-spec
  (:require [clojure.spec.alpha :as s])
  (:require [gorilla-plot.core :as plot]
            [clojure.repl       :as repl]
            )
  
  )

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/conform even? 1000)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-long'>1000</span>","value":"1000"}
;; <=

;; @@
(s/valid? even? 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? nil? nil)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? string? "abc")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? #(> % 5) 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? #(< % 5) 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(import java.util.Date)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-class'>java.util.Date</span>","value":"java.util.Date"}
;; <=

;; @@
(s/valid? inst? (Date.))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? #{:club :diamond :heart :spade} :club)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? #{:club :diamond :heart :spade} 42)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; **
;;; # Registry
;; **

;; @@
(s/def ::date inst?)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/date</span>","value":":gorilla-spec/date"}
;; <=

;; @@
(s/def ::suit #{:club :diamond :heart :space})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/suit</span>","value":":gorilla-spec/suit"}
;; <=

;; @@
(s/valid? ::date (Date.))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/conform ::suit :club)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:club</span>","value":":club"}
;; <=

;; @@
(repl/doc ::date)
;; @@
;; ->
;;; -------------------------
;;; :gorilla-spec/date
;;; Spec
;;;   inst?
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(repl/doc ::suit)
;; @@
;; ->
;;; -------------------------
;;; :gorilla-spec/suit
;;; Spec
;;;   #{:space :heart :diamond :club}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
1
;; @@
