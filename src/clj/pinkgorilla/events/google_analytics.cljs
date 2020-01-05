(ns pinkgorilla.events.google-analytics
  (:require
   [clojure.string :as str]
   [taoensso.timbre :refer-macros (info)]
   [re-frame.core :refer [reg-fx reg-event-fx dispatch-sync dispatch]]
   ;[district0x.re-frame.google-analytics-fx]
   ))

(def ^:dynamic *enabled* true)

(defn set-enabled! [enabled?]
  (set! *enabled* enabled?))

;; register a co-effect handler

;; https://developers.google.com/analytics/devguides/collection/gtagjs/migration

(reg-fx
 :ga/event
 (fn [[category]]
   (when *enabled*
     (js/gtag "event" (name category)); label value (clj->js fields-object)
     )))

