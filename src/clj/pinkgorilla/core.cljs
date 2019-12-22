(ns ^:figwheel-hooks pinkgorilla.core
  (:require
   [clojure.string :as str]
   [taoensso.timbre :refer-macros (info)]
   [secretary.core :as secretary]
   [reagent.core :as ra]
   [re-frame.core :refer [dispatch-sync dispatch]]
   [pinkgorilla.events.google-analytics]
   [pinkgorilla.subs] ; bring subs to scope
   [pinkgorilla.events] ; bring all events to scope
   [pinkgorilla.util :refer [application-url ws-origin]]
   [pinkgorilla.prefs :as prefs]
   [pinkgorilla.views :as v]
   [pinkgorilla.editor.core :as editor]
   [pinkgorilla.routes :as routes]
   [pinkgorilla.kernel.nrepl :as nrepl-kernel]
   [pinkgorilla.kernel.cljs :as cljs-kernel]
   [pinkgorilla.notifications :refer [add-notification notification]]

    ;[widget.replikativ]
   ))

;; shadow-cljs does not support require outside ns as of 2.8.80!
;; https://anmonteiro.com/2016/10/clojurescript-require-outside-ns/
#_(prefs/if-cljs-kernel
   (require '[pinkgorilla.kernel.shadowcljs :as cljs-kernel])
   (require '[pinkgorilla.kernel.mock :as cljs-kernel]))

(defn ^:before-load my-before-reload-callback []
  (info "BEFORE reload!!!"))

(defn ^:after-load my-after-reload-callback []
  (info "AFTER reload!!!"))

(defn mount-root
  []
  (ra/render [v/gorilla-app] (.getElementById js/document "react-app")))

(defn ^:export init! []
  ;(widget.replikativ/setup-replikativ)
  (pinkgorilla.events.google-analytics/set-enabled! true)
  (routes/app-routes)
  (editor/init-cm-globally!)
  (v/init-mathjax-globally!)
  (let [app-url (application-url)
        route (:anchor app-url)
        read-write (or (not route) (not (str/index-of route "/view")))]
    (dispatch-sync [:initialize-app-db app-url])
    ;; TODO config (+ settings-local-storage) init should kick of cljs-init
    (dispatch-sync [:settings-localstorage-load])
    (dispatch-sync [:initialize-config])
    (mount-root)
    ;; (add-notification (notification :warning "The sky is blue. Gorillas are Pink."))
    ;; (dispatch-sync [:init-cljs])
    ;; (cljs-kernel/init!)
    (if read-write
      (do
        (nrepl-kernel/init! (ws-origin "repl/" app-url))
        ;(dispatch-sync [:initialize-config])
        ;(dispatch-sync [:explore-load])
        ))
    (dispatch [:explore-load])
    (if (not route)
      (routes/nav! "/new")
      (secretary/dispatch! route))))
