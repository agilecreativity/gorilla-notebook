(ns pinkgorilla.events.google-analytics
  (:require
   [clojure.string :as str]
   [taoensso.timbre :refer-macros (info)]
   [re-frame.core :refer [reg-event-fx dispatch-sync dispatch]]
   [district0x.re-frame.google-analytics-fx]))

(reg-event-fx
 :ga-notebook-load
 (fn []
   {:ga/event ["notebook" "load" "" 1 {}]}))