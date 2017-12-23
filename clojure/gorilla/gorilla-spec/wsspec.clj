;; gorilla-repl.fileformat = 1

;; **
;;; # Clojure Spec Guide with Gorilla REPL
;;; 
;;; - Clojure Spec Guide
;;; 
;;;     https://clojure.org/guides/spec
;;; 
;;; - Gorilla REPL
;;; 
;;; Welcome to gorilla :-)
;;; 
;;; Shift + enter evaluates code. Hit alt+g twice in quick succession or click the menu icon (upper-right corner) for more commands ...
;;; 
;;; It's a good habit to run each worksheet in its own namespace: feel free to use the declaration we've provided below if you'd like.
;; **

;; @@
(ns gorilla-spec
  (:require
   [clojure.repl       :as repl ]
   [clojure.spec.alpha :as s    ]
   [clojure.pprint :as pp   ]
   [clojure.walk   :as w]
   [gorilla-plot.core  :as plot ]
   [gorilla-repl.html  :as html ]
   [gorilla-repl.table :as table]
   [hiccup.core        :as hc   ]
   )
  )
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; # test `gorilla-repl.html` + `hiccup.core`
;; **

;; @@
(html/html-view 
  (hc/html [:table [:tr [:td "a"] [:td "b"]
                   [:tr [:td "c"] [:td "d"]]]])
  )

;; @@
;; =>
;;; {"type":"html","content":"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>","value":"#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}"}
;; <=

;; **
;;; ## nested tables
;; **

;; @@

(def hv (html/html-view 
  (hc/html [:table [:tr [:td "a"] [:td "b"]
                   [:tr [:td "c"] [:td "d"]]]])))

  (table/table-view [[hv hv][hv hv]])

;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>","value":"#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}"},{"type":"html","content":"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>","value":"#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}"}],"value":"[#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"} #gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>","value":"#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}"},{"type":"html","content":"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>","value":"#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}"}],"value":"[#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"} #gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"} #gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}] [#gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"} #gorilla_repl.html.HtmlView{:content \"<table><tr><td>a</td><td>b</td><tr><td>c</td><td>d</td></tr></tr></table>\"}]], :opts nil}"}
;; <=

;; **
;;; ## more nested tables (inprogress)
;; **

;; @@
(defn count-rec [x] (if (pos? x) [(count-rec (dec x))] [0]))
(count-rec 3)
(defn count-rec [x] (if (pos? x)
                      (let [y (dec x)]
                        [
                          [(count-rec y)] [(count-rec y)]	
                          [(count-rec y)] [(count-rec y)]	
                          ])
                      [0]
                      )
  )
(defn count-rec [x] (if (pos? x)
                      (let [y (dec x)]
                        (into [] (repeat 2 (into [] (repeat 2 [(count-rec y)]))))
                        )
                      "a"
                      )
  )
#_(pp/pprint (count-rec 2))
(defn mknest [x] (if (pos? x)
                      (let [y (dec x)]
                        (into []
                          (repeat 2 
                                  (into []
                                  (repeat 2 
                                          (mknest y))))))
                        [
                          [1 2] 
                          [3 4]
                          ] 
                      )
  )
(pp/pprint (mknest 2))
(defn count-rec [x] (if (pos? x)
                      (let [y (dec x)]
                        (table/table-view
                          (repeat 2 
                                  (repeat 2 
                                          (count-rec y)))))
                      (table/table-view
                        [
                          [1, 1] 
                          [1, 1]
                          ] 
                        )
                      )
  )
(count-rec 2)

;; @@
;; ->
;;; [[[[[[1 2] [3 4]] [[1 2] [3 4]]] [[[1 2] [3 4]] [[1 2] [3 4]]]]
;;;   [[[[1 2] [3 4]] [[1 2] [3 4]]] [[[1 2] [3 4]] [[1 2] [3 4]]]]]
;;;  [[[[[1 2] [3 4]] [[1 2] [3 4]]] [[[1 2] [3 4]] [[1 2] [3 4]]]]
;;;   [[[[1 2] [3 4]] [[1 2] [3 4]]] [[[1 2] [3 4]] [[1 2] [3 4]]]]]]
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"}],"value":"#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"}],"value":"#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil} #gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil})"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"}],"value":"#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})"}],"value":"#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil}"}],"value":"(#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil} #gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil})"}],"value":"#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil} #gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil}) (#gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil} #gorilla_repl.table.TableView{:contents ((#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil}) (#gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil} #gorilla_repl.table.TableView{:contents [[1 1] [1 1]], :opts nil})), :opts nil})), :opts nil}"}
;; <=

;; **
;;; ## use table-view to view normal data structures
;; **

;; @@
(def concatv 
  (comp (partial into [])
      concat))

#_(instance? gorilla_repl.table.TableView
           (table/table-view (mknest 0)))

#_(w/prewalk #(if (number? %) (* 10 %) %) 
        (mknest 1))

#_(w/prewalk #(if (seqable? %)               
              (do (println :seq %)  %)
              (do (println :nseq %) %)
            ) 
        (mknest 1))
#_(w/prewalk #(if (and (not (instance? gorilla_repl.table.TableView %)) 
                     (seqable? %) 
                     (seqable? (% 0)))   
              (table/table-view %)
              %
            ) 
        (mknest 1))

#_(w/prewalk #(if (seqable? %) 
              (if (seqable? (% 0))
                (do (println :table %) (table/table-view %))
                (do (println :seq   %) %))	
              (do (println :nseq %) %))
        (mknest 1))

(w/prewalk #(if (seqable? %) 
              (if (seqable? (% 0))
                (do (println :table %) (concatv [:table] %  ))
                (do (println :seq   %) %))	
              (do (println :nseq %) %))
        (mknest 1))


;; @@
;; ->
;;; :table [[[[1 2] [3 4]] [[1 2] [3 4]]] [[[1 2] [3 4]] [[1 2] [3 4]]]]
;;; :nseq :table
;;; :table [[[1 2] [3 4]] [[1 2] [3 4]]]
;;; :nseq :table
;;; :table [[1 2] [3 4]]
;;; :nseq :table
;;; :seq [1 2]
;;; :nseq 1
;;; :nseq 2
;;; :seq [3 4]
;;; :nseq 3
;;; :nseq 4
;;; :table [[1 2] [3 4]]
;;; :nseq :table
;;; :seq [1 2]
;;; :nseq 1
;;; :nseq 2
;;; :seq [3 4]
;;; :nseq 3
;;; :nseq 4
;;; :table [[[1 2] [3 4]] [[1 2] [3 4]]]
;;; :nseq :table
;;; :table [[1 2] [3 4]]
;;; :nseq :table
;;; :seq [1 2]
;;; :nseq 1
;;; :nseq 2
;;; :seq [3 4]
;;; :nseq 3
;;; :nseq 4
;;; :table [[1 2] [3 4]]
;;; :nseq :table
;;; :seq [1 2]
;;; :nseq 1
;;; :nseq 2
;;; :seq [3 4]
;;; :nseq 3
;;; :nseq 4
;;; 
;; <-
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[3 4]"}],"value":"[:table [1 2] [3 4]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[3 4]"}],"value":"[:table [1 2] [3 4]]"}],"value":"[:table [:table [1 2] [3 4]] [:table [1 2] [3 4]]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[3 4]"}],"value":"[:table [1 2] [3 4]]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:table</span>","value":":table"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"},{"type":"html","content":"<span class='clj-long'>4</span>","value":"4"}],"value":"[3 4]"}],"value":"[:table [1 2] [3 4]]"}],"value":"[:table [:table [1 2] [3 4]] [:table [1 2] [3 4]]]"}],"value":"[:table [:table [:table [1 2] [3 4]] [:table [1 2] [3 4]]] [:table [:table [1 2] [3 4]] [:table [1 2] [3 4]]]]"}
;; <=

;; @@
  
(def t1 (table/table-view [[0 1] [2 3]]))
t1
(def t2 (table/table-view [[t1 t1] [t1 t1]]))
t2
(def t3 (table/table-view [[t2 t2] [t2 t2]]))
t3
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil} #gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"},{"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>0</span>","value":"0"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[0 1]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"}],"value":"#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}"}],"value":"[#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil} #gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}]"}],"value":"#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil} #gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil} #gorilla_repl.table.TableView{:contents [[#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}] [#gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil} #gorilla_repl.table.TableView{:contents [[0 1] [2 3]], :opts nil}]], :opts nil}]], :opts nil}"}
;; <=

;; **
;;; ## table-view with unregular tables
;; **

;; @@
(def t [
         [1 2]
         ])
t
(table/table-view t)
(def t [
         [1 2]
         [1]
         ])
t
(table/table-view t)
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1]"}],"value":"#gorilla_repl.table.TableView{:contents [[1 2] [1]], :opts nil}"}
;; <=

;; **
;;; 
;;; # Clojure Spec
;; **

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

;; **
;;; # composing predicates
;; **

;; @@
(s/def ::big-even (s/and int? even? #(> % 1000)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/big-even</span>","value":":gorilla-spec/big-even"}
;; <=

;; @@
(s/valid? ::big-even :foo)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/valid? ::big-even :foo)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/valid? ::big-even 10)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/valid? ::big-even 10000)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/def ::name-or-id (s/or :name string? :id int?))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/name-or-id</span>","value":":gorilla-spec/name-or-id"}
;; <=

;; @@
(s/valid? ::name-or-id "abc")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? ::name-or-id 100)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/valid? ::name-or-id :foo)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/conform ::name-or-id 100)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:id</span>","value":":id"},{"type":"html","content":"<span class='clj-long'>100</span>","value":"100"}],"value":"[:id 100]"}
;; <=

;; @@
(s/conform ::name-or-id "abc")
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"},{"type":"html","content":"<span class='clj-string'>&quot;abc&quot;</span>","value":"\"abc\""}],"value":"[:name \"abc\"]"}
;; <=

;; @@
(s/valid? string? nil)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/valid? (s/nilable string?) nil)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; **
;;; # explain
;; **

;; @@
(s/explain ::suit 42)
;; @@
;; ->
;;; val: 42 fails spec: :gorilla-spec/suit predicate: #{:space :heart :diamond :club}
;;; :clojure.spec.alpha/spec  :gorilla-spec/suit
;;; :clojure.spec.alpha/value  42
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/explain ::big-even 5)
;; @@
;; ->
;;; val: 5 fails spec: :gorilla-spec/big-even predicate: even?
;;; :clojure.spec.alpha/spec  :gorilla-spec/big-even
;;; :clojure.spec.alpha/value  5
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/explain ::name-or-id :foo)
;; @@
;; ->
;;; val: :foo fails spec: :gorilla-spec/name-or-id at: [:name] predicate: string?
;;; val: :foo fails spec: :gorilla-spec/name-or-id at: [:id] predicate: int?
;;; :clojure.spec.alpha/spec  :gorilla-spec/name-or-id
;;; :clojure.spec.alpha/value  :foo
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/explain-str ::name-or-id :foo)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-string'>&quot;val: :foo fails spec: :gorilla-spec/name-or-id at: [:name] predicate: string?\\nval: :foo fails spec: :gorilla-spec/name-or-id at: [:id] predicate: int?\\n:clojure.spec.alpha/spec  :gorilla-spec/name-or-id\\n:clojure.spec.alpha/value  :foo\\n&quot;</span>","value":"\"val: :foo fails spec: :gorilla-spec/name-or-id at: [:name] predicate: string?\\nval: :foo fails spec: :gorilla-spec/name-or-id at: [:id] predicate: int?\\n:clojure.spec.alpha/spec  :gorilla-spec/name-or-id\\n:clojure.spec.alpha/value  :foo\\n\""}
;; <=

;; @@
(s/explain-data ::name-or-id :foo)
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:clojure.spec.alpha/problems</span>","value":":clojure.spec.alpha/problems"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:path</span>","value":":path"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:name</span>","value":":name"}],"value":"[:name]"}],"value":"[:path [:name]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:pred</span>","value":":pred"},{"type":"html","content":"<span class='clj-symbol'>clojure.core/string?</span>","value":"clojure.core/string?"}],"value":"[:pred clojure.core/string?]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:val</span>","value":":val"},{"type":"html","content":"<span class='clj-keyword'>:foo</span>","value":":foo"}],"value":"[:val :foo]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:via</span>","value":":via"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/name-or-id</span>","value":":gorilla-spec/name-or-id"}],"value":"[:gorilla-spec/name-or-id]"}],"value":"[:via [:gorilla-spec/name-or-id]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:in</span>","value":":in"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[],"value":"[]"}],"value":"[:in []]"}],"value":"{:path [:name], :pred clojure.core/string?, :val :foo, :via [:gorilla-spec/name-or-id], :in []}"},{"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:path</span>","value":":path"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:id</span>","value":":id"}],"value":"[:id]"}],"value":"[:path [:id]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:pred</span>","value":":pred"},{"type":"html","content":"<span class='clj-symbol'>clojure.core/int?</span>","value":"clojure.core/int?"}],"value":"[:pred clojure.core/int?]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:val</span>","value":":val"},{"type":"html","content":"<span class='clj-keyword'>:foo</span>","value":":foo"}],"value":"[:val :foo]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:via</span>","value":":via"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/name-or-id</span>","value":":gorilla-spec/name-or-id"}],"value":"[:gorilla-spec/name-or-id]"}],"value":"[:via [:gorilla-spec/name-or-id]]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:in</span>","value":":in"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[],"value":"[]"}],"value":"[:in []]"}],"value":"{:path [:id], :pred clojure.core/int?, :val :foo, :via [:gorilla-spec/name-or-id], :in []}"}],"value":"({:path [:name], :pred clojure.core/string?, :val :foo, :via [:gorilla-spec/name-or-id], :in []} {:path [:id], :pred clojure.core/int?, :val :foo, :via [:gorilla-spec/name-or-id], :in []})"}],"value":"[:clojure.spec.alpha/problems ({:path [:name], :pred clojure.core/string?, :val :foo, :via [:gorilla-spec/name-or-id], :in []} {:path [:id], :pred clojure.core/int?, :val :foo, :via [:gorilla-spec/name-or-id], :in []})]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:clojure.spec.alpha/spec</span>","value":":clojure.spec.alpha/spec"},{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/name-or-id</span>","value":":gorilla-spec/name-or-id"}],"value":"[:clojure.spec.alpha/spec :gorilla-spec/name-or-id]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:clojure.spec.alpha/value</span>","value":":clojure.spec.alpha/value"},{"type":"html","content":"<span class='clj-keyword'>:foo</span>","value":":foo"}],"value":"[:clojure.spec.alpha/value :foo]"}],"value":"#:clojure.spec.alpha{:problems ({:path [:name], :pred clojure.core/string?, :val :foo, :via [:gorilla-spec/name-or-id], :in []} {:path [:id], :pred clojure.core/int?, :val :foo, :via [:gorilla-spec/name-or-id], :in []}), :spec :gorilla-spec/name-or-id, :value :foo}"}
;; <=

;; **
;;; # Entity maps
;; **

;; @@
(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;gorilla-spec/email-regex</span>","value":"#'gorilla-spec/email-regex"}
;; <=

;; @@
(s/def ::email-type (s/and string? #(re-matches email-regex %)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/email-type</span>","value":":gorilla-spec/email-type"}
;; <=

;; @@
(s/valid? ::email-type "d.l@gmail.com")
(s/valid? ::email-type "xyz")
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>false</span>","value":"false"}
;; <=

;; @@
(s/def ::acctid int?)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/acctid</span>","value":":gorilla-spec/acctid"}
;; <=

;; @@
(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::email     ::email-type)

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/email</span>","value":":gorilla-spec/email"}
;; <=

;; @@
(s/def ::person (s/keys 
                  :req [::first-name ::last-name ::email]
                  :opt [::phone]))	
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/person</span>","value":":gorilla-spec/person"}
;; <=

;; @@
(s/valid? ::person
          {::first-name "Elon"
           ::last-name  "Musk"
           ::email "elon@example.com"})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; @@
(s/explain ::person {::first-name "Elon"})
;; @@
;; ->
;;; val: #:gorilla-spec{:first-name &quot;Elon&quot;} fails spec: :gorilla-spec/person predicate: (contains? % :gorilla-spec/last-name)
;;; val: #:gorilla-spec{:first-name &quot;Elon&quot;} fails spec: :gorilla-spec/person predicate: (contains? % :gorilla-spec/email)
;;; :clojure.spec.alpha/spec  :gorilla-spec/person
;;; :clojure.spec.alpha/value  #:gorilla-spec{:first-name &quot;Elon&quot;}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/explain ::person {::first-name "Elon"
                     ::last-name "Musk"
                     ::email "n/a"})
;; @@
;; ->
;;; In: [:gorilla-spec/email] val: &quot;n/a&quot; fails spec: :gorilla-spec/email-type at: [:gorilla-spec/email] predicate: (re-matches email-regex %)
;;; :clojure.spec.alpha/spec  :gorilla-spec/person
;;; :clojure.spec.alpha/value  #:gorilla-spec{:first-name &quot;Elon&quot;, :last-name &quot;Musk&quot;, :email &quot;n/a&quot;}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/def :unq/person
       (s/keys :req-un [::first-name ::last-name ::email]
               :opt-un [::phone]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:unq/person</span>","value":":unq/person"}
;; <=

;; @@
(s/conform :unq/person
           {:first-name "Elon"
            :last-name "Musk"
            :email "elon@example.com"})
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:first-name</span>","value":":first-name"},{"type":"html","content":"<span class='clj-string'>&quot;Elon&quot;</span>","value":"\"Elon\""}],"value":"[:first-name \"Elon\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:last-name</span>","value":":last-name"},{"type":"html","content":"<span class='clj-string'>&quot;Musk&quot;</span>","value":"\"Musk\""}],"value":"[:last-name \"Musk\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:email</span>","value":":email"},{"type":"html","content":"<span class='clj-string'>&quot;elon@example.com&quot;</span>","value":"\"elon@example.com\""}],"value":"[:email \"elon@example.com\"]"}],"value":"{:first-name \"Elon\", :last-name \"Musk\", :email \"elon@example.com\"}"}
;; <=

;; @@
(s/explain :unq/person
           {:first-name "Elon"
            :last-name "Musk"
            :email "n/a"})
;; @@
;; ->
;;; In: [:email] val: &quot;n/a&quot; fails spec: :gorilla-spec/email-type at: [:email] predicate: (re-matches email-regex %)
;;; :clojure.spec.alpha/spec  :unq/person
;;; :clojure.spec.alpha/value  {:first-name &quot;Elon&quot;, :last-name &quot;Musk&quot;, :email &quot;n/a&quot;}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/explain :unq/person
           {:first-name "Elon"})
;; @@
;; ->
;;; val: {:first-name &quot;Elon&quot;} fails spec: :unq/person predicate: (contains? % :last-name)
;;; val: {:first-name &quot;Elon&quot;} fails spec: :unq/person predicate: (contains? % :email)
;;; :clojure.spec.alpha/spec  :unq/person
;;; :clojure.spec.alpha/value  {:first-name &quot;Elon&quot;}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(defrecord Person [first-name last-name email phone])

(s/explain :unq/person
           (->Person "Elon" nil nil nil))
;; @@
;; ->
;;; In: [:last-name] val: nil fails spec: :gorilla-spec/last-name at: [:last-name] predicate: string?
;;; In: [:email] val: nil fails spec: :gorilla-spec/email-type at: [:email] predicate: string?
;;; :clojure.spec.alpha/spec  :unq/person
;;; :clojure.spec.alpha/value  #gorilla_spec.Person{:first-name &quot;Elon&quot;, :last-name nil, :email nil, :phone nil}
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(s/conform :unq/person
           (->Person "Elon" "Muk" "elon@example.com" nil))
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-record'>#gorilla_spec.Person{</span>","close":"<span class='clj-record'>}</span>","separator":" ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:first-name</span>","value":":first-name"},{"type":"html","content":"<span class='clj-string'>&quot;Elon&quot;</span>","value":"\"Elon\""}],"value":"[:first-name \"Elon\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:last-name</span>","value":":last-name"},{"type":"html","content":"<span class='clj-string'>&quot;Muk&quot;</span>","value":"\"Muk\""}],"value":"[:last-name \"Muk\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:email</span>","value":":email"},{"type":"html","content":"<span class='clj-string'>&quot;elon@example.com&quot;</span>","value":"\"elon@example.com\""}],"value":"[:email \"elon@example.com\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:phone</span>","value":":phone"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[:phone nil]"}],"value":"#gorilla_spec.Person{:first-name \"Elon\", :last-name \"Muk\", :email \"elon@example.com\", :phone nil}"}
;; <=

;; @@
(s/def ::port number?)
(s/def ::host string?)
(s/def ::id   keyword?)
(s/def ::server (s/keys* :req [::id ::host] :opt [::port]))
(s/conform ::server [::id :s1 ::host "example.com" ::port 5555])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-map'>{</span>","close":"<span class='clj-map'>}</span>","separator":", ","items":[{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/id</span>","value":":gorilla-spec/id"},{"type":"html","content":"<span class='clj-keyword'>:s1</span>","value":":s1"}],"value":"[:gorilla-spec/id :s1]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/host</span>","value":":gorilla-spec/host"},{"type":"html","content":"<span class='clj-string'>&quot;example.com&quot;</span>","value":"\"example.com\""}],"value":"[:gorilla-spec/host \"example.com\"]"},{"type":"list-like","open":"","close":"","separator":" ","items":[{"type":"html","content":"<span class='clj-keyword'>:gorilla-spec/port</span>","value":":gorilla-spec/port"},{"type":"html","content":"<span class='clj-long'>5555</span>","value":"5555"}],"value":"[:gorilla-spec/port 5555]"}],"value":"#:gorilla-spec{:id :s1, :host \"example.com\", :port 5555}"}
;; <=

;; @@
(s/def :animal/kind string?)
(s/def :animal/says string?)
(s/def :animal/common (s/keys :req [:animal/kind :animal/says]))
(s/def :dog/tail? boolean?)
(s/def :dog/bread string?)

;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:dog/bread</span>","value":":dog/bread"}
;; <=

;; @@
(s/def :animal/dog (s/merge :animal/common
                            (s/keys :req [:dog/tail? :dog/breed])))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-keyword'>:animal/dog</span>","value":":animal/dog"}
;; <=

;; @@
(s/valid? :animal/dog 
          {:animal/kind "dog"
           :animal/says "woof"
           :dog/tail? true
           :dog/breed "retriever"})
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>true</span>","value":"true"}
;; <=

;; **
;;; # multi-spec
;; **

;; @@

;; @@
