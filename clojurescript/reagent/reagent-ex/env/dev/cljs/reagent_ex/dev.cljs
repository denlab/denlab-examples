(ns ^:figwheel-no-load reagent-ex.dev
  (:require
    [reagent-ex.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
