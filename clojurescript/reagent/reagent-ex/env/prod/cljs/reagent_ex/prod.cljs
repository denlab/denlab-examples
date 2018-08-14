(ns reagent-ex.prod
  (:require [reagent-ex.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
