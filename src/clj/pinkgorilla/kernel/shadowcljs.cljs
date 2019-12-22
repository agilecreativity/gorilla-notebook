(ns pinkgorilla.kernel.shadowcljs
  (:require-macros
   [cljs.core.async.macros :refer [go go-loop]])
  (:require
   [taoensso.timbre :refer-macros (info)]
   [cljs.core.async :refer [timeout chan close! put! <!]]

   [pinkgorilla.kernel.clojure :refer [the-eval]]
   [pinkgorilla.kernel.cljs-helper :refer [send-result-eval send-console]]))

(defn init! [config]
  ;(go (<! (cklipse/create-state-eval))
  (pinkgorilla.kernel.clojure/init! config)
  (info "shadow cljs kernel init done")
;  )
  )

;; EVAL

(defn eval!
  [segment-id snippet]
  (do
    (send-console segment-id "shadow cljs eval started..")
    (go (send-result-eval segment-id (<! (the-eval snippet {}))))
    nil))
