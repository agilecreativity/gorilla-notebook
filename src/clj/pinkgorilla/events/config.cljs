(ns pinkgorilla.events.config
  "events related to
   app-db init
   configuration loading"
  (:require
   [taoensso.timbre :refer-macros (info)]
   [clojure.string :as str]
   [ajax.core :as ajax]
   [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx dispatch]]
   [day8.re-frame-10x]

   [pinkgorilla.notifications :as events :refer [add-notification notification]]
   [pinkgorilla.db :as db :refer [initial-db]]
   [pinkgorilla.keybindings :as keybindings]
   ;[pinkgorilla.events.helper :refer [text-matches-re default-error-handler  check-and-throw  standard-interceptors]]
   ))

; initialize app-db

(defn- init-app-db
  [app-url]
  (let [base-path (str/replace (:path app-url) #"[^/]+$" "")
        db (merge initial-db {:base-path base-path})]
    db))


(reg-event-db
 :initialize-app-db
 (fn [_ [_ app-url]]
   (info "initializing app-db ..")
   (init-app-db app-url)))

; load configuration


(reg-event-fx
 :initialize-config
 (fn [{:keys [db]} _]
   (add-notification (notification :info "Loading config.. "))
   {:db       db ;  (merge db {:message "Loading configuration ..."})
    :http-xhrio {:method          :get
                 :uri             (str (:base-path db) "config")
                 :timeout         5000                     ;; optional see API docs
                 :response-format (ajax/transit-response-format) ;; IMPORTANT!: You must provide this.
                 :on-success      [:process-config-response]
                 :on-failure      [:process-error-response "load-config"]}}))


; used by process config reponse below.
(def install-commands
  (re-frame.core/->interceptor
   :id :install-commands
   :after (fn
            [context]
            (keybindings/install-commands (get-in context [:coeffects :db :all-commands]))
            context)))


(reg-event-db
 :process-config-response
 [install-commands]
 (fn [db [_ response]]
   (-> (-> (assoc-in db [:config] response)
           ;; (assoc-in db [:settings :service] (:settings response)
                     )
           ;(assoc :message nil)
           )))


(reg-event-db
 :toggle.reframe10x
 (fn [db _]
   (let [visible (not (get-in db [:dev :reframe10x-visible?]))
         ;_ (.setItem js/localStorage "day8.re-frame-10x.show-panel" (str visible))
         _ (info "reframe-10x visible: " visible)
        ; _ (dispatch [:settings/user-toggle-panel])
         _ (day8.re-frame-10x/show-panel! visible)] ; https://github.com/day8/re-frame-10x/pull/210s
     (assoc-in db [:dev :reframe10x-visible?] visible))))


